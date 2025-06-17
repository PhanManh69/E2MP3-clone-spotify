package com.emanh.rootapp.presentation.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class AppNavigationRoute {

    @Serializable
    data object Home : AppNavigationRoute()

    @Serializable
    data object Search : AppNavigationRoute()

    @Serializable
    data object YourLibrary : AppNavigationRoute()

    @Serializable
    data object SearchInput : AppNavigationRoute()

    @Serializable
    data object CreatePlaylist : AppNavigationRoute()

    @Serializable
    data object LoginControl : AppNavigationRoute()

    @Serializable
    data object Login : AppNavigationRoute()

    @Serializable
    data object LoginNotPassword : AppNavigationRoute()

    @Serializable
    data object SignUp : AppNavigationRoute()

    @Serializable
    data object Revenue : AppNavigationRoute()

    @Serializable
    data class RevenueDetail(val songId: Long = 0) : AppNavigationRoute()

    @Serializable
    data object Upload : AppNavigationRoute()

    @Serializable
    data class Playlist(val playlistId: Long = 0) : AppNavigationRoute()

    @Serializable
    data class PlaylistYour(val playlistId: Long = 0) : AppNavigationRoute()

    @Serializable
    data class Album(val albumId: Long = 0) : AppNavigationRoute()

    @Serializable
    data class Single(val singleId: Long = 0) : AppNavigationRoute()

    @Serializable
    data class Artist(val artistId: Long = 0) : AppNavigationRoute()

    @Serializable
    data object TestComposable : AppNavigationRoute()
}