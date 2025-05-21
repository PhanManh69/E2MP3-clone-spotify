package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.STFCarouselHorizontal
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselType

@Composable
fun HomeRecentlyListened(
    modifier: Modifier = Modifier, recentlyLestenedList: List<SongsModel>, onThumbClick: (Int) -> Unit, onViewAll: () -> Unit
) {
    val thumbItem = recentlyLestenedList.take(10).map { song ->
        STFCarouselThumbData(id = song.id, imageUrl = song.avatarUrl.orEmpty(), title = song.title.orEmpty(), subtitle = song.subtitle.orEmpty())
    }

    if (thumbItem.isEmpty()) return else {
        STFCarouselHorizontal(modifier = modifier,
                              title = stringResource(R.string.recently_listened),
                              viewAll = if (recentlyLestenedList.size > 10) stringResource(R.string.view_all) else null,
                              type = STFCarouselType.MusicSmall,
                              thumbItem = thumbItem,
                              onThumbClick = onThumbClick,
                              onViewAll = onViewAll)
    }
}