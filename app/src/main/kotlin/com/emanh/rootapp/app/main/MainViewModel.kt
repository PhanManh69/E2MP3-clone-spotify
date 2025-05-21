package com.emanh.rootapp.app.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import com.emanh.rootapp.domain.usecase.ViewsSongUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefSongUseCase
import com.emanh.rootapp.utils.loadProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val viewsSongUseCase: ViewsSongUseCase,
    private val crossRefSongUseCase: CrossRefSongUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineException: ${exception.message}")
        _uiState.update { it.copy(isLoading = false) }
    }

    fun onPlayPauseClick(isPlayed: Boolean, scope: CoroutineScope) {
        if (isPlayed == _uiState.value.isPlayed) return

        _uiState.update { it.copy(isPlayed = isPlayed) }

        if (isPlayed) {
            startProgressTracking(scope)
        } else {
            stopProgressTracking()
        }
    }

    private fun startProgressTracking(scope: CoroutineScope) {
        stopProgressTracking()

        val currentPosition = (_uiState.value.currentProgress * _uiState.value.timeline).toLong()
        val job = scope.launch(SupervisorJob()) {
            try {
                loadProgress(timeSeconds = _uiState.value.timeline,
                             startPositionSeconds = currentPosition,
                             isPlaying = { _uiState.value.isPlayed },
                             updateProgress = { progress ->
                                 _uiState.update { it.copy(currentProgress = progress) }
                             },
                             onFinish = {
                                 _uiState.update { it.copy(isPlayed = false, progressJob = null) }
                             })
            } catch (e: CancellationException) {
                Log.d(TAG, "Progress tracking cancelled: ${e.message}")
            } catch (e: Exception) {
                Log.e(TAG, "Error in progress tracking: ${e.message}")
                _uiState.update { it.copy(isPlayed = false, progressJob = null) }
            }
        }

        _uiState.update { it.copy(progressJob = job) }
    }

    private fun stopProgressTracking() {
        runCatching {
            _uiState.value.progressJob?.cancel()
        }.onFailure { e ->
            Log.e(TAG, "Error cancelling progress job: ${e.message}")
        }

        _uiState.update { it.copy(progressJob = null) }
    }

    fun getSongId(songId: Int) {
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
        stopProgressTracking()

        _uiState.update { it.copy(currentProgress = newProgress) }

        if (_uiState.value.isPlayed) {
            startProgressTracking(scope)
        }
    }

    private fun getSongById(songId: Int) {
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

        stopProgressTracking()

        val job = viewModelScope.launch {
            loadProgress(timeSeconds = timelineSeconds, startPositionSeconds = 0L, isPlaying = { true }, updateProgress = { progress ->
                _uiState.update { it.copy(currentProgress = progress) }
            }, onFinish = {
                _uiState.update { it.copy(isPlayed = false, progressJob = null) }
            })
        }

        _uiState.update { currentState ->
            currentState.copy(single = mapToSongsModel(song),
                              timeline = timelineSeconds,
                              artistsList = mapToUsersList(song.artistsIdList, artistsList),
                              currentProgress = 0f,
                              progressJob = job,
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

    private fun mapToUsersList(artistsIdList: List<Int>?, artistsList: List<UsersEntity>): List<UsersModel> {
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

    private fun increaseViewsForSong(songId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                val userId = 2
                val result = viewsSongUseCase.trackSongView(userId, songId)
                val viewCount = result.numberListener ?: 0
                Log.d(TAG, "Song $songId viewed by user $userId - view count: $viewCount, timestamp: ${result.dateTime}")
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
}