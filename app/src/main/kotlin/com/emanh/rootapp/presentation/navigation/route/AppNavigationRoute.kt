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
    data object TestComposable : AppNavigationRoute()
}