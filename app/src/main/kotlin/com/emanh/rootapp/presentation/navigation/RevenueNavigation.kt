package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.revenue.RevenueScreen

fun NavGraphBuilder.revenueScreenGraph(currentUser: UserInfo) {
    composable<AppNavigationRoute.Revenue> {
        RevenueScreen(currentUser)
    }
}

object RevenueScreenNavigation {
    fun getRoute() = AppNavigationRoute.Revenue
}