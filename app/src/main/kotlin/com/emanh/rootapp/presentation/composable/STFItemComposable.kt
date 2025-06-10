package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body3Regular
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.IconSecondary
import com.emanh.rootapp.presentation.theme.LikedSongs
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.theme.YourSongs
import com.emanh.rootapp.utils.MyConstant.IMAGE_URL
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class STFItemType {
    Music, Artists, ArtistsLibrary, ArtistsFollow
}

enum class STFItemSize {
    Big, Medium, Small
}

@Immutable
data class STFItemLayoutData(
    val showTitle: Boolean = true, val showChips: Boolean = false, val showRibbon: Boolean = false
)

private fun getItemSizeFactory(type: STFItemSize): Dp {
    return when (type) {
        STFItemSize.Big -> 80.dp
        STFItemSize.Medium -> 64.dp
        STFItemSize.Small -> 48.dp
    }
}

private fun getItemLayoutDataFactory(type: STFItemType, size: STFItemSize): STFItemLayoutData {
    return when (type) {
        STFItemType.Artists -> {
            when (size) {
                STFItemSize.Medium -> STFItemLayoutData(showTitle = false)
                STFItemSize.Small -> STFItemLayoutData(showTitle = false, showRibbon = true)
                else -> STFItemLayoutData()
            }
        }

        STFItemType.ArtistsFollow -> {
            when (size) {
                STFItemSize.Small -> STFItemLayoutData(showChips = true, showRibbon = true)
                else -> STFItemLayoutData()
            }
        }

        else -> STFItemLayoutData()
    }
}

@Composable
fun STFItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    label: String,
    title: String,
    isLiked: Boolean = false,
    labelChips: String = "",
    yourIconId: Int? = null,
    iconId: Int = R.drawable.ic_24_close,
    type: STFItemType,
    size: STFItemSize,
    onItemClick: () -> Unit = {},
    onIconClick: () -> Unit = {}
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val animatedPadding by animateDpAsState(if (toggled) 4.dp else 0.dp)
    val itemSize = remember(size) { getItemSizeFactory(size) }
    val itemLayout = remember(type, size) { getItemLayoutDataFactory(type, size) }

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

    Row(modifier = modifier
        .padding(vertical = 8.dp)
        .padding(start = 16.dp)
        .debounceClickable(indication = null) {
            coroutineScope.launch {
                toggled = true
                delay(100L)
                toggled = false
                onItemClick()
            }
        }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        if (isLiked) {
            Box(modifier = Modifier
                .size(itemSize)
                .padding(animatedPadding)
                .background(brush = if (yourIconId == null) LikedSongs else YourSongs,
                            shape = if (type == STFItemType.Music) RoundedCornerShape(8.dp) else CircleShape)
                .clip(shape = if (type == STFItemType.Music) RoundedCornerShape(8.dp) else CircleShape), contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(if (yourIconId == null) R.drawable.ic_48_heart_fill else R.drawable.ic_24_artist),
                     contentDescription = null,
                     tint = IconPrimary,
                     modifier = Modifier.size(32.dp))
            }
        } else {
            if (!isLoadFailed) {
                AsyncImage(modifier = Modifier
                    .size(itemSize)
                    .padding(animatedPadding)
                    .clip(shape = if (type == STFItemType.Music) RoundedCornerShape(8.dp) else CircleShape)
                    .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                           model = ImageRequest.Builder(LocalContext.current)
                               .data(imageUrl)
                               .crossfade(true)
                               .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> })
                               .build(),
                           contentDescription = null,
                           contentScale = ContentScale.Crop)
            } else {
                Box(modifier = Modifier
                    .size(itemSize)
                    .padding(animatedPadding)
                    .background(color = IconInvert, shape = if (type == STFItemType.Music) RoundedCornerShape(8.dp) else CircleShape)
                    .clip(shape = if (type == STFItemType.Music) RoundedCornerShape(8.dp) else CircleShape)) {
                    Image(painter = painterResource(R.drawable.img_loading_failed),
                          contentDescription = null,
                          contentScale = ContentScale.Crop,
                          modifier = Modifier.padding(16.dp))
                }
            }
        }

        Column(modifier = Modifier
            .weight(1f)
            .padding(end = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = title, color = TextPrimary, style = Body3Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)

                if (itemLayout.showRibbon) {
                    Image(painter = painterResource(R.drawable.ic_12_ribbon), contentDescription = null)
                }
            }
            if (itemLayout.showTitle) {
                Text(text = label, color = TextSecondary, style = Body6Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }

        if (size == STFItemSize.Small) {
            if (itemLayout.showChips) {
                STFChips(text = labelChips, size = STFChipsSize.Normal, type = STFChipsType.Stroke, onClick = onIconClick)
            } else {
                Icon(painter = painterResource(iconId),
                     contentDescription = null,
                     tint = IconSecondary,
                     modifier = Modifier
                         .size(24.dp)
                         .clip(shape = RoundedCornerShape(8.dp))
                         .debounceClickable { onIconClick() })
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview
@Composable
fun ItemMusicBigPreview() {
    E2MP3Theme {
        STFItem(imageUrl = IMAGE_URL,
                title = "Nơi này có anh",
                label = "Sơn Tùng M-TP",
                type = STFItemType.Music,
                size = STFItemSize.Big,
                onItemClick = {},
                onIconClick = {})
    }
}

@Preview
@Composable
fun ItemMusicMediumPreview1() {
    E2MP3Theme {
        STFItem(imageUrl = IMAGE_URL,
                title = "Nơi này có anh",
                label = "Sơn Tùng M-TP",
                type = STFItemType.Music,
                size = STFItemSize.Medium,
                onItemClick = {},
                onIconClick = {})
    }
}

@Preview
@Composable
fun ItemMusicMediumPreview2() {
    E2MP3Theme {
        STFItem(imageUrl = "",
                title = "Nơi này có anh",
                label = "Sơn Tùng M-TP",
                isLiked = true,
                type = STFItemType.Music,
                size = STFItemSize.Medium,
                onItemClick = {},
                onIconClick = {})
    }
}

@Preview
@Composable
fun ItemMusicMediumPreview3() {
    E2MP3Theme {
        STFItem(imageUrl = "",
                title = "Nơi này có anh",
                label = "Sơn Tùng M-TP",
                isLiked = true,
                yourIconId = R.drawable.ic_24_artist,
                type = STFItemType.Music,
                size = STFItemSize.Medium,
                onItemClick = {},
                onIconClick = {})
    }
}

@Preview
@Composable
fun ItemArtistsMediumPreview1() {
    E2MP3Theme {
        STFItem(imageUrl = IMAGE_URL,
                title = "Nơi này có anh",
                label = "Sơn Tùng M-TP",
                type = STFItemType.Artists,
                size = STFItemSize.Medium,
                onItemClick = {},
                onIconClick = {})
    }
}

@Preview
@Composable
fun ItemArtistsMediumPreview2() {
    E2MP3Theme {
        STFItem(imageUrl = IMAGE_URL,
                title = "Nơi này có anh",
                label = "Sơn Tùng M-TP",
                type = STFItemType.ArtistsLibrary,
                size = STFItemSize.Medium,
                onItemClick = {},
                onIconClick = {})
    }
}

@Preview
@Composable
fun ItemMusicSmallPreview() {
    E2MP3Theme {
        STFItem(imageUrl = IMAGE_URL,
                title = "Nơi này có anh",
                label = "Sơn Tùng M-TP",
                type = STFItemType.Music,
                size = STFItemSize.Small,
                onItemClick = {},
                onIconClick = {})
    }
}

@Preview
@Composable
fun ItemArtistsSmallPreview() {
    E2MP3Theme {
        STFItem(imageUrl = IMAGE_URL,
                title = "Nơi này có anh",
                label = "Sơn Tùng M-TP",
                type = STFItemType.Artists,
                size = STFItemSize.Small,
                onItemClick = {},
                onIconClick = {})
    }
}

@Preview
@Composable
fun ItemArtistsFollowSmallPreview() {
    E2MP3Theme {
        STFItem(imageUrl = IMAGE_URL,
                title = "Nơi này có anh",
                label = "Sơn Tùng M-TP",
                labelChips = "Follow",
                type = STFItemType.ArtistsFollow,
                size = STFItemSize.Small,
                onItemClick = {},
                onIconClick = {})
    }
}