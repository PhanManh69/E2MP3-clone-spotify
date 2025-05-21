package com.emanh.rootapp.presentation.ui.lyrics.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceInvert
import com.emanh.rootapp.presentation.ui.player.composable.PlayerMediaSlider

@Composable
fun LyricsButtonControl(
    modifier: Modifier = Modifier,
    currentTime: String,
    remainingTime: String,
    valueSlider: Float,
    isPlayed: Boolean,
    onShareClick: () -> Unit,
    onPlayPauseClick: (Boolean) -> Unit,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (Float) -> Unit,
) {
    val played = remember(isPlayed) { mutableStateOf(isPlayed) }

    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        PlayerMediaSlider(value = valueSlider,
                          currentTime = currentTime,
                          remainingTime = remainingTime,
                          onValueChange = onValueChange,
                          onValueChangeFinished = onValueChangeFinished)

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)) {
            Box(modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(color = SurfaceInvert, shape = CircleShape)
                .align(Alignment.Center)
                .clickable(onClick = {
                    played.value = !played.value
                    onPlayPauseClick(played.value)
                }), contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(if (played.value) R.drawable.ic_32_pause else R.drawable.ic_32_play),
                     contentDescription = null,
                     tint = IconInvert)
            }

            IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = onShareClick) {
                Icon(painter = painterResource(R.drawable.ic_24_share), contentDescription = null, tint = IconPrimary)
            }
        }
    }
}

@Preview
@Composable
private fun LyricsButtonControlPreview() {
    E2MP3Theme {
        LyricsButtonControl(isPlayed = true,
                            currentTime = "1:52",
                            remainingTime = "-1:03",
                            valueSlider = 0.6f,
                            onPlayPauseClick = {},
                            onShareClick = {},
                            onValueChange = {},
                            onValueChangeFinished = {})
    }
}