package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shadowCustom
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.theme.Title4Bold
import com.emanh.rootapp.utils.MyConstant.IMAGE_URL
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class STFTitleType {
    Podcast, Artists
}

@Composable
fun STFTitle(modifier: Modifier = Modifier, imageUrl: String, label: String, title: String, type: STFTitleType, onClick: () -> Unit = {}) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val animatedPadding by animateDpAsState(if (toggled) 4.dp else 0.dp)

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

    Row(modifier = modifier.debounceClickable(indication = null) {
        coroutineScope.launch {
            toggled = true
            delay(100L)
            toggled = false
            onClick()
        }
    }, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        if (!isLoadFailed) {
            AsyncImage(modifier = Modifier
                .shadowCustom(shapeRadius = if (type == STFTitleType.Podcast) 8.dp else Int.MAX_VALUE.dp)
                .size(48.dp)
                .padding(animatedPadding)
                .clip(if (type == STFTitleType.Podcast) RoundedCornerShape(8.dp) else CircleShape)
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
                .shadowCustom(shapeRadius = if (type == STFTitleType.Podcast) 8.dp else Int.MAX_VALUE.dp)
                .size(48.dp)
                .padding(animatedPadding)
                .background(color = IconInvert, shape = if (type == STFTitleType.Podcast) RoundedCornerShape(8.dp) else CircleShape)
                .clip(if (type == STFTitleType.Podcast) RoundedCornerShape(8.dp) else CircleShape)) {
                Image(painter = painterResource(R.drawable.img_loading_failed),
                      contentDescription = null,
                      contentScale = ContentScale.Crop,
                      modifier = Modifier.padding(16.dp))
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)) {
            Text(text = label, color = TextSecondary, style = Body6Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.weight(1f))

            Text(text = title, color = TextPrimary, style = Title4Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview
@Composable
fun TitlePodcastPreview() {
    E2MP3Theme {
        STFTitle(imageUrl = IMAGE_URL,
                 label = "Podcast",
                 title = "Nơi này có anh",
                 type = STFTitleType.Podcast,
                 modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Preview
@Composable
fun TitleArtistsPreview() {
    E2MP3Theme {
        STFTitle(imageUrl = IMAGE_URL,
                 label = "Artists",
                 title = "Nơi này có anh",
                 type = STFTitleType.Artists,
                 modifier = Modifier.padding(horizontal = 16.dp))
    }
}