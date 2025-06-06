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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        if (playlistId != -1L) {
            Log.d(TAG, "Initializing with playlistId: $playlistId")

            getSongsRecommend(playlistId)
            getOwnerAlbum(playlistId)
            loadPlaylistDetails(playlistId)
        }
    }

    fun onRefreshClick() {
        getSongsRecommend(playlistId)
    }

    fun onIconClick(songId: Long) {
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
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error in onIconClick", e)
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

    private fun getSongsRecommend(playlistId: Long) {
        viewModelScope.launch {
            songsUseCase.getSongsRecommend(playlistId).collect { songs ->
                _uiState.update { it.copy(songsRecommendList = songs) }
            }
        }
    }
}