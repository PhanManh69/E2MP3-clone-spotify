package com.emanh.rootapp.app

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.emanh.rootapp.presentation.composable.PlayerSticlyPreview
import com.emanh.rootapp.presentation.composable.navbar.STFTabbar
import com.emanh.rootapp.presentation.navigation.homeScreenGraph
import com.emanh.rootapp.presentation.navigation.loginScreenGraph
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.presentation.navigation.searchScreenGraph
import com.emanh.rootapp.presentation.navigation.testComposableScreenGraph
import com.emanh.rootapp.presentation.navigation.yourLibraryScreenGraph
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appRouter: AppRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        setImmersiveMode()
        super.onCreate(savedInstanceState)
        setContent {
            E2MP3Theme {
                RootNavScreen(appRouter = appRouter)
            }
        }
    }

    private fun setImmersiveMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }
}

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
            yourLibraryScreenGraph()
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PlayerSticlyPreview()

            STFTabbar(navController = navController)
        }
    }
}