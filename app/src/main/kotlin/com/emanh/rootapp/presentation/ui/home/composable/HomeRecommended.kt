package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.STFCarouselHorizontal
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselType
import com.emanh.rootapp.presentation.ui.home.HomeSongsData

@Composable
fun HomeRecommended(modifier: Modifier = Modifier, recommendedList: List<HomeSongsData>, onThumbClick: (Int) -> Unit, onPlayAll: () -> Unit) {
    val thumbItem = recommendedList.map { song ->
        STFCarouselThumbData(id = song.id, imageUrl = song.avatarUrl, title = song.title, subtitle = song.subtitle)
    }

    STFCarouselHorizontal(modifier = modifier,
                          title = stringResource(R.string.recommended),
                          viewAll = stringResource(R.string.play_all),
                          type = STFCarouselType.MusicBig2,
                          thumbItem = thumbItem,
                          onViewAll = onPlayAll,
                          onThumbClick = onThumbClick)
}