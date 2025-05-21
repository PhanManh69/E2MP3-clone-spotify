package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage

@Composable
fun STFPlaylistAvatar(modifier: Modifier = Modifier, imageUrl: String, currentImageSize: Dp, imageAlpha: Float, imageScale: Float) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(currentImageSize)) {
        SubcomposeAsyncImage(model = imageUrl,
                             contentDescription = null,
                             contentScale = ContentScale.Crop,
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(horizontal = 48.dp)
                                 .aspectRatio(ratio = 1f)
                                 .size(currentImageSize)
                                 .align(Alignment.Center)
                                 .alpha(imageAlpha)
                                 .graphicsLayer {
                                     scaleX = imageScale
                                     scaleY = imageScale
                                 }
                                 .clip(shape = RoundedCornerShape(8.dp)))
    }
}