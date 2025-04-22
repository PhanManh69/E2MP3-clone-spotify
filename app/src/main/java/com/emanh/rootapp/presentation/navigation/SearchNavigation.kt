package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.search.SearchScreen

fun NavGraphBuilder.searchScreenGraph() {
    composable<AppNavigationRoute.Search> {
        SearchScreen()
    }
}

object SearchScreenNavigation {
    fun getRoute() = AppNavigationRoute.Search
}