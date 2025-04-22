package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.emanh.rootapp.presentation.composable.utils.STFLoadVideo
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body6Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class STFThumbVerticalSize {
    Big, Small
}

private fun getThumbSizeFactory(size: STFThumbVerticalSize): Pair<Dp, Dp> {
    return when (size) {
        STFThumbVerticalSize.Big -> 109.dp to 190.dp
        STFThumbVerticalSize.Small -> 98.dp to 170.dp
    }
}

@Composable
fun STFThumbVertical(
    modifier: Modifier = Modifier, videoUri: String, title: String, size: STFThumbVerticalSize = STFThumbVerticalSize.Big, onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val resolvedUri = remember { "android.resource://${context.packageName}/raw/$videoUri".toUri() }
    val thumbSize = remember(size) { getThumbSizeFactory(size) }

    var toggled by remember { mutableStateOf(false) }

    val animatedPadding by animateDpAsState(if (toggled) 2.dp else 0.dp)

    Box(modifier = modifier
        .debounceClickable(indication = null) {
            coroutineScope.launch {
                toggled = true
                delay(150L)
                toggled = false
                onClick()
            }
        }) {

        STFLoadVideo(modifier = Modifier
            .width(thumbSize.first)
            .height(thumbSize.second)
            .padding(animatedPadding)
            .clip(shape = RoundedCornerShape(8.dp)), videoUri = resolvedUri)

        Text(text = title,
             color = TextBackgroundPrimary,
             style = Body6Bold,
             maxLines = 2,
             overflow = TextOverflow.Ellipsis,
             modifier = Modifier
                 .width(thumbSize.first)
                 .padding(horizontal = 12.dp)
                 .padding(bottom = 12.dp)
                 .align(Alignment.BottomStart))
    }
}

@Preview
@Composable
fun ThumbVerticalBigPreview() {
    E2MP3Theme {
        STFThumbVertical(videoUri = "vd_thumb_2", title = "pop mỹ", size = STFThumbVerticalSize.Big)
    }
}

@Preview
@Composable
fun ThumbVerticalSmallPreview() {
    E2MP3Theme {
        STFThumbVertical(videoUri = "vd_thumb_1", title = "hiphop việt", size = STFThumbVerticalSize.Small)
    }
}