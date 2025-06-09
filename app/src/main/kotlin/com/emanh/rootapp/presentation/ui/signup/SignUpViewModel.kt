package com.emanh.rootapp.presentation.ui.signup

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.presentation.navigation.LoginScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.utils.removeAccents
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appRouter: AppRouter,
    private val usersUseCase: UsersUseCase,
) : ViewModel() {

    companion object {
        private const val TAG = "SignUpViewModel"
    }

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun goBack() {
        appRouter.getAuthNavController()?.goBack()
    }


    fun onSignUpClick() {
        viewModelScope.launch {
            val uiState = _uiState.value
            val fullName = uiState.inputFullName.trim()
            val username = uiState.inputUsername.trim()
            val email = uiState.inputEmail.trim()
            val password = uiState.inputPassword
            val confirmPassword = uiState.inputConfirmPassword

            _uiState.update { it.copy(errorMessage = "", isLoading = true) }

            if (!validateInputs(fullName, username, email, password, confirmPassword)) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            if (password != confirmPassword) {
                _uiState.update { it.copy(errorMessage = context.getString(R.string.passwords_do_not_match), isLoading = false) }
                return@launch
            }

            try {
                val existingUserByUsername = usersUseCase.getUsername(username).first()
                if (existingUserByUsername != null) {
                    _uiState.update { it.copy(errorMessage = context.getString(R.string.username_already_exists), isLoading = false) }
                    return@launch
                }

                val existingUserByEmail = usersUseCase.getEmail(email).first()
                if (existingUserByEmail != null) {
                    _uiState.update { it.copy(errorMessage = context.getString(R.string.email_already_exists), isLoading = false) }
                    return@launch
                }

                val user = UsersModel(
                        username = username,
                        email = email,
                        password = password,
                        name = fullName,
                        normalizedSearchValue = fullName.removeAccents(),
                )

                usersUseCase.insertUser(user)
                appRouter.getAuthNavController()?.navigateTo(LoginScreenNavigation.getRoute())
                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                Log.e(TAG, "onSignUpClick: ", e)
                _uiState.update { it.copy(errorMessage = context.getString(R.string.signup_failed), isLoading = false) }
            }
        }
    }

    fun onLoginClick() {
        appRouter.getAuthNavController()?.navigateTo(LoginScreenNavigation.getRoute())
    }

    fun onInputFullNameChange(inputText: String) {
        _uiState.update { it.copy(inputFullName = inputText) }
    }

    fun onInputUsernameChange(inputText: String) {
        _uiState.update { it.copy(inputUsername = inputText) }
    }

    fun onInputEmailChange(inputText: String) {
        _uiState.update { it.copy(inputEmail = inputText) }
    }

    fun onInputPasswordChange(inputText: String) {
        _uiState.update { it.copy(inputPassword = inputText) }
    }

    fun onInputConfirmPasswordChange(inputText: String) {
        _uiState.update { it.copy(inputConfirmPassword = inputText) }
    }

    private fun validateInputs(
        fullName: String, username: String, email: String, password: String, confirmPassword: String
    ): Boolean {
        return when {
            fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                _uiState.update { it.copy(errorMessage = context.getString(R.string.please_fill_in_all_required_fields)) }
                false
            }

            !isValidEmail(email) -> {
                _uiState.update { it.copy(errorMessage = context.getString(R.string.invalid_email_format)) }
                false
            }

            password.length < 6 -> {
                _uiState.update { it.copy(errorMessage = context.getString(R.string.password_too_short)) }
                false
            }

            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}