package com.emanh.rootapp.presentation.ui.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val account: String = "",
    val password: String = "",
    val errorMessage: String? = null,
)