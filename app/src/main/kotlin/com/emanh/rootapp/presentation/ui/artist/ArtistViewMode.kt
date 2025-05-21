package com.emanh.rootapp.presentation.ui.artist

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.GenresUseCase
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.domain.usecase.ViewsSongUseCase
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
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArtistUiState())
    val uiState: StateFlow<ArtistUiState> = _uiState.asStateFlow()

    private val artistId: Int = savedStateHandle.get<Int>("artistId") ?: -1

    init {
        if (artistId != -1) {
            Log.d(TAG, "Initializing with albumId: $artistId")

            loadArtistById(artistId)
            loadSongsByArtist(artistId)
            loadGenreNameByArtist(artistId)
            loadListenerMonth(artistId)
        }
    }

    fun onBackClick() {
        appRouter.getNavController()?.goBack()
    }

    @Composable
    fun getGenreName(genreNameList: List<Int>): String {
        val names = genreNameList.map { id -> stringResource(id) }
        return names.joinToString(" • ")
    }


    private fun loadArtistById(artistId: Int) {
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

    private fun loadSongsByArtist(artistId: Int) {
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

    private fun loadGenreNameByArtist(artistId: Int) {
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

    private fun loadListenerMonth(artistId: Int) {
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