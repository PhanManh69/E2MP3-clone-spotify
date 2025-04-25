package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.STFCarouselHorizontal
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselType
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.utils.MyConstant.fakeRecentlyLestenedList

@Composable
fun HomeRecentlyListened(modifier: Modifier = Modifier, onThumbClick: (Int) -> Unit, onViewAll: () -> Unit) {
    val thumbItem = fakeRecentlyLestenedList.map { song ->
        STFCarouselThumbData(id = song.id, imageUrl = song.imageUrl, title = song.title, subtitle = song.subtitle)
    }

    STFCarouselHorizontal(modifier = modifier,
                          title = stringResource(R.string.recently_listened),
                          viewAll = stringResource(R.string.view_all),
                          type = STFCarouselType.MusicSmall,
                          thumbItem = thumbItem,
                          onThumbClick = onThumbClick,
                          onViewAll = onViewAll)
}

@Preview
@Composable
private fun HomeRecentlyListenedPreview() {
    E2MP3Theme {
        HomeRecentlyListened(onThumbClick = {}, onViewAll = {})
    }
}