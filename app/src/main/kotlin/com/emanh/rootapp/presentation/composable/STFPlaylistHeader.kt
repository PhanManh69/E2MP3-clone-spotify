package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body5Bold
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.TextPrimary

@Composable
fun STFPlaylistHeader(
    modifier: Modifier = Modifier, title: String, headerAlpha: Float, nestedScrollConnection: NestedScrollConnection, onBackClick: () -> Unit
) {
    Row(modifier = modifier.padding(start = 16.dp, top = 48.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(R.drawable.ic_24_musical_chevron_lt),
             contentDescription = null,
             tint = IconPrimary,
             modifier = Modifier.debounceClickable(onClick = onBackClick))

        Text(text = title,
             color = TextPrimary,
             style = Body5Bold,
             maxLines = 1,
             overflow = TextOverflow.Ellipsis,
             modifier = Modifier
                 .padding(end = 72.dp)
                 .alpha(headerAlpha)
                 .nestedScroll(nestedScrollConnection))
    }
}