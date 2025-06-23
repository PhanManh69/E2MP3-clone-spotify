package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.processing.ProcessingScreen

fun NavGraphBuilder.processingScreenGraph(currentUser: UserInfo) {
    composable<AppNavigationRoute.Processing> {
        ProcessingScreen(currentUser)
    }
}

object ProcessingScreenNavigation {
    fun getRoute() = AppNavigationRoute.Processing
}