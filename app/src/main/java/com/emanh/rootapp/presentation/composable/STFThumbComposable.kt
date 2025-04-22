package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
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
import com.emanh.rootapp.R
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

enum class STFThumbType {
    Music, Music2, Podcast, Artists
}

enum class STFThumbSize {
    Big, Medium, Small, XSmall
}

private fun getShapeFactory(type: STFThumbType): Shape {
    return when (type) {
        STFThumbType.Music, STFThumbType.Music2, STFThumbType.Podcast -> RoundedCornerShape(8.dp)
        STFThumbType.Artists -> CircleShape
    }
}

private fun getSizeImageFactory(size: STFThumbSize): Dp {
    return when (size) {
        STFThumbSize.Big -> 148.dp
        STFThumbSize.Medium -> 112.dp
        STFThumbSize.Small -> 108.dp
        STFThumbSize.XSmall -> 94.dp
    }
}

private fun hideSubtitleFactory(type: STFThumbType, size: STFThumbSize): Boolean {
    return if (type == STFThumbType.Artists && size == STFThumbSize.Big) false
    else if (type == STFThumbType.Music && size == STFThumbSize.Medium) false
    else true
}

private fun hideDescriptionFactory(type: STFThumbType, size: STFThumbSize): Boolean {
    return if (type == STFThumbType.Music && size == STFThumbSize.Small) true
    else if (type == STFThumbType.Music2 && size == STFThumbSize.Big) true
    else false
}

private fun resolveThumbAlignmentFactory(
    type: STFThumbType, size: STFThumbSize
): Triple<Alignment.Horizontal, Alignment.Vertical, TextAlign> {
    return if (type == STFThumbType.Artists && size == STFThumbSize.Big) {
        Triple(Alignment.CenterHorizontally, Alignment.CenterVertically, TextAlign.Center)
    } else if (type == STFThumbType.Artists && size == STFThumbSize.XSmall) {
        Triple(Alignment.CenterHorizontally, Alignment.CenterVertically, TextAlign.Center)
    } else {
        Triple(Alignment.Start, Alignment.CenterVertically, TextAlign.Start)
    }
}

@Composable
fun STFThumb(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String = "",
    subtitle: String = "",
    description: String = "",
    type: STFThumbType,
    size: STFThumbSize,
    onClick: () -> Unit = {}
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val shape = remember(type) { getShapeFactory(type) }
    val sizeImage = remember(size) { getSizeImageFactory(size) }
    val hideSubtitle = remember(size, type) { hideSubtitleFactory(type, size) }
    val hideDescription = remember(size, type) { hideDescriptionFactory(type, size) }
    val resolveThumbAlignment = remember(size, type) { resolveThumbAlignmentFactory(type, size) }
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

    Column(modifier = modifier.debounceClickable(indication = null) {
        coroutineScope.launch {
            toggled = true
            delay(100L)
            toggled = false
            onClick()
        }
    }, horizontalAlignment = resolveThumbAlignment.first) {
        if (!isLoadFailed) {
            AsyncImage(
                    modifier = Modifier
                        .shadowCustom(shapeRadius = if (shape == CircleShape) Int.MAX_VALUE.dp else 8.dp)
                        .size(sizeImage)
                        .padding(animatedPadding)
                        .clip(shape = shape)
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
                .shadowCustom(shapeRadius = if (shape == CircleShape) Int.MAX_VALUE.dp else 8.dp)
                .size(sizeImage)
                .padding(animatedPadding)
                .background(color = IconInvert, shape = shape)
                .clip(shape = shape)) {
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
             textAlign = resolveThumbAlignment.third,
             maxLines = if (!hideSubtitle) Int.MAX_VALUE else 1,
             overflow = TextOverflow.Ellipsis,
             modifier = Modifier.width(sizeImage))

        Spacer(modifier = Modifier.height(4.dp))

        Row(modifier = Modifier
            .width(sizeImage)
            .alpha(if (hideSubtitle) 1f else 0f), verticalAlignment = resolveThumbAlignment.second) {
            Text(text = subtitle,
                 color = TextSecondary,
                 style = Body6Regular,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis,
                 modifier = Modifier)

            if (!hideDescription) {
                Text(text = " • ", color = TextSecondary, style = Body6Regular)

                Text(text = description,
                     color = TextSecondary,
                     style = Body6Regular,
                     maxLines = 1,
                     overflow = TextOverflow.Ellipsis,
                     modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun STFThumbPlaylist(
    modifier: Modifier = Modifier, imageUrl: String, description: String = "", onClick: () -> Unit = {}
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
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
                        .size(148.dp)
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
                .size(148.dp)
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

        Text(text = description,
             color = TextSecondary,
             style = Body6Regular,
             maxLines = 2,
             overflow = TextOverflow.Ellipsis,
             modifier = Modifier.width(148.dp))
    }
}

@Preview
@Composable
fun ThumbMusicBigPreview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL,
                 title = "Nơi này có anh",
                 subtitle = "Sơn Tùng M-TP",
                 description = "350M view",
                 type = STFThumbType.Music,
                 size = STFThumbSize.Big)
    }
}

@Preview
@Composable
fun ThumbMusicBig2Preview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL,
                 title = "Nơi này có anh",
                 subtitle = "Sơn Tùng M-TP",
                 description = "350M view",
                 type = STFThumbType.Music2,
                 size = STFThumbSize.Big)
    }
}

@Preview
@Composable
fun ThumbPodcastBigPreview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL,
                 title = "Nơi này có anh",
                 subtitle = "Sơn Tùng M-TP",
                 description = "350M view",
                 type = STFThumbType.Podcast,
                 size = STFThumbSize.Big)
    }
}

@Preview
@Composable
fun ThumbArtistsBigPreview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL, title = "Nơi này có anh", type = STFThumbType.Artists, size = STFThumbSize.Big)
    }
}

@Preview
@Composable
fun ThumbMusicMediumPreview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL, title = "Nơi này có anh", type = STFThumbType.Music, size = STFThumbSize.Medium)
    }
}

@Preview
@Composable
fun ThumbMusicSmallPreview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL,
                 title = "Nơi này có anh",
                 subtitle = "Sơn Tùng M-TP",
                 description = "350M view",
                 type = STFThumbType.Music,
                 size = STFThumbSize.Small)
    }
}

@Preview
@Composable
fun ThumbPodcastSmallPreview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL,
                 title = "Nơi này có anh",
                 subtitle = "Sơn Tùng M-TP",
                 description = "350M view",
                 type = STFThumbType.Podcast,
                 size = STFThumbSize.Small)
    }
}

@Preview
@Composable
fun ThumbArtistsSmallPreview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL,
                 title = "Nơi này có anh",
                 subtitle = "Sơn Tùng M-TP",
                 description = "350M view",
                 type = STFThumbType.Artists,
                 size = STFThumbSize.Small)
    }
}

@Preview
@Composable
fun ThumbMusicXSmallPreview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL,
                 title = "Nơi này có anh",
                 subtitle = "Sơn Tùng M-TP",
                 description = "350M view",
                 type = STFThumbType.Music,
                 size = STFThumbSize.XSmall)
    }
}

@Preview
@Composable
fun ThumbArtistsXSmallPreview() {
    E2MP3Theme {
        STFThumb(imageUrl = IMAGE_URL,
                 title = "Nơi này có anh",
                 subtitle = "Sơn Tùng M-TP",
                 description = "350M view",
                 type = STFThumbType.Artists,
                 size = STFThumbSize.XSmall)
    }
}

@Preview
@Composable
fun ThumbPlaylistPreview() {
    E2MP3Theme {
        STFThumbPlaylist(imageUrl = IMAGE_URL, description = "description")
    }
}