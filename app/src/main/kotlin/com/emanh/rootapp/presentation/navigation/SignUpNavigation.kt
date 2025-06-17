package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.signup.SignUpScreen

fun NavGraphBuilder.signUpScreenGraph() {
    composable<AppNavigationRoute.SignUp> {
        SignUpScreen()
    }
}

object SignUpScreenNavigation {
    fun getRoute() = AppNavigationRoute.SignUp
}