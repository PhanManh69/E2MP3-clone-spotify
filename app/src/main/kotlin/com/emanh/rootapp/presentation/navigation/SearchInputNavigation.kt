package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.searchinput.SearchInputScreen

fun NavGraphBuilder.searchInputScreenGraph(currentUser: UserInfo, onItemClick: (Long, String) -> Unit) {
    composable<AppNavigationRoute.SearchInput> {
        SearchInputScreen(currentUser, onItemClick)
    }
}

object SearchInputScreenNavigation {
    fun getRoute() = AppNavigationRoute.SearchInput
}