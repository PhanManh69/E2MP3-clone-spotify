package com.emanh.rootapp.presentation.ui.revenuedetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.SongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RevenueDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val songsUseCase: SongsUseCase,
) : ViewModel() {

    companion object {
        private const val TAG = "RevenueDetailViewModel"
    }

    private val _uiState = MutableStateFlow(RevenueDetailUiState())
    val uiState: StateFlow<RevenueDetailUiState> = _uiState.asStateFlow()

    private val songId: Long = savedStateHandle.get<Long>("songId") ?: -1

    init {
        if (songId != -1L) {
            Log.d(TAG, "Initializing with albumId: $songId")

            loadSongById(songId)
        }
    }

    private fun loadSongById(songId: Long) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            songsUseCase.getSongsById(songId).catch { error ->
                Log.e(TAG, "Error fetching SongById: $error")
                _uiState.update { it.copy(isLoading = false) }
            }.collect {
                _uiState.update { currentState ->
                    currentState.copy(song = it, isLoading = false)
                }
            }
        }
    }
}