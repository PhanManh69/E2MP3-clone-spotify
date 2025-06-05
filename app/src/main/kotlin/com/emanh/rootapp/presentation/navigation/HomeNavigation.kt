package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.home.HomeScreen

fun NavGraphBuilder.homeScreenGraph(currentUser: UserInfo, onLogoutClick: () -> Unit) {
    composable<AppNavigationRoute.Home> {
        HomeScreen(currentUser, onLogoutClick)
    }
}