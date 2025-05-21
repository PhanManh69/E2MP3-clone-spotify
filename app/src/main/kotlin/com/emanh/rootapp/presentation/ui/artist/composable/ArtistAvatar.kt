package com.emanh.rootapp.presentation.ui.artist.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.emanh.rootapp.presentation.theme.SurfaceAlphaTabbar

@Composable
fun ArtistAvatar(modifier: Modifier = Modifier, avatarUrl: String?) {
    Box(modifier = modifier) {
        SubcomposeAsyncImage(model = avatarUrl,
                             contentDescription = null,
                             contentScale = ContentScale.Crop,
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .height(288.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(288.dp)
            .background(brush = Brush.verticalGradient(0f to Color.Transparent, 1f to SurfaceAlphaTabbar)))
    }
}