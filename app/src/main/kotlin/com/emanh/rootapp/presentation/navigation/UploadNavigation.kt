package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.upload.UploadScreen

fun NavGraphBuilder.uploadScreenGraph() {
    composable<AppNavigationRoute.Upload> {
        UploadScreen()
    }
}

object UploadScreenNavigation {
    fun getRoute() = AppNavigationRoute.Upload
}