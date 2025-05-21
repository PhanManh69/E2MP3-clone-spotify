package com.emanh.rootapp.presentation.ui.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.MusixmatchUseCase
import com.emanh.rootapp.domain.usecase.ViewsSongUseCase
import com.emanh.rootapp.presentation.navigation.ArtistScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PlayerViewModel"

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val appRouter: AppRouter,
    private val viewsSongUseCase: ViewsSongUseCase,
    private val musixmatchUseCase: MusixmatchUseCase,
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

    fun goToArtist(artistId: Int) {
        appRouter.getNavController()?.navigateTo(ArtistScreenNavigation.getRoute(artistId))
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

    fun loadListenerMonth(artistId: Int) {
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