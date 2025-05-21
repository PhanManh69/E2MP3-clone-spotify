package com.emanh.rootapp.presentation.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.usecase.crossref.CrossRefPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import kotlinx.coroutines.flow.first

private const val TAG = "PlaylistViewModel"

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRouter: AppRouter,
    private val usersUseCase: UsersUseCase,
    private val crossRefPlaylistUseCase: CrossRefPlaylistUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistUiState())
    val uiState: StateFlow<PlaylistUiState> = _uiState.asStateFlow()

    private val playlistId: Int = savedStateHandle.get<Int>("playlistId") ?: -1

    init {
        if (playlistId != -1) {
            Log.d(TAG, "Initializing with playlistId: $playlistId")

            loadPlaylistDetails(playlistId)
        }
    }

    fun onBackClick() {
        appRouter.getNavController()?.goBack()
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

    private fun loadPlaylistDetails(playlistId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val playlists = crossRefPlaylistUseCase.getPlaylistDetailsById(playlistId).first()
                val playlist = playlists.playlists
                val songs = playlists.songsList
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
                                                                songsIdList = playlist.songsIdList), songList = sortedSongs, isLoading = false)
                }

                playlist.ownerId?.let { ownerId ->
                    try {
                        val owner = usersUseCase.getOwnerPlaylist(ownerId).first()
                        _uiState.update { it.copy(owner = owner) }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error loading Owner: ${e.message}")
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error loading playlist: ${e.message}")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}