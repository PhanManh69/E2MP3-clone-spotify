package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.loginnotpassword.LoginNotPasswordScreen

fun NavGraphBuilder.loginNotPasswordScreenGraph() {
    composable<AppNavigationRoute.LoginNotPassword> {
        LoginNotPasswordScreen()
    }
}

object LoginNotPasswordScreenNavigation {
    fun getRoute() = AppNavigationRoute.LoginNotPassword
}