package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.artist.ArtistScreen

fun NavGraphBuilder.artistScreenGraph(currentUser: UserInfo, onItemClick: (Long, String) -> Unit,) {
    composable<AppNavigationRoute.Artist> {
        ArtistScreen(currentUser, onItemClick)
    }
}

object ArtistScreenNavigation {
    fun getRoute(artistId: Long = 0) = AppNavigationRoute.Artist(artistId)
}