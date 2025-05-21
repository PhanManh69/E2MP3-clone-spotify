package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shadowCustom
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body6Medium
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.utils.MyConstant.IMAGE_URL
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class STFThumbHeroType {
    Album, Artist, ArtistCircle, Song, Playlist
}

private fun getSizeImageFactory(size: STFThumbHeroType): Dp {
    return when (size) {
        STFThumbHeroType.Album -> 144.dp
        STFThumbHeroType.Artist -> 162.dp
        STFThumbHeroType.Song -> 94.dp
        STFThumbHeroType.Playlist -> 170.dp
        else -> 0.dp
    }
}

@Composable
fun STFThumbHero(
    modifier: Modifier = Modifier, imageUrl: String, title: String = "", subtitle: String = "", type: STFThumbHeroType, onClick: () -> Unit = {}
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val sizeImage = remember(type) { getSizeImageFactory(type) }
    val animatedPadding by animateDpAsState(if (toggled) 6.dp else 0.dp)

    DisposableEffect(imageUrl) {
        coroutineScope.launch {
            delay(10000L)
            if (!isImageLoaded) {
                isLoadFailed = true
            }
        }

        onDispose {
            coroutineScope.cancel()
        }
    }

    if (type != STFThumbHeroType.ArtistCircle) {
        Column(modifier = modifier.debounceClickable(indication = null) {
            coroutineScope.launch {
                toggled = true
                delay(100L)
                toggled = false
                onClick()
            }
        }) {
            if (!isLoadFailed) {
                AsyncImage(
                        modifier = Modifier
                            .shadowCustom(shapeRadius = 8.dp)
                            .size(sizeImage)
                            .padding(animatedPadding)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> })
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                )
            } else {
                Box(modifier = Modifier
                    .shadowCustom(shapeRadius = 8.dp)
                    .size(sizeImage)
                    .padding(animatedPadding)
                    .background(color = IconInvert, shape = RoundedCornerShape(8.dp))
                    .clip(shape = RoundedCornerShape(8.dp))) {
                    Image(painter = painterResource(R.drawable.img_loading_failed),
                          contentDescription = null,
                          contentScale = ContentScale.Crop,
                          modifier = Modifier.padding(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = title,
                 color = TextPrimary,
                 style = Body6Medium,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis,
                 modifier = Modifier.width(sizeImage))

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = subtitle,
                 color = TextSecondary,
                 style = Body6Regular,
                 maxLines = if (type == STFThumbHeroType.Song) 1 else 2,
                 overflow = TextOverflow.Ellipsis,
                 modifier = Modifier.width(sizeImage))
        }
    } else {
        STFThumbArtistsCircle(modifier = modifier, imageUrl = imageUrl, title = title, isLoadFailed = isLoadFailed, onClick = onClick)
    }
}

@Composable
private fun STFThumbArtistsCircle(modifier: Modifier = Modifier, imageUrl: String, title: String, isLoadFailed: Boolean, onClick: () -> Unit) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val animatedPadding by animateDpAsState(if (toggled) 6.dp else 0.dp)

    Column(modifier = modifier.debounceClickable(indication = null) {
        coroutineScope.launch {
            toggled = true
            delay(100L)
            toggled = false
            onClick()
        }
    }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (!isLoadFailed) {
            AsyncImage(
                    modifier = Modifier
                        .shadowCustom(shapeRadius = Int.MAX_VALUE.dp)
                        .size(162.dp)
                        .padding(animatedPadding)
                        .clip(CircleShape)
                        .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> })
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
            )
        } else {
            Box(modifier = Modifier
                .shadowCustom(shapeRadius = Int.MAX_VALUE.dp)
                .size(162.dp)
                .padding(animatedPadding)
                .background(color = IconInvert, shape = CircleShape)
                .clip(shape = CircleShape)) {
                Image(painter = painterResource(R.drawable.img_loading_failed),
                      contentDescription = null,
                      contentScale = ContentScale.Crop,
                      modifier = Modifier.padding(16.dp))
            }
        }

        Text(text = title,
             color = TextSecondary,
             style = Body6Regular,
             textAlign = TextAlign.Center,
             maxLines = 2,
             overflow = TextOverflow.Ellipsis,
             modifier = Modifier.width(162.dp))
    }
}

@Preview
@Composable
fun ThumbHeroAlbumPreview() {
    E2MP3Theme {
        STFThumbHero(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TPT", type = STFThumbHeroType.Album)
    }
}

@Preview
@Composable
fun ThumbHeroArtistPreview() {
    E2MP3Theme {
        STFThumbHero(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", type = STFThumbHeroType.Artist)
    }
}

@Preview
@Composable
fun ThumbHeroArtistCirclePreview() {
    E2MP3Theme {
        STFThumbHero(imageUrl = IMAGE_URL, title = "Nơi này có anh", type = STFThumbHeroType.ArtistCircle)
    }
}

@Preview
@Composable
fun ThumbHeroSongPreview() {
    E2MP3Theme {
        STFThumbHero(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", type = STFThumbHeroType.Song)
    }
}

@Preview
@Composable
fun ThumbHeroPlaylistPreview() {
    E2MP3Theme {
        STFThumbHero(imageUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", type = STFThumbHeroType.Playlist)
    }
}