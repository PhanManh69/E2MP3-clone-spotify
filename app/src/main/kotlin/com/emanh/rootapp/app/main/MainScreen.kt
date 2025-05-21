package com.emanh.rootapp.app.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.emanh.rootapp.presentation.composable.STFPlayerSticky
import com.emanh.rootapp.presentation.composable.navbar.STFTabbar
import com.emanh.rootapp.presentation.navigation.albumScreenGraph
import com.emanh.rootapp.presentation.navigation.artistScreenGraph
import com.emanh.rootapp.presentation.navigation.homeScreenGraph
import com.emanh.rootapp.presentation.navigation.loginScreenGraph
import com.emanh.rootapp.presentation.navigation.playlistScreenGraph
import com.emanh.rootapp.presentation.navigation.route.AppNavigationRoute
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.presentation.navigation.searchInputScreenGraph
import com.emanh.rootapp.presentation.navigation.searchScreenGraph
import com.emanh.rootapp.presentation.navigation.singleScreenGraph
import com.emanh.rootapp.presentation.navigation.testComposableScreenGraph
import com.emanh.rootapp.presentation.navigation.yourLibraryScreenGraph
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.rootapp.presentation.composable.STFPlayerStickyLoading
import com.emanh.rootapp.presentation.ui.player.PlayerScreen
import com.emanh.rootapp.presentation.ui.player.PlayerViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier, appRouter: AppRouter, navController: NavHostController = rememberNavController()
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val mainUiState by mainViewModel.uiState.collectAsState()
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val playerUiState by playerViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

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
            playlistScreenGraph(onItemClick = { songId, title ->
                mainViewModel.getSongId(songId)
                mainViewModel.getTitleFromItem("Playlist", title)
            })
            albumScreenGraph(onItemClick = { songId, title ->
                mainViewModel.getSongId(songId)
                mainViewModel.getTitleFromItem("Album", title)
            })
            singleScreenGraph(onItemClick = { songId, title ->
                mainViewModel.getSongId(songId)
                mainViewModel.getTitleFromItem("Bài hát", title)
            })
            artistScreenGraph(onItemClick = { songId, title ->
                mainViewModel.getSongId(songId)
                mainViewModel.getTitleFromItem("Nghệ sĩ", title)
            })
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (mainUiState.single != null) {
                if (mainUiState.isLoading) {
                    STFPlayerStickyLoading()
                } else {
                    STFPlayerSticky(song = mainUiState.single!!,
                                    isPlayed = mainUiState.isPlayed,
                                    currentProgress = mainUiState.currentProgress,
                                    onMusicalClick = {},
                                    onPlayerStickyClick = playerViewModel::showPlayer,
                                    onPlayPauseClick = {
                                        mainViewModel.onPlayPauseClick(isPlayed = it, scope = scope)
                                    })
                }

                if (playerUiState.showPlayerSheet) {
                    PlayerScreen(headerTitle = mainUiState.headerTitle,
                                 headerSubtitle = mainUiState.headerSubtitle,
                                 totalDuration = mainUiState.timeline,
                                 currentProgress = mainUiState.currentProgress,
                                 isPlayed = mainUiState.isPlayed,
                                 song = mainUiState.single!!,
                                 artistsList = mainUiState.artistsList,
                                 onPlayPauseClick = {
                                     mainViewModel.onPlayPauseClick(isPlayed = it, scope = scope)
                                 },
                                 onValueChange = { mainViewModel.onValueTimeLineChange(it) },
                                 onValueChangeFinished = { mainViewModel.onSliderPositionChangeFinished(it, scope) })
                }
            }

            STFTabbar(navController = navController)
        }
    }
}