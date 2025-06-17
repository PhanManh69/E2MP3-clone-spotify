package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.playlist.PlaylistScreen

fun NavGraphBuilder.playlistScreenGraph(currentUser: UserInfo, onItemClick: (Long, String) -> Unit) {
    composable<AppNavigationRoute.Playlist> { 
        PlaylistScreen(currentUser, onItemClick)
    }
}

object PlaylistScreenNavigation {
    fun getRoute(playlistId: Long = 0) = AppNavigationRoute.Playlist(playlistId)
}