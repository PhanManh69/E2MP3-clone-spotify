package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.yourlibrary.YourLibraryScreen

fun NavGraphBuilder.yourLibraryScreenGraph(currentUser: UserInfo, onNavigationDrawerClick: () -> Unit) {
    composable<AppNavigationRoute.YourLibrary> {
        YourLibraryScreen(currentUser = currentUser, onNavigationDrawerClick = onNavigationDrawerClick)
    }
}