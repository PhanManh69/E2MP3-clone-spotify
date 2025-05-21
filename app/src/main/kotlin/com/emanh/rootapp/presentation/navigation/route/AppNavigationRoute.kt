package com.emanh.rootapp.presentation.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class AppNavigationRoute {
    @Serializable
    data object Login : AppNavigationRoute()

    @Serializable
    data object Home : AppNavigationRoute()

    @Serializable
    data object Search : AppNavigationRoute()

    @Serializable
    data object YourLibrary : AppNavigationRoute()

    @Serializable
    data object SearchInput : AppNavigationRoute()

    @Serializable
    data class Playlist(val playlistId: Int = 0) : AppNavigationRoute()

    @Serializable
    data class Album(val albumId: Int = 0) : AppNavigationRoute()

    @Serializable
    data class Single(val singleId: Int = 0) : AppNavigationRoute()

    @Serializable
    data class Artist(val artistId: Int = 0) : AppNavigationRoute()

    @Serializable
    data object Player : AppNavigationRoute()

    @Serializable
    data object Lyrics : AppNavigationRoute()

    @Serializable
    data object TestComposable : AppNavigationRoute()
}