package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body7Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.SurfaceSecondary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.utils.MyConstant.IMAGE_URL
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun STFCardSlim(modifier: Modifier = Modifier, imageUrl: String, title: String, onClick: () -> Unit = {}) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    DisposableEffect(imageUrl) {
        scope.launch {
            delay(10000L)
            if (!isImageLoaded) {
                isLoadFailed = true
            }
        }

        onDispose {
            scope.cancel()
        }
    }

    Row(modifier = modifier
        .background(color = SurfaceSecondary, shape = RoundedCornerShape(8.dp))
        .clip(shape = RoundedCornerShape(8.dp))
        .debounceClickable { onClick() }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        if (!isLoadFailed) {
            AsyncImage(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
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
                .size(56.dp)
                .background(color = IconInvert, shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .clip(shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))) {
                Image(painter = painterResource(R.drawable.img_loading_failed),
                      contentDescription = null,
                      contentScale = ContentScale.Crop,
                      modifier = Modifier.padding(16.dp))
            }
        }

        Text(text = title,
             color = TextPrimary,
             style = Body7Bold,
             maxLines = 3,
             overflow = TextOverflow.Ellipsis,
             modifier = Modifier.padding(end = 8.dp))
    }
}

@Preview
@Composable
fun CardSlimPreview() {
    E2MP3Theme {
        STFCardSlim(imageUrl = IMAGE_URL, title = "Title")
    }
}