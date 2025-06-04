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

    init {
        getUserById()
        loadUserLibraryData()
    }

    fun goToSearchInput() {
        appRouter.getNavController()?.navigateTo(SearchInputScreenNavigation.getRoute())
    }

    fun onCloseClick() {
        _uiState.update { it.copy(primaryChips = null, secondaryChips = "", currentType = STFMenuLibraryType.Default) }
    }

    fun onLikedSongsClick() {
        appRouter.getNavController()?.navigateTo(PlaylistScreenNavigation.getRoute(VIEW_ALL_LIKED))
    }

    fun onCreatePlaylist() {
        appRouter.getNavController()?.navigateTo(CreatePlaylistScreenNavigation.getRoute())
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
        appRouter.getNavController()?.navigateTo(PlaylistYourScreenNavigation.getRoute(playlist.id))
    }

    fun onPlaylistForYouClick(playlist: PlaylistsModel) {
        appRouter.getNavController()?.navigateTo(PlaylistScreenNavigation.getRoute(playlist.id))
    }

    fun onFavoriteArtistClick(artist: UsersModel) {
        appRouter.getNavController()?.navigateTo(ArtistScreenNavigation.getRoute(artist.id))
    }

    fun onLikedAlbumClick(album: AlbumsModel) {
        appRouter.getNavController()?.navigateTo(AlbumScreenNavigation.getRoute(album.id))
    }

    private fun getUserById() {
        val userIdFake = 2L

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                usersUseCase.getArtistById(userIdFake).catch { error ->
                    Log.e(TAG, "Error fetching Users: $error")
                }.collect { user ->
                    _uiState.update { it.copy(user = user, isLoading = false) }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in getLikedSongByUser: $e")
            }
        }
    }

    private fun loadUserLibraryData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val deferredLikedSongs = async { getLikedSongByUser() }
                val deferredYourPlaylists = async { getPlaylistsYourByUser() }
                val deferredForYouPlaylists = async { getPlaylistsForYouByUser() }
                val deferredFavoriteArtists = async { getFavoriteArtistsByUser() }
                val deferredLikedAlbum = async { getLikedAlbumByUser() }

                deferredLikedSongs.await()
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

    private suspend fun getLikedSongByUser() {
        val userIdFake = 2L

        try {
            songsUseCase.getLikedSongsByUser(userIdFake).catch { error ->
                Log.e(TAG, "Error fetching LikedSongsByUser: $error")
            }.collect { listSongs ->
                _uiState.update { it.copy(listLikedSongs = listSongs) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getLikedSongByUser: $e")
        }
    }

    private suspend fun getPlaylistsYourByUser() {
        val userIdFake = 2L

        try {
            playlistUseCase.getPlaylistsYourByUser(userIdFake).catch { error ->
                Log.e(TAG, "Error fetching PlaylistsYourByUser: $error")
            }.collect { listPlaylists ->
                _uiState.update { it.copy(listPlaylistYour = listPlaylists) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getPlaylistsYourByUser: $e")
        }
    }

    private suspend fun getPlaylistsForYouByUser() {
        val userIdFake = 2L

        try {
            playlistUseCase.getPlaylistsForYouByUser(userIdFake).catch { error ->
                Log.e(TAG, "Error fetching PlaylistsForYouByUser: $error")
            }.collect { listPlaylists ->
                _uiState.update { it.copy(listPlaylistForYou = listPlaylists) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getPlaylistsForYouByUser: $e")
        }
    }

    private suspend fun getFavoriteArtistsByUser() {
        val userIdFake = 2L

        try {
            usersUseCase.getFoveriteArtistsByUser(userIdFake).catch { error ->
                Log.e(TAG, "Error fetching FavoriteArtistsByUser: $error")
            }.collect { listArtists ->
                _uiState.update { it.copy(listFavoriteArtist = listArtists) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getFavoriteArtistsByUser: $e")
        }
    }

    private suspend fun getLikedAlbumByUser() {
        val userIdFake = 2L

        try {
            albumUseCase.getAlbumLikeByUser(userIdFake).catch { error ->
                Log.e(TAG, "Error fetching LikedAlbumByUser: $error")
            }.collect { listAlbum ->
                _uiState.update { it.copy(listLikedAlbum = listAlbum) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getLikedAlbumByUser: $e")
        }
    }
}