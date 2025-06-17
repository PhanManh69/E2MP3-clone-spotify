package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.revenuedetails.RevenueDetailScreen

fun NavGraphBuilder.revenueDetailsScreenGraph() {
    composable<AppNavigationRoute.RevenueDetail> {
        RevenueDetailScreen()
    }
}

object RevenueDetailsScreenNavigation {
    fun getRoute(songId: Long = 0) = AppNavigationRoute.RevenueDetail(songId)
}