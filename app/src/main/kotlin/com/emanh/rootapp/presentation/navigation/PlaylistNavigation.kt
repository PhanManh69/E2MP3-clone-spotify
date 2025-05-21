package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.playlist.PlaylistScreen

fun NavGraphBuilder.playlistScreenGraph(onItemClick: (Int, String) -> Unit) {
    composable<AppNavigationRoute.Playlist> { 
        PlaylistScreen(onItemClick)
    }
}

object PlaylistScreenNavigation {
    fun getRoute(playlistId: Int = 0) = AppNavigationRoute.Playlist(playlistId)
}