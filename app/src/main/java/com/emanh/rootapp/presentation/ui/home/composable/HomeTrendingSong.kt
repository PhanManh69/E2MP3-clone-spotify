package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.STFCarouselHorizontal
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselType
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.utils.MyConstant.fakeTrendingSongList

@Composable
fun HomeTrendingSong(modifier: Modifier = Modifier, onThumbClick: (Int) -> Unit, onPlayAll: () -> Unit) {
    val thumbItem = fakeTrendingSongList.map { song ->
        STFCarouselThumbData(id = song.id, imageUrl = song.imageUrl, title = song.title, subtitle = song.subtitle)
    }

    STFCarouselHorizontal(modifier = modifier,
                          title = stringResource(R.string.trending_song),
                          viewAll = stringResource(R.string.play_all),
                          type = STFCarouselType.MusicBig2,
                          thumbItem = thumbItem,
                          onViewAll = onPlayAll,
                          onThumbClick = onThumbClick)
}

@Preview
@Composable
private fun HomeTrendingSongPreview() {
    E2MP3Theme {
        HomeTrendingSong(onThumbClick = {}, onPlayAll = {})
    }
}