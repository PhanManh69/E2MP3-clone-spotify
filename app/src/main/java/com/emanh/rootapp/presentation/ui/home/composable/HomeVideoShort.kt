package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.STFThumbVertical
import com.emanh.rootapp.presentation.composable.STFThumbVerticalSize
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.Title4Bold
import kotlinx.coroutines.delay

@Composable
fun HomeVideoShort(modifier: Modifier = Modifier) {
    var visibleItemCount by remember { mutableIntStateOf(0) }

    val videoList = remember {
        listOf(Pair("vd_podcast_1", R.string.title_vd_podcast_1),
               Pair("vd_podcast_2", R.string.title_vd_podcast_2),
               Pair("vd_podcast_3", R.string.title_vd_podcast_3))
    }

    LaunchedEffect(videoList) {
        while (visibleItemCount < videoList.size) {
            delay(300L)
            visibleItemCount++
        }
    }

    Column(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(top = 24.dp, bottom = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = stringResource(R.string.ep_for_you), color = TextPrimary, style = Title4Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)

        Row(modifier = Modifier.height(190.dp)) {
            repeat(minOf(visibleItemCount, videoList.size)) { index ->
                AnimatedVisibility(modifier = Modifier.weight(1f), visible = true, enter = fadeIn(animationSpec = tween(durationMillis = 300))) {
                    STFThumbVertical(videoUri = videoList[index].first,
                                     title = stringResource(videoList[index].second),
                                     size = STFThumbVerticalSize.Big)
                }
            }
        }
    }
}