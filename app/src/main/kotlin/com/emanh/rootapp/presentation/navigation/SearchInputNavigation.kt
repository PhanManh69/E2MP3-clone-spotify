package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.searchinput.SearchInputScreen

fun NavGraphBuilder.searchInputScreenGraph(onItemClick: (Long, String) -> Unit) {
    composable<AppNavigationRoute.SearchInput> {
        SearchInputScreen(onItemClick)
    }
}

object SearchInputScreenNavigation {
    fun getRoute() = AppNavigationRoute.SearchInput
}