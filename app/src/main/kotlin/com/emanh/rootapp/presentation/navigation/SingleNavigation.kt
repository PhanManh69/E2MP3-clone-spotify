package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.single.SingleScreen

fun NavGraphBuilder.singleScreenGraph(onItemClick: (Int, String) -> Unit) {
    composable<AppNavigationRoute.Single> {
        SingleScreen(onItemClick)
    }
}

object SingleScreenNavigation {
    fun getRoute(singleId: Int = 0) = AppNavigationRoute.Single(singleId)
}