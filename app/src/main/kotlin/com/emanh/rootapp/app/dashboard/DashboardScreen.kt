package com.emanh.rootapp.app.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.rootapp.app.auth.AuthScreen
import com.emanh.rootapp.app.main.MainScreen
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceSecondaryInvert

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier, appRouter: AppRouter
) {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(SurfacePrimary)) {
            CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.Center),
                    color = SurfaceProduct,
                    trackColor = SurfaceSecondaryInvert,
            )
        }
    } else {
        if (uiState.isLoggedIn) {
            MainScreen(modifier = modifier, appRouter = appRouter, onLogout = viewModel::onLogout)
        } else {
            AuthScreen(modifier = modifier, appRouter = appRouter, onLoginSuccess = viewModel::onLoginSuccess)
        }
    }
}