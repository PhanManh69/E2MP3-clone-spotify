package com.emanh.rootapp.presentation.ui.playlistyour

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.usecase.PlaylistsUseCase
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PlaylistYourViewModel"

@HiltViewModel
class PlaylistYourViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val songsUseCase: SongsUseCase,
    private val usersUseCase: UsersUseCase,
    private val playlistsUseCase: PlaylistsUseCase,
    private val crossRefPlaylistUseCase: CrossRefPlaylistUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlaylistYourUiState())
    val uiState: StateFlow<PlaylistYourUiState> = _uiState.asStateFlow()

    private val playlistId: Long = savedStateHandle.get<Long>("playlistId") ?: -1

    init {
        getSongsRecommend()

        if (playlistId != -1L) {
            Log.d(TAG, "Initializing with playlistId: $playlistId")

            getOwnerAlbum(playlistId)
            loadPlaylistDetails(playlistId)
        }
    }

    fun totalTime(songsList: List<SongsModel>): String {
        var totalSeconds = 0

        songsList.forEach { song ->
            val parts = song.timeline?.split(":")
            if (parts?.size == 3) {
                val hours = parts[0].toIntOrNull() ?: 0
                val minutes = parts[1].toIntOrNull() ?: 0
                val seconds = parts[2].toIntOrNull() ?: 0

                totalSeconds += hours * 3600 + minutes * 60 + seconds
            }
        }

        val totalHours = totalSeconds / 3600
        val totalMinutes = (totalSeconds % 3600) / 60

        return "${totalHours}h${totalMinutes}min"
    }

    fun onRefreshClick() {
        getSongsRecommend()
    }

    fun onIconClick(type: Int, songId: Long) {
        viewModelScope.launch {
            try {
                crossRefPlaylistUseCase.insertSongToPlaylist(PlaylistSongEntity(playlistId, songId))

                val updatedPlaylist = uiState.value.playlist?.let { currentPlaylist ->
                    val newSongsIdList = currentPlaylist.songsIdList + songId
                    currentPlaylist.copy(songsIdList = newSongsIdList)
                }

                updatedPlaylist?.let {
                    playlistsUseCase.updatePlaylist(it)
                }

                when (type) {
                    1 -> {
                        songsUseCase.getSongsRecommend().collect { songs ->
                            _uiState.update { it.copy(songsRecommend1 = songs) }
                        }
                    }

                    2 -> {
                        songsUseCase.getSongsRecommend().collect { songs ->
                            _uiState.update { it.copy(songsRecommend2 = songs) }
                        }
                    }

                    3 -> {
                        songsUseCase.getSongsRecommend().collect { songs ->
                            _uiState.update { it.copy(songsRecommend3 = songs) }
                        }
                    }

                    4 -> {
                        songsUseCase.getSongsRecommend().collect { songs ->
                            _uiState.update { it.copy(songsRecommend4 = songs) }
                        }
                    }

                    5 -> {
                        songsUseCase.getSongsRecommend().collect { songs ->
                            _uiState.update { it.copy(songsRecommend5 = songs) }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error in onIconClick", e)
            }
        }
    }

    fun onMoreSongClick(songId: Long) {
        val song = uiState.value.songsList.find { it.id == songId }
        _uiState.update { it.copy(song = song, isShowButtonSheet = true) }
    }

    fun onDismissRequest() {
        _uiState.update { it.copy(isShowButtonSheet = false) }
    }

    fun onRemovePlaylistClick() {
        viewModelScope.launch {
            try {
                crossRefPlaylistUseCase.deletePlaylistSong(PlaylistSongEntity(playlistId, uiState.value.song?.id ?: -1))

                val updatedPlaylist = uiState.value.playlist?.let { currentPlaylist ->
                    val newSongsIdList = currentPlaylist.songsIdList - (uiState.value.song?.id ?: -1)
                    currentPlaylist.copy(songsIdList = newSongsIdList)
                }

                updatedPlaylist?.let {
                    playlistsUseCase.updatePlaylist(it)
                }

                _uiState.update { it.copy(isShowButtonSheet = false) }
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error in onRemovePlaylistClick", e)
            }
        }
    }

    private fun getOwnerAlbum(playlistId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            usersUseCase.getOwnerPlaylistYour(playlistId).collect { owner ->
                _uiState.update { it.copy(owner = owner, isLoading = false) }
            }
        }
    }

    private fun loadPlaylistDetails(playlistId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                crossRefPlaylistUseCase.getPlaylistDetailsById(playlistId).collect {
                    val playlist = it.playlists
                    val songs = it.songsList
                    val songMap = songs.associateBy { it.songId }
                    val sortedSongs = playlist.songsIdList.mapNotNull { songId ->
                        songMap[songId]?.let { song ->
                            SongsModel(id = song.songId,
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
                    }

                    _uiState.update { currentState ->
                        currentState.copy(playlist = PlaylistsModel(id = playlist.playlistId,
                                                                    avatarUrl = playlist.avatarUrl,
                                                                    title = playlist.title,
                                                                    subtitle = playlist.subtitle,
                                                                    ownerId = playlist.ownerId,
                                                                    releaseDate = playlist.releaseDate,
                                                                    songsIdList = playlist.songsIdList), songsList = sortedSongs, isLoading = false)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading playlist: ${e.message}")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun getSongsRecommend() {
        viewModelScope.launch {
            try {
                val deferredResults = (1..5).map {
                    async { songsUseCase.getSongsRecommend().first() }
                }

                val results = deferredResults.awaitAll()

                _uiState.update { currentState ->
                    currentState.copy(songsRecommend1 = results.getOrNull(0),
                                      songsRecommend2 = results.getOrNull(1),
                                      songsRecommend3 = results.getOrNull(2),
                                      songsRecommend4 = results.getOrNull(3),
                                      songsRecommend5 = results.getOrNull(4))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading songs recommend: ${e.message}")
            }
        }
    }
}