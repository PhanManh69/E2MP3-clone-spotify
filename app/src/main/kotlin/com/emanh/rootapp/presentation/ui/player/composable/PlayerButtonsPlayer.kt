package com.emanh.rootapp.presentation.ui.player.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.emanh.rootapp.presentation.theme.IconPrimaryAlpha60
import com.emanh.rootapp.presentation.theme.SurfaceInvert

@Composable
fun PlayerButtonsPlayer(
    modifier: Modifier = Modifier,
    isPlayed: Boolean,
    onShuffleClick: () -> Unit,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit,
    onLoopClick: () -> Unit,
    onMusicalBoxClick: () -> Unit,
    onShareClick: () -> Unit,
    onListClick: () -> Unit,
    onPlayPauseClick: (Boolean) -> Unit,
) {
    val played = remember(isPlayed) { mutableStateOf(isPlayed) }

    Column(modifier = Modifier.padding(horizontal = 12.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onShuffleClick) {
                Icon(painter = painterResource(R.drawable.ic_32_remdom), contentDescription = null, tint = IconPrimaryAlpha60)
            }

            IconButton(onClick = onBackClick) {
                Icon(painter = painterResource(R.drawable.ic_32_back), contentDescription = null, tint = IconPrimary)
            }

            Box(modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(color = SurfaceInvert, shape = CircleShape)
                .clickable(onClick = {
                    played.value = !played.value
                    onPlayPauseClick(played.value)
                })) {
                Icon(painter = painterResource(if (played.value) R.drawable.ic_32_pause else R.drawable.ic_32_play),
                     contentDescription = null,
                     tint = IconInvert,
                     modifier = Modifier.align(Alignment.Center))
            }

            IconButton(onClick = onForwardClick) {
                Icon(painter = painterResource(R.drawable.ic_32_forward), contentDescription = null, tint = IconPrimary)
            }

            IconButton(onClick = onLoopClick) {
                Icon(painter = painterResource(R.drawable.ic_32_loop), contentDescription = null, tint = IconPrimaryAlpha60)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMusicalBoxClick) {
                Icon(painter = painterResource(R.drawable.ic_24_musical_box), contentDescription = null, tint = IconPrimary)
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onShareClick) {
                Icon(painter = painterResource(R.drawable.ic_24_share), contentDescription = null, tint = IconPrimary)
            }

            IconButton(onClick = onListClick) {
                Icon(painter = painterResource(R.drawable.ic_24_list), contentDescription = null, tint = IconPrimary)
            }
        }
    }
}

@Preview
@Composable
private fun PlayerButtonsPlayerPreview() {
    E2MP3Theme {
        PlayerButtonsPlayer(isPlayed = false,
                            onShuffleClick = {},
                            onBackClick = {},
                            onPlayPauseClick = {},
                            onForwardClick = {},
                            onLoopClick = {},
                            onMusicalBoxClick = {},
                            onShareClick = {},
                            onListClick = {})
    }
}