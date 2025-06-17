package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.login.LoginScreen

fun NavGraphBuilder.loginScreenGraph(onLoginSuccess: () -> Unit) {
    composable<AppNavigationRoute.Login> {
        LoginScreen(onLoginSuccess)
    }
}

object LoginScreenNavigation {
    fun getRoute() = AppNavigationRoute.Login
}