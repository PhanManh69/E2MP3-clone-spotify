package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.playlistyour.PlaylistYourScreen

fun NavGraphBuilder.playlistYourScreenGraph(onItemClick: (Long, String) -> Unit) {
    composable<AppNavigationRoute.PlaylistYour> {
        PlaylistYourScreen(onItemClick)
    }
}

object PlaylistYourScreenNavigation {
    fun getRoute(playlistId: Long = 0) = AppNavigationRoute.PlaylistYour(playlistId)
}