package com.emanh.rootapp.presentation.ui.processing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProcessingViewModel @Inject constructor(
    private val appRouter: AppRouter,
    private val songUseCase: SongsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProcessingUiState())
    val uiState: StateFlow<ProcessingUiState> = _uiState.asStateFlow()

    fun goBack() {
        appRouter.getMainNavController()?.goBack()
    }

    fun getProcessingSongs(userId: Long) {
        viewModelScope.launch {
            songUseCase.getProcessingSongs(userId).collect { songs ->
                _uiState.update { it.copy(songsList = songs) }
            }
        }
    }
}