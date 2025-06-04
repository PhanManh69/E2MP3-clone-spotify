package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.album.AlbumScreen

fun NavGraphBuilder.albumScreenGraph(onItemClick: (Long, String) -> Unit) {
    composable<AppNavigationRoute.Album> {
        AlbumScreen(onItemClick)
    }
}

object AlbumScreenNavigation {
    fun getRoute(albumId: Long = 0) = AppNavigationRoute.Album(albumId)
}