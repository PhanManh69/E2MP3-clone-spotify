package com.emanh.rootapp.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.UserSessionUseCase
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.presentation.navigation.LoginNotPasswordScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appRouter: AppRouter,
    private val usersUseCase: UsersUseCase,
    private val userSessionUseCase: UserSessionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun goBack() {
        appRouter.getAuthNavController()?.goBack()
    }

    fun onNotPasswordClick() {
        appRouter.getAuthNavController()?.navigateTo(LoginNotPasswordScreenNavigation.getRoute())
        _uiState.update { it.copy(account = "", password = "", isLoggedIn = false, errorMessage = null) }
    }

    fun onInputAccountChange(inputAccount: String) {
        _uiState.update { it.copy(account = inputAccount, errorMessage = null) }
    }

    fun onInputPasswordChange(inputPassword: String) {
        _uiState.update { it.copy(password = inputPassword, errorMessage = null) }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(isLoading = true, isLoggedIn = false, errorMessage = null)
                }

                val user = usersUseCase.getGetUserLogin(account = _uiState.value.account.trim(), password = _uiState.value.password)

                if (user != null) {
                    userSessionUseCase.saveUserSession(user)
                    _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, isLoggedIn = false, errorMessage = "Tài khoản hoặc mật khẩu không đúng")
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, isLoggedIn = false, errorMessage = "Đã xảy ra lỗi. Vui lòng thử lại")
                }

                Log.e(TAG, "Login error", e)
            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}