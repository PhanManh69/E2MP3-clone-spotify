package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.login.LoginScreen

fun NavGraphBuilder.loginScreenGraph() {
    composable<AppNavigationRoute.Login> {
        LoginScreen()
    }
}

object LoginScreenNavigation {
    fun getRoute() = AppNavigationRoute.Login
}