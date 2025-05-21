package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.artist.ArtistScreen

fun NavGraphBuilder.artistScreenGraph(onItemClick: (Int, String) -> Unit,) {
    composable<AppNavigationRoute.Artist> {
        ArtistScreen(onItemClick)
    }
}

object ArtistScreenNavigation {
    fun getRoute(artistId: Int = 0) = AppNavigationRoute.Artist(artistId)
}