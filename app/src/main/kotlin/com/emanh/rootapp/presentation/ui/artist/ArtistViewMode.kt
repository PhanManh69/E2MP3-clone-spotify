package com.emanh.rootapp.presentation.ui.artist

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity
import com.emanh.rootapp.domain.usecase.GenresUseCase
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.domain.usecase.ViewsSongUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefUserUseCase
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ArtistViewModel"

@HiltViewModel
class ArtistViewMode @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRouter: AppRouter,
    private val usersUseCase: UsersUseCase,
    private val songsUseCase: SongsUseCase,
    private val genresUseCase: GenresUseCase,
    private val viewsSongUseCase: ViewsSongUseCase,
    private val crossRefUserUseCase: CrossRefUserUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArtistUiState())
    val uiState: StateFlow<ArtistUiState> = _uiState.asStateFlow()

    private val artistId: Long = savedStateHandle.get<Long>("artistId") ?: -1

    private var currentUserId: Long = -1

    init {
        if (artistId != -1L) {
            Log.d(TAG, "Initializing with albumId: $artistId")

            loadArtistById(artistId)
            loadSongsByArtist(artistId)
            loadGenreNameByArtist(artistId)
            loadListenerMonth(artistId)
        }
    }

    fun setCurrentUserId(userId: Long) {
        currentUserId = userId

        if (artistId != -1L && currentUserId != -1L) {
            getUserFollowing(artistId, currentUserId)
        }
    }

    fun onBackClick() {
        appRouter.getMainNavController()?.goBack()
    }

    @Composable
    fun getGenreName(genreNameList: List<Int>): String {
        val names = genreNameList.map { id -> stringResource(id) }
        return names.joinToString(" • ")
    }

    fun onFollowClick(currentUserId: Long) {
        viewModelScope.launch {
            if (_uiState.value.isFollowing) {
                crossRefUserUseCase.deleteUserFollwing(userFollowingEntity = UserFollowingEntity(userId = currentUserId, artistId = artistId))
                _uiState.update { it.copy(isFollowing = false) }
                return@launch
            } else {
                crossRefUserUseCase.insertUserFollwing(userFollowingEntity = UserFollowingEntity(userId = currentUserId, artistId = artistId))
                _uiState.update { it.copy(isFollowing = true) }
                return@launch
            }
        }
    }

    private fun getUserFollowing(artistId: Long, currentUserId: Long) {
        viewModelScope.launch {
            crossRefUserUseCase.getUserFollwing(userFollowingEntity = UserFollowingEntity(userId = currentUserId, artistId = artistId))
                .catch { error ->
                    Log.e(TAG, "Error fetching ArtistById: $error")
                }
                .collect {
                    val isFollwing = it != null
                    _uiState.update { it.copy(isFollowing = isFollwing) }
                }
        }
    }

    private fun loadArtistById(artistId: Long) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            usersUseCase.getArtistById(artistId).catch { error ->
                Log.e(TAG, "Error fetching ArtistById: $error")
                _uiState.update { it.copy(isLoading = false) }
            }.collect {
                _uiState.update { currentState ->
                    currentState.copy(artist = it, isLoading = false)
                }
            }
        }
    }

    private fun loadSongsByArtist(artistId: Long) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            songsUseCase.getSongsByArtist(artistId).catch { error ->
                Log.e(TAG, "Error loading songs: ${error.message}")
                _uiState.update { it.copy(isLoading = false) }
            }.collect {
                _uiState.update { currentState ->
                    currentState.copy(songsList = it, isLoading = false)
                }
            }
        }
    }

    private fun loadGenreNameByArtist(artistId: Long) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            genresUseCase.getGenreNameByArtist(artistId).catch { error ->
                Log.e(TAG, "Error loading genre name: ${error.message}")
                _uiState.update { it.copy(isLoading = false) }
            }.collect {
                _uiState.update { currentState ->
                    currentState.copy(genreNameList = it, isLoading = false)
                }
            }
        }
    }

    private fun loadListenerMonth(artistId: Long) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            viewsSongUseCase.getListenerMonth(artistId).catch { error ->
                Log.e(TAG, "Error loading listener month: ${error.message}")
                _uiState.update { it.copy(isLoading = false) }
            }.collect {
                _uiState.update { currentState ->
                    currentState.copy(viewsMonth = it, isLoading = false)
                }
            }
        }
    }
}