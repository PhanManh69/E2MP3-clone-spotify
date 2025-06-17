package com.emanh.rootapp.app.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.rootapp.app.auth.AuthScreen
import com.emanh.rootapp.app.main.MainScreen
import com.emanh.rootapp.presentation.composable.STFLoading
import com.emanh.rootapp.presentation.navigation.router.AppRouter

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier, appRouter: AppRouter
) {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        STFLoading()
    } else {
        if (uiState.isLoggedIn) {
            MainScreen(modifier = modifier, appRouter = appRouter, onLogout = viewModel::onLogout)
        } else {
            AuthScreen(modifier = modifier, appRouter = appRouter, onLoginSuccess = viewModel::onLoginSuccess)
        }
    }
}