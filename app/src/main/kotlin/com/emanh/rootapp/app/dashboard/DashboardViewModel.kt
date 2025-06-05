package com.emanh.rootapp.app.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.UserSessionUseCase
import com.emanh.rootapp.domain.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val userSessionUseCase: UserSessionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            userSessionUseCase.isLoggedIn().first().let { isLoggedIn ->
                if (isLoggedIn) {
                    _uiState.update { it.copy(isLoggedIn = true, isLoading = false) }
                } else {
                    _uiState.update { it.copy(isLoggedIn = false, isLoading = false) }
                }
            }
        }
    }

    fun onLoginSuccess() {
        _uiState.update { it.copy(isLoggedIn = true) }
    }

    fun onLogout() {
        viewModelScope.launch {
            try {
                userSessionUseCase.logout()
                _uiState.value = _uiState.value.copy(isLoggedIn = false)
            } catch (e: Exception) {
                Log.e("DashboardViewModel", "Error during logout: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "DashboardViewModel"
    }
}