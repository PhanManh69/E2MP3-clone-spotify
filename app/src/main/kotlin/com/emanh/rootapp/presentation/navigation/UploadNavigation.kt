package com.emanh.rootapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.ui.upload.UploadScreen

fun NavGraphBuilder.uploadScreenGraph(currentUser: UserInfo) {
    composable<AppNavigationRoute.Upload> {
        UploadScreen(currentUser)
    }
}

object UploadScreenNavigation {
    fun getRoute() = AppNavigationRoute.Upload
}