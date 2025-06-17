package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shadowCustom
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body3Medium
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun STFCardSearch(modifier: Modifier = Modifier, titleId: Int, imageId: Int, onClick: () -> Unit = {}) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val animatedPadding by animateDpAsState(if (toggled) 4.dp else 0.dp)

    Box(modifier = modifier.debounceClickable(indication = null) {
        coroutineScope.launch {
            toggled = true
            delay(100L)
            toggled = false
            onClick()
        }
    }) {
        AsyncImage(
                modifier = Modifier
                    .shadowCustom(shapeRadius = 8.dp)
                    .height(98.dp)
                    .padding(animatedPadding)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageId)
                    .crossfade(true)
                    .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> isImageLoaded = true })
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
        )

        Text(text = stringResource(titleId),
             color = TextBackgroundPrimary,
             style = Body3Medium,
             modifier = Modifier
                 .padding(vertical = 8.dp)
                 .padding(start = 12.dp, end = 48.dp))
    }
}

@Preview
@Composable
fun CardSearchPreview() {
    E2MP3Theme {
        Row {
            STFCardSearch(titleId = R.string.music, imageId = R.drawable.img_card_music, modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.width(16.dp))

            STFCardSearch(titleId = R.string.pop, imageId = R.drawable.img_card_podcast, modifier = Modifier.weight(1f))
        }
    }
}