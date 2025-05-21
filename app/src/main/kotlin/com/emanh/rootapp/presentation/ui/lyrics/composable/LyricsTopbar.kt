package com.emanh.rootapp.presentation.ui.lyrics.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.STFMarqueeText
import com.emanh.rootapp.presentation.theme.AlphaN00_20
import com.emanh.rootapp.presentation.theme.Body6Bold
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundPrimary
import com.emanh.rootapp.presentation.theme.TextPrimary

@Composable
fun LyricsTopbar(modifier: Modifier = Modifier, gradientEdgeColor: Color, song: SongsModel, onDownClick: () -> Unit, onFlagClick: () -> Unit) {
    Row(modifier = modifier.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onDownClick) {
            Box(modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(color = AlphaN00_20, shape = CircleShape),
                contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(R.drawable.ic_24_musical_chevron02_dw), contentDescription = null, tint = IconBackgroundPrimary)
            }
        }

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            STFMarqueeText(text = song.title.orEmpty(),
                           textColor = TextPrimary,
                           textStyle = Body6Bold,
                           alignment = Alignment.CenterHorizontally,
                           gradientEdgeColor = gradientEdgeColor)

            Text(text = song.subtitle.orEmpty(), color = TextPrimary, style = Body6Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

        IconButton(onClick = onFlagClick) {
            Icon(painter = painterResource(R.drawable.ic_32_flag), contentDescription = null, tint = IconBackgroundPrimary)
        }
    }
}

@Preview
@Composable
private fun PlayerTopbarPreview() {
    E2MP3Theme {
        LyricsTopbar(gradientEdgeColor = Color.Transparent,
                     song = SongsModel(title = "Nếu lúc đó",
                                       subtitle = "tlinh",
                                       avatarUrl = "https://lh3.googleusercontent.com/5mLb1J5XVQMLi395i1w24u2lC_W4tBznuRrzz8Kw0mxQKOrCHBGCbYx63jxBJI8QHcUwiYsJmDPY0igy=w544-h544-l90-rj"),
                     onDownClick = {},
                     onFlagClick = {})
    }
}