package com.emanh.rootapp.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.emanh.rootapp.presentation.composable.PlayerSticlyPreview
import com.emanh.rootapp.presentation.composable.navbar.STFTabbar
import com.emanh.rootapp.presentation.navigation.homeScreenGraph
import com.emanh.rootapp.presentation.navigation.loginScreenGraph
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.presentation.navigation.searchInputScreenGraph
import com.emanh.rootapp.presentation.navigation.searchScreenGraph
import com.emanh.rootapp.presentation.navigation.testComposableScreenGraph
import com.emanh.rootapp.presentation.navigation.yourLibraryScreenGraph

@Composable
fun RootNavScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    appRouter: AppRouter,
) {
    DisposableEffect(navController) {
        appRouter.bind(navController)
        onDispose {
            appRouter.unbind()
        }
    }

    Box(modifier = modifier) {
        NavHost(modifier = Modifier, navController = navController, startDestination = AppNavigationRoute.Home, enterTransition = {
            fadeIn(animationSpec = tween(100))
        }, exitTransition = {
            fadeOut(animationSpec = tween(100))
        }, popEnterTransition = {
            fadeIn(animationSpec = tween(100))
        }, popExitTransition = {
            fadeOut(animationSpec = tween(100))
        }) {
            testComposableScreenGraph()
            loginScreenGraph()
            homeScreenGraph()
            searchScreenGraph()
            searchInputScreenGraph()
            yourLibraryScreenGraph()
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PlayerSticlyPreview()

            STFTabbar(navController = navController)
        }
    }
}