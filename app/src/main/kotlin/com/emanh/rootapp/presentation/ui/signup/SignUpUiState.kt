package com.emanh.rootapp.presentation.ui.signup

data class SignUpUiState(
    val isLoading: Boolean = false,
    val inputFullName: String = "",
    val inputUsername: String = "",
    val inputEmail: String = "",
    val inputPassword: String = "",
    val inputConfirmPassword: String = "",
    val errorMessage: String = "",
)