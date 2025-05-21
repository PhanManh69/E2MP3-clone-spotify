package com.emanh.rootapp.presentation.ui.player.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.STFMarqueeText
import com.emanh.rootapp.presentation.composable.utils.shadowCustom
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body3Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundPrimary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextTertiary
import com.emanh.rootapp.presentation.theme.Title4Bold

@Composable
fun PlayerContentButton(
    modifier: Modifier = Modifier,
    isPlayed: Boolean,
    currentTime: String,
    remainingTime: String,
    valueSlider: Float,
    song: SongsModel,
    onAddClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit,
    onLoopClick: () -> Unit,
    onMusicalBoxClick: () -> Unit,
    onShareClick: () -> Unit,
    onListClick: () -> Unit,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (Float) -> Unit,
    onPlayPauseClick: (Boolean) -> Unit,
) {
    Column(modifier = modifier) {
        var isImageLoaded by remember { mutableStateOf(false) }

        Row(modifier = Modifier.padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AsyncImage(
                    modifier = Modifier
                        .shadowCustom(shapeRadius = 8.dp)
                        .size(48.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(song.avatarUrl)
                        .crossfade(true)
                        .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> isImageLoaded = true })
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
            )

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                STFMarqueeText(text = song.title.orEmpty(), textColor = TextPrimary, textStyle = Title4Bold)

                Text(text = song.subtitle.orEmpty(), color = TextTertiary, style = Body3Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            Icon(painter = painterResource(R.drawable.ic_32_plus_circle),
                 contentDescription = null,
                 tint = IconBackgroundPrimary,
                 modifier = Modifier
                     .clip(CircleShape)
                     .clickable(onClick = onAddClick))
        }

        Spacer(modifier = Modifier.height(16.dp))

        PlayerMediaSlider(value = valueSlider,
                          currentTime = currentTime,
                          remainingTime = remainingTime,
                          onValueChange = onValueChange,
                          onValueChangeFinished = onValueChangeFinished)

        Spacer(modifier = Modifier.height(12.dp))

        PlayerButtonsPlayer(isPlayed = isPlayed,
                            onShuffleClick = onShuffleClick,
                            onBackClick = onBackClick,
                            onPlayPauseClick = onPlayPauseClick,
                            onForwardClick = onForwardClick,
                            onLoopClick = onLoopClick,
                            onMusicalBoxClick = onMusicalBoxClick,
                            onShareClick = onShareClick,
                            onListClick = onListClick)
    }
}

@Preview
@Composable
private fun PlayerContentButtonPreview() {
    var sliderPosition by remember { mutableFloatStateOf(0.6f) }

    E2MP3Theme {
        PlayerContentButton(isPlayed = false,
                            song = SongsModel(title = "Nơi này có anh", subtitle = "Sơn Tùng MTP", timeline = "03:03"),
                            valueSlider = sliderPosition,
                            currentTime = "1:52",
                            remainingTime = "-1:03",
                            onAddClick = {},
                            onShuffleClick = {},
                            onBackClick = {},
                            onPlayPauseClick = {},
                            onForwardClick = {},
                            onLoopClick = {},
                            onMusicalBoxClick = {},
                            onShareClick = {},
                            onListClick = {},
                            onValueChange = { sliderPosition = it },
                            onValueChangeFinished = {})
    }
}