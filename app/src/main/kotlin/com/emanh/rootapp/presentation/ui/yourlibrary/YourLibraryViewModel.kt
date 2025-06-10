package com.emanh.rootapp.presentation.ui.yourlibrary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.usecase.AlbumsUseCase
import com.emanh.rootapp.domain.usecase.PlaylistsUseCase
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.presentation.composable.STFMenuLibraryType
import com.emanh.rootapp.presentation.composable.SecondaryLibraryData
import com.emanh.rootapp.presentation.navigation.AlbumScreenNavigation
import com.emanh.rootapp.presentation.navigation.ArtistScreenNavigation
import com.emanh.rootapp.presentation.navigation.CreatePlaylistScreenNavigation
import com.emanh.rootapp.presentation.navigation.PlaylistScreenNavigation
import com.emanh.rootapp.presentation.navigation.PlaylistYourScreenNavigation
import com.emanh.rootapp.presentation.navigation.SearchInputScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.utils.MyConstant.VIEW_ALL_LIKED
import com.emanh.rootapp.utils.MyConstant.VIEW_ALL_YOUR_SONG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "YourLibraryViewModel"

@HiltViewModel
class YourLibraryViewModel @Inject constructor(
    private val appRouter: AppRouter,
    private val songsUseCase: SongsUseCase,
    private val playlistUseCase: PlaylistsUseCase,
    private val usersUseCase: UsersUseCase,
    private val albumUseCase: AlbumsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(YourLibraryUiState())
    val uiState: StateFlow<YourLibraryUiState> = _uiState.asStateFlow()

    private var currentUserId: Long = -1

    fun setCurrentUserId(userId: Long) {
        currentUserId = userId

        if (currentUserId != -1L) {
            getUserById(currentUserId)
            loadUserLibraryData(currentUserId)
        }
    }

    fun goToSearchInput() {
        appRouter.getMainNavController()?.navigateTo(SearchInputScreenNavigation.getRoute())
    }

    fun onCloseClick() {
        _uiState.update { it.copy(primaryChips = null, secondaryChips = "", currentType = STFMenuLibraryType.Default) }
    }

    fun onLikedSongsClick() {
        appRouter.getMainNavController()?.navigateTo(PlaylistScreenNavigation.getRoute(VIEW_ALL_LIKED))
    }

    fun onYourSongsClick() {
        appRouter.getMainNavController()?.navigateTo(PlaylistScreenNavigation.getRoute(VIEW_ALL_YOUR_SONG))
    }

    fun onCreatePlaylist() {
        appRouter.getMainNavController()?.navigateTo(CreatePlaylistScreenNavigation.getRoute())
    }

    fun onPrimaryChipsClick(item: SecondaryLibraryData, type: STFMenuLibraryType) {
        _uiState.update { it.copy(currentType = type) }

        if (_uiState.value.primaryChips == item) {
            _uiState.update { it.copy(primaryChips = null) }
        } else {
            _uiState.update { it.copy(primaryChips = item) }
        }
    }

    fun onSecondaryChipsClick(item: String, type: STFMenuLibraryType) {
        _uiState.update { it.copy(currentType = type) }

        if (_uiState.value.secondaryChips == item) {
            _uiState.update { it.copy(secondaryChips = "") }
        } else {
            _uiState.update { it.copy(secondaryChips = item) }
        }
    }

    fun onPlaylistYourClick(playlist: PlaylistsModel) {
        appRouter.getMainNavController()?.navigateTo(PlaylistYourScreenNavigation.getRoute(playlist.id))
    }

    fun onPlaylistForYouClick(playlist: PlaylistsModel) {
        appRouter.getMainNavController()?.navigateTo(PlaylistScreenNavigation.getRoute(playlist.id))
    }

    fun onFavoriteArtistClick(artist: UsersModel) {
        appRouter.getMainNavController()?.navigateTo(ArtistScreenNavigation.getRoute(artist.id))
    }

    fun onLikedAlbumClick(album: AlbumsModel) {
        appRouter.getMainNavController()?.navigateTo(AlbumScreenNavigation.getRoute(album.id))
    }

    private fun getUserById(currentUserId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                usersUseCase.getArtistById(currentUserId).catch { error ->
                    Log.e(TAG, "Error fetching Users: $error")
                }.collect { user ->
                    _uiState.update { it.copy(user = user, isLoading = false) }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in getLikedSongByUser: $e")
            }
        }
    }

    private fun loadUserLibraryData(currentUserId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val deferredLikedSongs = async { getLikedSongByUser(currentUserId) }
                val deferredYourSongs = async { getYourSongByUser(currentUserId) }
                val deferredAlbumYour = async { getAlbumYourByUser(currentUserId) }
                val deferredYourPlaylists = async { getPlaylistsYourByUser(currentUserId) }
                val deferredForYouPlaylists = async { getPlaylistsForYouByUser(currentUserId) }
                val deferredFavoriteArtists = async { getFavoriteArtistsByUser(currentUserId) }
                val deferredLikedAlbum = async { getLikedAlbumByUser(currentUserId) }

                deferredLikedSongs.await()
                deferredYourSongs.await()
                deferredAlbumYour.await()
                deferredYourPlaylists.await()
                deferredForYouPlaylists.await()
                deferredFavoriteArtists.await()
                deferredLikedAlbum.await()

            } catch (e: Exception) {
                Log.e(TAG, "Error loading library data: $e")
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun getLikedSongByUser(currentUserId: Long) {
        try {
            songsUseCase.getLikedSongsByUser(currentUserId).catch { error ->
                Log.e(TAG, "Error fetching LikedSongsByUser: $error")
            }.collect { listSongs ->
                _uiState.update { it.copy(listLikedSongs = listSongs) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getLikedSongByUser: $e")
        }
    }

    private suspend fun getYourSongByUser(currentUserId: Long) {
        try {
            songsUseCase.getSongsByArtist(currentUserId).catch { error ->
                Log.e(TAG, "Error fetching LikedSongsByUser: $error")
            }.collect { listSongs ->
                _uiState.update { it.copy(listYourSongs = listSongs) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getLikedSongByUser: $e")
        }
    }

    private suspend fun getAlbumYourByUser(currentUserId: Long) {
        try {
            albumUseCase.getAlbumByArtist(currentUserId).catch { error ->
                Log.e(TAG, "Error fetching LikedAlbumByUser: $error")
            }.collect { listAlbum ->
                _uiState.update { it.copy(listAlbumsYour = listAlbum) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getLikedAlbumByUser: $e")
        }
    }

    private suspend fun getPlaylistsYourByUser(currentUserId: Long) {
        try {
            playlistUseCase.getPlaylistsYourByUser(currentUserId).catch { error ->
                Log.e(TAG, "Error fetching PlaylistsYourByUser: $error")
            }.collect { listPlaylists ->
                _uiState.update { it.copy(listPlaylistYour = listPlaylists) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getPlaylistsYourByUser: $e")
        }
    }

    private suspend fun getPlaylistsForYouByUser(currentUserId: Long) {
        try {
            playlistUseCase.getPlaylistsForYouByUser(currentUserId).catch { error ->
                Log.e(TAG, "Error fetching PlaylistsForYouByUser: $error")
            }.collect { listPlaylists ->
                _uiState.update { it.copy(listPlaylistForYou = listPlaylists) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getPlaylistsForYouByUser: $e")
        }
    }

    private suspend fun getFavoriteArtistsByUser(currentUserId: Long) {
        try {
            usersUseCase.getFoveriteArtistsByUser(currentUserId).catch { error ->
                Log.e(TAG, "Error fetching FavoriteArtistsByUser: $error")
            }.collect { listArtists ->
                _uiState.update { it.copy(listFavoriteArtist = listArtists) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getFavoriteArtistsByUser: $e")
        }
    }

    private suspend fun getLikedAlbumByUser(currentUserId: Long) {
        try {
            albumUseCase.getAlbumLikeByUser(currentUserId).catch { error ->
                Log.e(TAG, "Error fetching LikedAlbumByUser: $error")
            }.collect { listAlbum ->
                _uiState.update { it.copy(listLikedAlbum = listAlbum) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getLikedAlbumByUser: $e")
        }
    }
}