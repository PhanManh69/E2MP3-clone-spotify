package com.emanh.rootapp.presentation.navigation.extensions

import androidx.navigation.NavController
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute

object NavActions {
    fun NavController.navigateTo(route: AppNavigationRoute) {
        this.navigate(route)
    }

    fun NavController.goBack() {
        if (this.previousBackStackEntry != null) {
            this.navigateUp()
        }
    }
} 