package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.home.HomeScreen

fun NavGraphBuilder.homeScreenGraph() {
    composable<AppNavigationRoute.Home> {
        HomeScreen()
    }
}

object HomeScreenNavigation {
    fun getRoute() = AppNavigationRoute.Home
}