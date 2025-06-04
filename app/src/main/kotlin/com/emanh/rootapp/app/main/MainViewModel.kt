package com.emanh.rootapp.app.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import com.emanh.rootapp.domain.usecase.ViewsSongUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.SessionToken
import com.emanh.rootapp.service.MusicService
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import androidx.core.net.toUri

@UnstableApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val viewsSongUseCase: ViewsSongUseCase,
    private val crossRefSongUseCase: CrossRefSongUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var mediaController: MediaController? = null
    private val controllerFuture: ListenableFuture<MediaController> by lazy {
        val intent = Intent(context, MusicService::class.java)
        context.startService(intent)

        MediaController.Builder(context, SessionToken(context, ComponentName(context, MusicService::class.java))).buildAsync()
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineException: ${exception.message}")
        _uiState.update { it.copy(isLoading = false) }
    }

    init {
        initializeMediaController()
    }

    private fun initializeMediaController() {
        controllerFuture.addListener({
                                         try {
                                             mediaController = controllerFuture.get()
                                             setupMediaControllerListener()
                                         } catch (e: Exception) {
                                             Log.e(TAG, "Failed to create MediaController: ${e.message}")
                                         }
                                     }, ContextCompat.getMainExecutor(context))
    }

    private fun setupMediaControllerListener() {
        mediaController?.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _uiState.update { it.copy(isPlayed = isPlaying) }

                if (isPlaying) {
                    _uiState.value.progressJob?.cancel()
                    startProgressTracking(viewModelScope)
                } else {
                    stopProgressTracking()
                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    _uiState.update { it.copy(isPlayed = false, currentProgress = 0f) }
                }
            }

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo, newPosition: Player.PositionInfo, reason: Int
            ) {
                updateProgress()
            }
        })
    }

    fun onPlayPauseClick(isPlayed: Boolean, scope: CoroutineScope) {
        mediaController?.let { controller ->
            if (isPlayed) {
                controller.play()
                startProgressTracking(scope)
            } else {
                controller.pause()
                stopProgressTracking()
            }
        }
    }

    private fun updateProgress() {
        mediaController?.let { controller ->
            val duration = controller.duration
            val currentPosition = controller.currentPosition

            if (duration > 0) {
                val progress = currentPosition.toFloat() / duration.toFloat()
                _uiState.update { it.copy(currentProgress = progress) }
            }
        }
    }

    private fun stopProgressTracking() {
        runCatching {
            _uiState.value.progressJob?.cancel()
        }.onFailure { e ->
            Log.e(TAG, "Error cancelling progress job: ${e.message}")
        }

        _uiState.update { it.copy(progressJob = null) }
    }

    fun getSongId(songId: Long) {
        getSongById(songId)
        increaseViewsForSong(songId)
    }

    fun getTitleFromItem(title: String, subtitle: String) {
        _uiState.update { it.copy(headerTitle = title, headerSubtitle = subtitle) }
    }

    fun onValueTimeLineChange(currentProgress: Float) {
        _uiState.update { it.copy(currentProgress = currentProgress) }
    }

    fun onSliderPositionChangeFinished(newProgress: Float, scope: CoroutineScope) {
        mediaController?.let { controller ->
            val duration = controller.duration
            val newPosition = (newProgress * duration).toLong()
            controller.seekTo(newPosition)

            _uiState.update { it.copy(currentProgress = newProgress) }

            if (controller.isPlaying) {
                startProgressTracking(scope)
            }
        }
    }

    private fun getSongById(songId: Long) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val single = crossRefSongUseCase.getSongDetailsById(songId).first()
                processSongDetails(single)
            } catch (e: Exception) {
                Log.e(TAG, "Error loading song: ${e.message}")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun processSongDetails(single: CrossRefSongsModel) {
        val song = single.songs
        val artistsList = single.artistsList
        val timelineSeconds = convertStringToTime(song.timeline ?: "00:00:00")

        mediaController?.let { controller ->
            val mediaItem = MediaItem.Builder()
                .setUri(song.songUrl)
                .setMediaMetadata(MediaMetadata.Builder()
                                      .setTitle(song.title)
                                      .setArtist(song.subtitle)
                                      .setArtworkUri(song.avatarUrl?.toUri())
                                      .build())
                .build()

            controller.setMediaItem(mediaItem)
            controller.prepare()
            controller.play()
        }

        _uiState.update { currentState ->
            currentState.copy(single = mapToSongsModel(song),
                              timeline = timelineSeconds,
                              artistsList = mapToUsersList(song.artistsIdList, artistsList),
                              currentProgress = 0f,
                              isPlayed = true,
                              isLoading = false)
        }
    }

    private fun mapToSongsModel(song: SongsEntity): SongsModel {
        return SongsModel(id = song.songId,
                          avatarUrl = song.avatarUrl,
                          songUrl = song.songUrl,
                          title = song.title,
                          subtitle = song.subtitle,
                          timeline = song.timeline,
                          releaseDate = song.releaseDate,
                          genres = song.genresIdList,
                          likes = song.likesIdList,
                          artists = song.artistsIdList)
    }

    private fun mapToUsersList(artistsIdList: List<Long>?, artistsList: List<UsersEntity>): List<UsersModel> {
        if (artistsIdList.isNullOrEmpty()) return emptyList()

        val artistsMap = artistsList.associateBy { it.userId }

        return artistsIdList.mapNotNull { artistId ->
            artistsMap[artistId]?.let { artist ->
                UsersModel(id = artist.userId,
                           isArtist = artist.isArtist,
                           username = artist.username,
                           email = artist.email,
                           password = artist.password,
                           avatarUrl = artist.avatarUrl,
                           name = artist.name,
                           followers = artist.followers,
                           following = artist.followingIdList)
            }
        }
    }

    private fun increaseViewsForSong(songId: Long) {
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                val userIdFake = 2L
                val result = viewsSongUseCase.trackSongView(userIdFake, songId)
                val viewCount = result.numberListener ?: 0
                Log.d(TAG, "Song $songId viewed by user $userIdFake - view count: $viewCount, timestamp: ${result.dateTime}")
            } catch (e: Exception) {
                Log.e(TAG, "Error recording song view: ${e.message}")
            }
        }
    }

    private fun convertStringToTime(timeString: String): Long {
        val parts = timeString.split(":").map { it.toLongOrNull() ?: 0L }

        return when (parts.size) {
            3 -> parts[0] * 3600 + parts[1] * 60 + parts[2]
            2 -> parts[0] * 60 + parts[1]
            1 -> parts[0]
            else -> 0L
        }
    }

    private fun startProgressTracking(scope: CoroutineScope) {
        stopProgressTracking()

        val job = scope.launch {
            while (mediaController?.isPlaying == true) {
                updateProgress()
                delay(16)
            }
        }

        _uiState.update { it.copy(progressJob = job) }
    }

    private fun stopMusicService() {
        try {
            mediaController?.stop()

            val intent = Intent(context, MusicService::class.java)
            context.stopService(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping MusicService: ${e.message}")
        }
    }

    override fun onCleared() {
        super.onCleared()

        stopProgressTracking()

        try {
            mediaController?.stop()
            MediaController.releaseFuture(controllerFuture)
        } catch (e: Exception) {
            Log.e(TAG, "Error releasing MediaController: ${e.message}")
        }

        stopMusicService()
        mediaController = null
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}