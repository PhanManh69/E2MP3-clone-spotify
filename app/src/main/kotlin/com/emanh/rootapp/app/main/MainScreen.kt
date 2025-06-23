package com.emanh.rootapp.app.main

import androidx.annotation.OptIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
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
import com.emanh.rootapp.presentation.navigation.albumScreenGraph
import com.emanh.rootapp.presentation.navigation.artistScreenGraph
import com.emanh.rootapp.presentation.navigation.homeScreenGraph
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
import androidx.media3.common.util.UnstableApi
import com.emanh.rootapp.app.main.composable.MainBottomButton
import com.emanh.rootapp.app.main.composable.MainDrawerSheet
import com.emanh.rootapp.presentation.composable.STFLoading
import com.emanh.rootapp.presentation.navigation.createPlaylistScreenGraph
import com.emanh.rootapp.presentation.navigation.playlistYourScreenGraph
import com.emanh.rootapp.presentation.navigation.processingScreenGraph
import com.emanh.rootapp.presentation.navigation.revenueDetailsScreenGraph
import com.emanh.rootapp.presentation.navigation.revenueScreenGraph
import com.emanh.rootapp.presentation.navigation.uploadScreenGraph
import com.emanh.rootapp.presentation.theme.SurfaceTertiary
import com.emanh.rootapp.presentation.ui.player.PlayerViewModel
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appRouter: AppRouter,
    navController: NavHostController = rememberNavController(),
    onLogout: () -> Unit,
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val mainUiState by mainViewModel.uiState.collectAsState()
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val playerUiState by playerViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    DisposableEffect(navController) {
        appRouter.bindMain(navController)
        onDispose {
            appRouter.unbindMain()
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    if (mainUiState.currentUser == null) {
        STFLoading()
    } else {
        ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 48.dp), drawerContainerColor = SurfaceTertiary) {
                        MainDrawerSheet(
                                currentUser = mainUiState.currentUser!!,
                                onUploadClick = {
                                    mainViewModel.onUploadClick()
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                },
                                onRevenueClick = {
                                    mainViewModel.onRevenueClick()
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                },
                                onLogoutClick = onLogout,
                        )
                    }
                },
        ) {
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
                    homeScreenGraph(currentUser = mainUiState.currentUser!!, onNavigationDrawerClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    })
                    searchScreenGraph(currentUser = mainUiState.currentUser!!, onNavigationDrawerClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    })
                    yourLibraryScreenGraph(currentUser = mainUiState.currentUser!!, onNavigationDrawerClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    })
                    createPlaylistScreenGraph(currentUser = mainUiState.currentUser!!)
                    searchInputScreenGraph(currentUser = mainUiState.currentUser!!, onItemClick = { id, title ->
                        mainViewModel.getSongId(songId = id, currentUserId = mainUiState.currentUser!!.id)
                        mainViewModel.getTitleFromItem("Bài hát", title)
                    })
                    playlistScreenGraph(currentUser = mainUiState.currentUser!!, onItemClick = { id, title ->
                        mainViewModel.getSongId(songId = id, currentUserId = mainUiState.currentUser!!.id)
                        mainViewModel.getTitleFromItem("Playlist", title)
                    })
                    playlistYourScreenGraph(onItemClick = { id, title ->
                        mainViewModel.getSongId(songId = id, currentUserId = mainUiState.currentUser!!.id)
                        mainViewModel.getTitleFromItem("Playlist", title)
                    })
                    albumScreenGraph(currentUser = mainUiState.currentUser!!, onItemClick = { id, title ->
                        mainViewModel.getSongId(songId = id, currentUserId = mainUiState.currentUser!!.id)
                        mainViewModel.getTitleFromItem("Album", title)
                    })
                    singleScreenGraph(currentUser = mainUiState.currentUser!!, onItemClick = { id, title ->
                        mainViewModel.getSongId(songId = id, currentUserId = mainUiState.currentUser!!.id)
                        mainViewModel.getTitleFromItem("Bài hát", title)
                    })
                    artistScreenGraph(currentUser = mainUiState.currentUser!!, onItemClick = { id, title ->
                        mainViewModel.getSongId(songId = id, currentUserId = mainUiState.currentUser!!.id)
                        mainViewModel.getTitleFromItem("Nghệ sĩ", title)
                    })
                    uploadScreenGraph(currentUser = mainUiState.currentUser!!)
                    revenueScreenGraph(currentUser = mainUiState.currentUser!!)
                    processingScreenGraph(currentUser = mainUiState.currentUser!!)
                    revenueDetailsScreenGraph()
                }

                MainBottomButton(modifier = Modifier.align(Alignment.BottomCenter),
                                 currentProgress = mainUiState.currentProgress,
                                 timeline = mainUiState.timeline,
                                 headerTitle = mainUiState.headerTitle,
                                 headerSubtitle = mainUiState.headerSubtitle,
                                 isLoading = mainUiState.isLoading,
                                 isPlayed = mainUiState.isPlayed,
                                 showPlayerSheet = playerUiState.showPlayerSheet,
                                 currentUser = mainUiState.currentUser!!,
                                 single = mainUiState.single,
                                 navController = navController,
                                 artistsList = mainUiState.artistsList,
                                 onBackClick = {
                                     mainViewModel.onBackClick(mainUiState.currentUser!!.id)
                                 },
                                 onForwardClick = {
                                     mainViewModel.onForwardClick(mainUiState.currentUser!!.id)
                                 },
                                 onPlayerStickyClick = playerViewModel::showPlayer,
                                 onValueTimeLineChange = { mainViewModel.onValueTimeLineChange(it) },
                                 onSliderPositionChangeFinished = { mainViewModel.onSliderPositionChangeFinished(it, scope) },
                                 onPlayPauseClick = { mainViewModel.onPlayPauseClick(isPlayed = it, scope = scope) })
            }
        }
    }
}