package com.emanh.rootapp.presentation.ui.artist.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body5Regular
import com.emanh.rootapp.presentation.theme.Body7Bold
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.SurfaceAlphaTabbar
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary

@Composable
fun ArtistAbout(modifier: Modifier = Modifier, avatarUrl: String, viewMonth: Long) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }

    Box(modifier = modifier.clip(shape = RoundedCornerShape(8.dp))) {
        if (!isLoadFailed) {
            AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .align(Alignment.Center)
                        .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatarUrl)
                        .crossfade(true)
                        .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> })
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
            )
        } else {
            Box(modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(color = IconInvert, shape = RoundedCornerShape(8.dp))
                .clip(shape = RoundedCornerShape(8.dp))
                .align(Alignment.Center)) {
                Image(painter = painterResource(R.drawable.img_loading_failed),
                      contentDescription = null,
                      contentScale = ContentScale.Crop,
                      modifier = Modifier.padding(16.dp))
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .align(Alignment.TopCenter)
            .background(brush = Brush.verticalGradient(0f to SurfaceAlphaTabbar, 1f to Color.Transparent)))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .align(Alignment.BottomCenter)
            .background(brush = Brush.verticalGradient(0f to Color.Transparent, 1f to SurfaceAlphaTabbar)))

        Row(modifier = Modifier
            .padding(18.dp)
            .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(painter = painterResource(R.drawable.ic_12_ribbon), contentDescription = null, tint = Color.Unspecified)

            Text(text = stringResource(R.string.verified_artist), color = TextBackgroundPrimary, style = Body7Bold)
        }

        Text(text = "$viewMonth ${stringResource(R.string.monthly_listeners)}",
             color = TextBackgroundPrimary,
             style = Body5Regular,
             modifier = Modifier
                 .padding(18.dp)
                 .align(Alignment.BottomStart))
    }
}