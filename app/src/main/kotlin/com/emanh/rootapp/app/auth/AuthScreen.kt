package com.emanh.rootapp.app.auth

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.emanh.rootapp.presentation.navigation.loginControlScreenGraph
import com.emanh.rootapp.presentation.navigation.loginNotPasswordScreenGraph
import com.emanh.rootapp.presentation.navigation.loginScreenGraph
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.navigation.router.AppRouter

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    appRouter: AppRouter,
    navController: NavHostController = rememberNavController(),
    onLoginSuccess: () -> Unit,
) {
    DisposableEffect(navController) {
        appRouter.bindAuth(navController)
        onDispose {
            appRouter.unbindAuth()
        }
    }

    NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = AppNavigationRoute.LoginControl,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }
    ) {
        loginControlScreenGraph()
        loginScreenGraph(onLoginSuccess = onLoginSuccess)
        loginNotPasswordScreenGraph()
    }
}