package com.emanh.rootapp.presentation.ui.album

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.domain.usecase.ViewsSongUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefAlbumUseCase
import com.emanh.rootapp.presentation.navigation.ArtistScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AlbumViewModel"

@HiltViewModel
class AlbumViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRouter: AppRouter,
    private val usersUseCase: UsersUseCase,
    private val viewsSongUseCase: ViewsSongUseCase,
    private val crossRefAlbumUseCase: CrossRefAlbumUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AlbumUiState())
    val uiState: StateFlow<AlbumUiState> = _uiState.asStateFlow()

    private val albumId: Int = savedStateHandle.get<Int>("albumId") ?: -1

    init {
        if (albumId != -1) {
            Log.d(TAG, "Initializing with albumId: $albumId")

            loadAlbumDetails(albumId)
            loadAlbumData(albumId)
        }
    }

    fun onBackClick() {
        appRouter.getNavController()?.goBack()
    }

    fun goToArtist(artistId: Int) {
        appRouter.getNavController()?.navigateTo(ArtistScreenNavigation.getRoute(artistId))
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

    private fun loadAlbumDetails(albumId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val albums = crossRefAlbumUseCase.getAlbumDetailsById(albumId).first()
                val album = albums.albums
                val songs = albums.songsList
                val songMap = songs.associateBy { it.songId }
                val sortedSongs = album.songsIdList.mapNotNull { songId ->
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
                    currentState.copy(album = AlbumsModel(id = album.albumId,
                                                          avatarUrl = album.avatarUrl,
                                                          title = album.title,
                                                          subtitle = album.subtitle,
                                                          albumType = album.albumType,
                                                          releaseDate = album.releaseDate,
                                                          artist = album.artistsIdList,
                                                          songs = album.songsIdList), songList = sortedSongs, isLoading = false)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading album: ${e.message}")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun loadAlbumData(albumId: Int) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            combine(
                    usersUseCase.getOwnerAlbum(albumId),
                    viewsSongUseCase.getTotalListenerAlbum(albumId)
            ) { artistList, views ->
                Pair(artistList, views)
            }.catch { error ->
                Log.e(TAG, "Error fetching album data: $error")
                _uiState.update { it.copy(isLoading = false) }
                emit(Pair(emptyList(), 0))
            }.collect { (artistList, views) ->
                _uiState.update { currentState ->
                    currentState.copy(
                            artistList = artistList,
                            views = views,
                            isLoading = false
                    )
                }
            }
        }
    }
}