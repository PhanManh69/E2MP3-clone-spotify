package com.emanh.rootapp.presentation.ui.searchinput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SearchInputViewModel @Inject constructor(
    private val appRouter: AppRouter
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchInputState())
    val uiState: StateFlow<SearchInputState> = _uiState.asStateFlow()

    fun goToBack() {
        appRouter.getNavController()?.goBack()
    }

    fun updateMessage(message: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(currentMessage = message) }
        }
    }
}