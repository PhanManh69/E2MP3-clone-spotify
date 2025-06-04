package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.createplaylist.CreatePlaylistScreen

fun NavGraphBuilder.createPlaylistScreenGraph() {
    composable<AppNavigationRoute.CreatePlaylist> {
        CreatePlaylistScreen()
    }
}

object CreatePlaylistScreenNavigation {
    fun getRoute() = AppNavigationRoute.CreatePlaylist
}