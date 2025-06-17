package com.emanh.rootapp.presentation.ui.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.data.db.entity.crossref.SongLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.usecase.MusixmatchUseCase
import com.emanh.rootapp.domain.usecase.ViewsSongUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefSongUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefUserUseCase
import com.emanh.rootapp.presentation.navigation.ArtistScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PlayerViewModel"

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val appRouter: AppRouter,
    private val viewsSongUseCase: ViewsSongUseCase,
    private val musixmatchUseCase: MusixmatchUseCase,
    private val crossRefSongUseCase: CrossRefSongUseCase,
    private val crossRefUserUseCase: CrossRefUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    fun showPlayer() {
        _uiState.update { it.copy(showPlayerSheet = true) }
    }

    fun hidePlayer() {
        _uiState.update { it.copy(showPlayerSheet = false) }
    }

    fun onDownPlayerClick() {
        hidePlayer()
    }

    fun showLyrics() {
        _uiState.update { it.copy(showLyricsSheet = true) }
    }

    fun hideLyrics() {
        _uiState.update { it.copy(showLyricsSheet = false) }
    }

    fun onDownLyricsClick() {
        hideLyrics()
    }

    fun onAddClick(songId: Long, currentUserId: Long) {
        viewModelScope.launch {
            if (_uiState.value.isAddSong) {
                crossRefSongUseCase.deleteSongLike(songLikeEntity = SongLikeEntity(songId = songId, userId = currentUserId))
                _uiState.update { it.copy(isAddSong = false) }
                return@launch
            } else {
                crossRefSongUseCase.insertSongLike(songLikeEntity = SongLikeEntity(songId = songId, userId = currentUserId))
                _uiState.update { it.copy(isAddSong = true) }
                return@launch
            }
        }
    }

    fun onFollowClick(artistId: Long, currentUserId: Long) {
        val isCurrentlyFollowing = _uiState.value.followingArtists.contains(artistId)

        viewModelScope.launch {
            try {
                if (isCurrentlyFollowing) {
                    crossRefUserUseCase.deleteUserFollwing(userFollowingEntity = UserFollowingEntity(userId = currentUserId, artistId = artistId))
                    _uiState.update { currentState ->
                        currentState.copy(followingArtists = currentState.followingArtists - artistId)
                    }
                    Log.d(TAG, "Unfollowed artist: $artistId")
                } else {
                    crossRefUserUseCase.insertUserFollwing(userFollowingEntity = UserFollowingEntity(userId = currentUserId, artistId = artistId))
                    _uiState.update { currentState ->
                        currentState.copy(followingArtists = currentState.followingArtists + artistId)
                    }
                    Log.d(TAG, "Followed artist: $artistId")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error toggling follow status for artist $artistId: $e")
            }
        }
    }

    fun goToArtist(artistId: Long) {
        appRouter.getMainNavController()?.navigateTo(ArtistScreenNavigation.getRoute(artistId))
    }

    fun getSongLike(songId: Long, currentUserId: Long) {
        viewModelScope.launch {
            crossRefSongUseCase.getSongLike(songLikeEntity = SongLikeEntity(songId = songId, userId = currentUserId)).catch { error ->
                Log.e(TAG, "Error fetching ArtistById: $error")
            }.collect {
                val isAdded = it != null
                _uiState.update { it.copy(isAddSong = isAdded) }
            }
        }
    }

    fun getUserFollowing(artistsList: List<UsersModel>, currentUserId: Long) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(followingArtists = setOf()) }

                val followingResults = artistsList.map { artist ->
                    async {
                        try {
                            crossRefUserUseCase.getUserFollwing(userFollowingEntity = UserFollowingEntity(userId = currentUserId, artistId = artist.id))
                                .firstOrNull()
                        } catch (e: Exception) {
                            Log.e(TAG, "Error fetching following status for artist ${artist.id}: $e")
                            null
                        }
                    }
                }

                val results = followingResults.awaitAll()
                val followingArtistIds = artistsList.zip(results).filter { (_, result) -> result != null }.map { (artist, _) -> artist.id }.toSet()

                _uiState.update { it.copy(followingArtists = followingArtistIds) }

            } catch (e: Exception) {
                Log.e(TAG, "Error in getUserFollowing: $e")
            }
        }
    }

    fun getLyrics(trackName: String, artistName: String) {
        if (trackName.isBlank() || artistName.isBlank()) {
            _uiState.update { it.copy(lyrics = "Lyrics not available", isLoading = false) }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val fullLyrics = musixmatchUseCase.getLyrics(trackName, artistName)
                val lyrics = fullLyrics?.replace(Regex("\\*\\*\\*\\*\\*\\*\\*.*", RegexOption.DOT_MATCHES_ALL), "")?.trim()

                _uiState.update {
                    it.copy(lyrics = lyrics ?: "Lyrics not available for this song", isLoading = false)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting lyrics: ${e.message}")
                _uiState.update {
                    it.copy(lyrics = "Unable to load lyrics at this time", isLoading = false)
                }
            }
        }
    }

    fun loadListenerMonth(artistId: Long) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            viewsSongUseCase.getListenerMonth(artistId).catch { error ->
                Log.e(TAG, "Error loading listener month: ${error.message}")
                _uiState.update { it.copy(isLoading = false) }
            }.collect { views ->
                _uiState.update { currentState ->
                    currentState.copy(viewMonthArtists = currentState.viewMonthArtists.toMutableMap().apply {
                        put(artistId, views)
                    }, isLoading = false)
                }
            }
        }
    }
}