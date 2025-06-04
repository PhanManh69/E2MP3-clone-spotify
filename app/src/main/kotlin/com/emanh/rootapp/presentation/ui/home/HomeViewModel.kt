package com.emanh.rootapp.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.AlbumsUseCase
import com.emanh.rootapp.domain.usecase.PlaylistsUseCase
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefPlaylistUseCase
import com.emanh.rootapp.presentation.navigation.AlbumScreenNavigation
import com.emanh.rootapp.presentation.navigation.ArtistScreenNavigation
import com.emanh.rootapp.presentation.navigation.PlaylistScreenNavigation
import com.emanh.rootapp.presentation.navigation.SingleScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.utils.MyConstant.ALBUM_TYPE
import com.emanh.rootapp.utils.MyConstant.ARTIST_TYPE
import com.emanh.rootapp.utils.MyConstant.PLAYLIST_TYPE
import com.emanh.rootapp.utils.MyConstant.SINGLE_TYPE
import com.emanh.rootapp.utils.MyConstant.VIEW_ALL_HISTORY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRouter: AppRouter,
    private val songsUseCase: SongsUseCase,
    private val albumsUseCase: AlbumsUseCase,
    private val playlistsUseCase: PlaylistsUseCase,
    private val usersUseCase: UsersUseCase,
    private val crossRefPlaylistUseCase: CrossRefPlaylistUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getRecommendedSongs()
        getRecentlyListenedSongs()
        getTrendingSongs()
        getQuickPlaylists()
        getYourTopMixes()
        getRadioForYou()
        getPlaylistCard()
        getYourFavoriteArtists()
        getSimilarContent()
    }

    fun onQuickPlayClick(id: Long, type: String) {
        if (type == ALBUM_TYPE) {
            goToAlbum(id)
        } else if (type == PLAYLIST_TYPE) {
            goToPlaylist(id)
        }
    }

    fun onSimilarClick(id: Long, type: String) {
        if (type == ALBUM_TYPE) {
            goToAlbum(id)
        } else if (type == SINGLE_TYPE) {
            goToSingle(id)
        } else if (type == ARTIST_TYPE) {
            goToArtist(id)
        }
    }

    fun goToPlaylist(playlistId: Long) {
        appRouter.getNavController()?.navigateTo(PlaylistScreenNavigation.getRoute(playlistId))
    }

    fun goToAlbum(albumId: Long) {
        appRouter.getNavController()?.navigateTo(AlbumScreenNavigation.getRoute(albumId))
    }

    fun goToSingle(singleId: Long) {
        appRouter.getNavController()?.navigateTo(SingleScreenNavigation.getRoute(singleId))
    }

    fun goToArtist(artistId: Long) {
        appRouter.getNavController()?.navigateTo(ArtistScreenNavigation.getRoute(artistId))
    }

    fun onViewAllHistory() {
        appRouter.getNavController()?.navigateTo(PlaylistScreenNavigation.getRoute(VIEW_ALL_HISTORY))
    }

    private fun getRecommendedSongs() {
        val userIdFake = 2L
        _uiState.update { it.copy(isLoading = true) }

        songsUseCase.getRecommendedSongs(userIdFake).onEach { songsList ->
            _uiState.update { currentState ->
                currentState.copy(recommendedSongs = songsList, isLoading = false)
            }
        }.catch { error ->
            Log.e(TAG, "Error fetching RecommendedSongs: $error")
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private fun getRecentlyListenedSongs() {
        val userIdFake = 2L
        _uiState.update { it.copy(isLoading = true) }

        songsUseCase.getRecentlyListenedSongs(userIdFake).onEach { songsList ->
            _uiState.update { currentState ->
                currentState.copy(recentlyListenedSongs = songsList, isLoading = false)
            }
        }.catch { error ->
            Log.e(TAG, "Error fetching RecentlyListenedSongs: $error")
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private fun getTrendingSongs() {
        _uiState.update { it.copy(isLoading = true) }

        songsUseCase.getTrendingSongs().onEach { songsList ->
            _uiState.update { currentState ->
                currentState.copy(trendingSongs = songsList, isLoading = false)
            }
        }.catch { error ->
            Log.e(TAG, "Error fetching TrendingSongs: $error")
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private fun getQuickPlaylists() {
        val userIdFake = 2L
        _uiState.update { it.copy(isLoading = true) }

        playlistsUseCase.getQuickPlaylist(userIdFake).zip(albumsUseCase.getQuickAlbum()) { playlistSongs, albumSongs ->
            albumSongs + playlistSongs
        }.onEach { combinedSongs ->
            _uiState.update { currentState ->
                currentState.copy(quickPlaylistsList = combinedSongs, isLoading = false)
            }
        }.catch { error ->
            Log.e(TAG, "Error fetching QuickPlaylists: $error")
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private fun getYourTopMixes() {
        _uiState.update { it.copy(isLoading = true) }

        crossRefPlaylistUseCase.getYourTopMixes().onEach { playlists ->
            _uiState.update { currentState ->
                currentState.copy(yourTopMixesPlaylist = playlists, isLoading = false)
            }
        }.catch { error ->
            Log.e(TAG, "Error fetching YourTopMixes: $error")
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private fun getRadioForYou() {
        _uiState.update { it.copy(isLoading = true) }

        playlistsUseCase.getRadioForYou().onEach { playlist ->
            _uiState.update { currentState ->
                currentState.copy(radioForYouPlaylist = playlist, isLoading = false)
            }
        }.catch { error ->
            Log.e(TAG, "Error fetching RadioForYou: $error")
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private fun getYourFavoriteArtists() {
        val userIdFake = 2L
        _uiState.update { it.copy(isLoading = true) }

        usersUseCase.getYourFavoriteArtists(userIdFake).onEach { artists ->
            _uiState.update { currentState ->
                currentState.copy(yourFavoriteArtists = artists, isLoading = false)
            }
        }.catch { error ->
            Log.e(TAG, "Error fetching YourFavoriteArtists: $error")
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private fun getSimilarContent() {
        val userIdFake = 2L
        _uiState.update { it.copy(isLoading = true) }

        usersUseCase.getSimilarArtists(userIdFake).zip(songsUseCase.getSimilarSongs()) { artists, songs ->
            artists to songs
        }.zip(albumsUseCase.getSimilarAlbums()) { (artists, songs), albums ->
            (artists + songs + albums).shuffled()
        }.onEach { combinedShuffledContent ->
            _uiState.update { currentState ->
                currentState.copy(similarContent = combinedShuffledContent, isLoading = false)
            }

            Log.d(TAG, "SimilarContent: ${_uiState.value.similarContent}")
        }.catch { error ->
            Log.e(TAG, "Error fetching SimilarContent: $error")
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }

    private fun getPlaylistCard() {
        _uiState.update { it.copy(isLoading = true) }

        crossRefPlaylistUseCase.getPlaylistCard().onEach { playlist ->
            _uiState.update { currentState ->
                currentState.copy(playlistCard = playlist, isLoading = false)
            }
            Log.d(TAG, "playlistCard: ${_uiState.value.playlistCard}")
        }.catch { error ->
            Log.e(TAG, "Error fetching PlaylistCard: $error")
            _uiState.update { it.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }
}