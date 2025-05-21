package com.emanh.rootapp.presentation.ui.player.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.emanh.rootapp.presentation.theme.SurfaceAlphaTabbar

@Composable
fun PlayerAvatar(modifier: Modifier = Modifier, avatarUrl: String?) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Box(modifier = modifier) {
        SubcomposeAsyncImage(model = avatarUrl,
                             contentDescription = null,
                             contentScale = ContentScale.Crop,
                             modifier = Modifier.fillMaxWidth().height(screenHeight + 32.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(statusBarHeight + 72.dp)
            .align(Alignment.TopCenter)
            .background(brush = Brush.verticalGradient(0f to SurfaceAlphaTabbar, 1f to Color.Transparent)))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(298.dp)
            .align(Alignment.BottomCenter)
            .background(brush = Brush.verticalGradient(0f to Color.Transparent, 1f to SurfaceAlphaTabbar)))
    }
}