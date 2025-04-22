package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.res.stringResource
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
import com.emanh.rootapp.presentation.theme.AlphaN00_10
import com.emanh.rootapp.presentation.theme.AlphaN00_30
import com.emanh.rootapp.presentation.theme.AlphaN00_50
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundPrimary
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.IconProduct
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary
import com.emanh.rootapp.presentation.theme.TextBackgroundSecondary
import com.emanh.rootapp.presentation.theme.Title4Bold
import com.emanh.rootapp.utils.MyConstant.IMAGE_URL
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun STFCardImage(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    type: String,
    description: String,
    avatarUrl: String,
    imageUrlList: List<String>,
    isLiked: Boolean,
    onAddPlaylist: (Boolean) -> Unit = {},
    onClick: () -> Unit = {}
) {
    var currentImageIndex by remember { mutableIntStateOf(0) }
    val currentImageUrl = remember(currentImageIndex, imageUrlList) {
        imageUrlList.getOrElse(currentImageIndex) { "" }
    }

    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }
    var toggled by remember { mutableStateOf(false) }

    val liked = remember(isLiked) { mutableStateOf(isLiked) }
    val coroutineScope = rememberCoroutineScope()
    val animatedPadding by animateDpAsState(if (toggled) 6.dp else 0.dp)

    DisposableEffect(currentImageUrl) {
        val job = coroutineScope.launch {
            delay(10000L)
            if (!isImageLoaded) {
                isLoadFailed = true
            }
        }

        onDispose {
            job.cancel()
        }
    }

    Box(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(top = 24.dp, bottom = 8.dp)
        .debounceClickable(indication = null) {
            coroutineScope.launch {
                toggled = true
                delay(100L)
                toggled = false
                onClick()
            }
        }) {
        MainImage(imageUrl = currentImageUrl, isImageLoaded = isImageLoaded, isLoadFailed = isLoadFailed, onImageLoaded = { isImageLoaded = true })

        Box(modifier = Modifier
            .shadowCustom(shapeRadius = 16.dp)
            .fillMaxWidth()
            .height(440.dp)
            .background(color = AlphaN00_10, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp)))

        STFCardImageTitle(title = title,
                          subtitle = subtitle,
                          type = type,
                          imageUrl = avatarUrl,
                          liked = liked,
                          animatedPadding = animatedPadding,
                          onAddPlaylist = onAddPlaylist)

        ImageNavigationControls(modifier = Modifier.align(Alignment.Center), onPreviousClick = {
            if (imageUrlList.isNotEmpty()) {
                currentImageIndex = if (currentImageIndex > 0) currentImageIndex - 1
                else imageUrlList.size - 1

                isImageLoaded = false
                isLoadFailed = false
            }
        }, onNextClick = {
            if (imageUrlList.isNotEmpty()) {
                currentImageIndex = (currentImageIndex + 1) % imageUrlList.size
                isImageLoaded = false
                isLoadFailed = false
            }
        })

        STFCardImageButtons(modifier = Modifier.align(Alignment.BottomCenter), text = description)
    }
}

@Composable
private fun MainImage(
    imageUrl: String, isImageLoaded: Boolean, isLoadFailed: Boolean, onImageLoaded: () -> Unit
) {
    if (!isLoadFailed && imageUrl.isNotEmpty()) {
        AsyncImage(
                modifier = Modifier
                    .shadowCustom(shapeRadius = 16.dp)
                    .fillMaxWidth()
                    .height(440.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .listener(onSuccess = { _, _ -> onImageLoaded() }, onError = { _, _ -> })
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
        )
    } else {
        Box(modifier = Modifier
            .shadowCustom(shapeRadius = 16.dp)
            .fillMaxWidth()
            .height(440.dp)
            .background(color = IconInvert, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))) {
            Image(painter = painterResource(R.drawable.img_loading_failed),
                  contentDescription = null,
                  contentScale = ContentScale.Crop,
                  modifier = Modifier
                      .size(96.dp)
                      .align(Alignment.Center))
        }
    }
}

@Composable
private fun ImageNavigationControls(
    modifier: Modifier = Modifier, onPreviousClick: () -> Unit, onNextClick: () -> Unit
) {
    Row(modifier = modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        NavigationButton(icon = R.drawable.ic_24_musical_chevron_lt, onClick = onPreviousClick)

        NavigationButton(icon = R.drawable.ic_24_musical_chevron_rt, onClick = onNextClick)
    }
}

@Composable
private fun NavigationButton(
    icon: Int, onClick: () -> Unit
) {
    Box(modifier = Modifier
        .clip(shape = RoundedCornerShape(100))
        .background(color = AlphaN00_30, shape = RoundedCornerShape(100))
        .clickable { onClick() }) {
        Icon(painter = painterResource(icon), contentDescription = null, tint = IconBackgroundPrimary, modifier = Modifier.padding(8.dp))
    }
}

@Composable
private fun STFCardImageTitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    type: String,
    imageUrl: String,
    liked: MutableState<Boolean>,
    animatedPadding: Dp,
    onAddPlaylist: (Boolean) -> Unit
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(imageUrl) {
        val job = coroutineScope.launch {
            delay(10000L)
            if (!isImageLoaded) {
                isLoadFailed = true
            }
        }

        onDispose {
            job.cancel()
        }
    }

    Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        if (!isLoadFailed) {
            AsyncImage(
                    modifier = Modifier
                        .shadowCustom(shapeRadius = 8.dp)
                        .size(64.dp)
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
                .size(64.dp)
                .padding(animatedPadding)
                .background(color = IconInvert, shape = RoundedCornerShape(8.dp))
                .clip(shape = RoundedCornerShape(8.dp))) {
                Image(painter = painterResource(R.drawable.img_loading_failed),
                      contentDescription = null,
                      contentScale = ContentScale.Crop,
                      modifier = Modifier
                          .size(32.dp)
                          .align(Alignment.Center))
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = TextBackgroundPrimary, style = Title4Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)

            Text(text = "$type • $subtitle", color = TextBackgroundSecondary, style = Body6Regular)
        }

        Icon(painter = painterResource(if (liked.value) R.drawable.ic_24_plus_check
                                       else R.drawable.ic_24_plus_circle),
             contentDescription = null,
             tint = if (liked.value) IconProduct else IconBackgroundPrimary,
             modifier = Modifier
                 .clip(shape = RoundedCornerShape(8.dp))
                 .debounceClickable {
                     liked.value = true
                     onAddPlaylist(liked.value)
                 })
    }
}

@Composable
private fun STFCardImageButtons(modifier: Modifier = Modifier, text: String) {
    Column(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(bottom = 16.dp)) {
        Text(text = text,
             color = TextBackgroundSecondary,
             style = Body6Regular,
             maxLines = 1,
             overflow = TextOverflow.Ellipsis,
             modifier = Modifier.padding(bottom = 8.dp))

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            PreviewButton()

            Spacer(modifier = Modifier.weight(1f))

            ActionButtons()
        }
    }
}

@Composable
private fun PreviewButton() {
    Row(modifier = Modifier
        .clip(shape = RoundedCornerShape(100))
        .background(color = AlphaN00_50, shape = RoundedCornerShape(100))
        .debounceClickable { }, verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(R.drawable.ic_24_sound_off),
             contentDescription = null,
             tint = IconBackgroundPrimary,
             modifier = Modifier
                 .padding(vertical = 8.dp)
                 .padding(start = 12.dp)
                 .clip(shape = RoundedCornerShape(8.dp))
                 .debounceClickable { })

        Text(text = stringResource(R.string.preview_episoe),
             color = TextBackgroundPrimary,
             style = Body6Regular,
             modifier = Modifier.padding(start = 8.dp, end = 12.dp))
    }
}

@Composable
private fun ActionButtons() {
    Icon(painter = painterResource(R.drawable.ic_24_bullet),
         contentDescription = null,
         tint = IconBackgroundPrimary,
         modifier = Modifier
             .clip(shape = RoundedCornerShape(8.dp))
             .debounceClickable { })

    Image(painter = painterResource(R.drawable.ic_42_play),
          contentDescription = null,
          modifier = Modifier
              .clip(shape = RoundedCornerShape(100))
              .debounceClickable { })
}

@Preview
@Composable
fun CardImagePreview() {
    E2MP3Theme {
        STFCardImage(title = "THE WXRDIES",
                     subtitle = "32 songs, 2hr 32min",
                     type = "Album",
                     description = "description, description, description, description, description",
                     avatarUrl = IMAGE_URL,
                     imageUrlList = listOf("https://lh3.googleusercontent.com/c0FW-iJwNP-fg8mZIfX9lHbuLf10X8Gm4BHlScJmAN2ZJ4Wn1yU3Wu3OoZRGjy-XWFMh0DdTP2nogf0=w544-h544-l90-rj",
                                           "https://lh3.googleusercontent.com/kZpotSmLLEhmlwwuzZuu45niLbL9gmDG0r7YEOH4o7QH99093KRO9XmyrsEvB8JvO0V2s7r8GiJzHRa1=w544-h544-l90-rj",
                                           "https://lh3.googleusercontent.com/dX41VBG_a84AdAqVZsktrYwXDKSfqbirc8x1CVHkxwHMdKl7sEdFUkSJYXFz7-y1_diYLQTd4Ve_4tvv=w544-h544-l90-rj",
                                           "https://lh3.googleusercontent.com/53FbQUMZkeQxQYXgqOFu4xtLp3nLo8xBa3ZEWoi7z8sYSjDPl50WFOwbG9xNKt24d7Mc9VZkb_K1j1ig=w544-h544-l90-rj",
                                           "https://lh3.googleusercontent.com/ptSXANqzjDIXHlhy6usQZ0Rvbxy2GEFbQ0gHZcqploPGy9OLo5gbb_wc4yVLXs-Tk8VYJaqydOPvEsYVEg=w544-h544-l90-rj"),
                     isLiked = false,
                     onAddPlaylist = {})
    }
}