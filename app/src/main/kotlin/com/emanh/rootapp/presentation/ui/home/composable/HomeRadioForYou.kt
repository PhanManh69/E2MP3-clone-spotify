package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.presentation.composable.STFCarouselHorizontal
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselType

@Composable
fun HomeRadioForYou(
    modifier: Modifier = Modifier, radioForYouPlaylist: List<PlaylistsModel>, onThumbClick: (Long) -> Unit
) {
    val thumbItem = radioForYouPlaylist.map { playlist ->
        STFCarouselThumbData(id = playlist.id,
                             imageUrl = playlist.avatarUrl.orEmpty(),
                             description = playlist.title.orEmpty())
    }

    STFCarouselHorizontal(modifier = modifier,
                          title = stringResource(R.string.radio_for_you),
                          type = STFCarouselType.Playlist,
                          thumbItem = thumbItem,
                          onThumbClick = { id ->
                              onThumbClick(id)
                          })
}