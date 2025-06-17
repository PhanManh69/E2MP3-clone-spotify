package com.emanh.rootapp.presentation.ui.player.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.STFMarqueeText
import com.emanh.rootapp.presentation.theme.Body6Bold
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundPrimary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextTertiary

@Composable
fun PlayerTopbar(modifier: Modifier = Modifier, title: String, subtitle: String, onDownClick: () -> Unit, onMoreClick: () -> Unit) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onDownClick) {
            Icon(painter = painterResource(R.drawable.ic_24_musical_chevron02_dw), contentDescription = null, tint = IconBackgroundPrimary)
        }

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "${stringResource(R.string.player_from)} ${title.uppercase()}",
                 color = TextTertiary,
                 style = Body6Regular,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis)

            STFMarqueeText(text = subtitle, textColor = TextPrimary, textStyle = Body6Bold, alignment = Alignment.CenterHorizontally)
        }

        IconButton(onClick = onMoreClick) {
            Icon(painter = painterResource(R.drawable.ic_24_bullet), contentDescription = null, tint = IconBackgroundPrimary)
        }
    }
}

@Preview
@Composable
private fun PlayerTopbarPreview() {
    E2MP3Theme {
        PlayerTopbar(title = "Playlist", subtitle = "Nghe gan day", onDownClick = {}, onMoreClick = {})
    }
}