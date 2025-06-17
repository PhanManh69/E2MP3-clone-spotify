package com.emanh.rootapp.presentation.ui.revenue

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.presentation.navigation.RevenueDetailsScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
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

@HiltViewModel
class RevenueViewModel @Inject constructor(
    private val appRouter: AppRouter,
    private val songsUseCase: SongsUseCase,
) : ViewModel() {

    companion object {
        private const val TAG = "RevenueViewModel"
    }

    private val _uiState = MutableStateFlow(RevenueUiState())
    val uiState: StateFlow<RevenueUiState> = _uiState.asStateFlow()

    fun getYourSong(currentUserId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                songsUseCase.getSongsByArtist(currentUserId).catch { error ->
                    Log.e(TAG, "Error fetching YourSong: $error")
                    _uiState.update { it.copy(isLoading = false) }
                }.collect { listSongs ->
                    _uiState.update { it.copy(yourSong = listSongs, isLoading = false) }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in getYourSong: $e")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun goBack() {
        appRouter.getMainNavController()?.goBack()
    }

    fun onItemClick(id: Long) {
        appRouter.getMainNavController()?.navigateTo(RevenueDetailsScreenNavigation.getRoute(id))
    }
}