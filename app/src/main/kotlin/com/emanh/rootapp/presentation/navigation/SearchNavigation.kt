package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.search.SearchScreen

fun NavGraphBuilder.searchScreenGraph(currentUser: UserInfo, onNavigationDrawerClick: () -> Unit) {
    composable<AppNavigationRoute.Search> {
        SearchScreen(currentUser = currentUser, onNavigationDrawerClick = onNavigationDrawerClick)
    }
}