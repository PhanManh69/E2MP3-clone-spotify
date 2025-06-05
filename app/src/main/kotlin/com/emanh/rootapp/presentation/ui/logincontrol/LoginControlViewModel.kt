package com.emanh.rootapp.presentation.ui.logincontrol

import androidx.lifecycle.ViewModel
import com.emanh.rootapp.presentation.navigation.LoginScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginControlViewModel @Inject constructor(
    private val appRouter: AppRouter,
) : ViewModel() {

    fun onLoginClick() {
        appRouter.getAuthNavController()?.navigateTo(LoginScreenNavigation.getRoute())
    }
}