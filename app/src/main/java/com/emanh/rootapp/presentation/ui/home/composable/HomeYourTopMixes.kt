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
import com.emanh.rootapp.utils.MyConstant.fakeYourTopMixesList

@Composable
fun HomeYourTopMixes(
    modifier: Modifier = Modifier, onThumbClick: (Int) -> Unit
) {
    val thumbItem = fakeYourTopMixesList.map { playlist ->
        val allSingerNames = playlist.songs.flatMap { it.singer }.map { it.nameSinger }.distinct().joinToString(", ")

        STFCarouselThumbData(id = playlist.id, imageUrl = playlist.imageUrl, description = allSingerNames)
    }

    STFCarouselHorizontal(modifier = modifier,
                          title = stringResource(R.string.your_top_mixes),
                          type = STFCarouselType.Playlist,
                          thumbItem = thumbItem,
                          onThumbClick = { index ->
                              val playlistId = thumbItem.getOrNull(index)?.id
                              playlistId?.let { onThumbClick(it) }
                          })
}

@Preview
@Composable
private fun HomePopularAlbumsSinglesPreview() {
    E2MP3Theme {
        HomeYourTopMixes(onThumbClick = {})
    }
}