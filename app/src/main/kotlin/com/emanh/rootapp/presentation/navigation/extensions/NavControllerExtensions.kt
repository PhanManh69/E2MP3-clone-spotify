package com.emanh.rootapp.presentation.navigation.extensions

import androidx.navigation.NavController
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute

object NavActions {
    fun NavController.navigateTo(
        route: AppNavigationRoute, popUpToRoute: AppNavigationRoute? = null, inclusive: Boolean = false
    ) {
        this.navigate(route) {
            popUpToRoute?.let {
                popUpTo(it) {
                    this.inclusive = inclusive
                }
            }
        }
    }

    fun NavController.goBack() {
        if (this.previousBackStackEntry != null) {
            this.navigateUp()
        }
    }

    fun NavController.onPopBack() {
        this.popBackStack()
    }
} 