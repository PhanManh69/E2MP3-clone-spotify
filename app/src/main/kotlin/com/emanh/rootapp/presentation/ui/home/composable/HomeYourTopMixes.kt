package com.emanh.rootapp.presentation.ui.home.composable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import com.emanh.rootapp.presentation.composable.STFCarouselHorizontal
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselType

@Composable
fun HomeYourTopMixes(
    modifier: Modifier = Modifier, yourTopMixesList: List<CrossRefPlaylistsModel>, onThumbClick: (Int) -> Unit
) {
    val thumbItem = yourTopMixesList.map { playlist ->
        val orderedSongsList = playlist.playlists.songsIdList.mapNotNull { songId ->
            playlist.songsList.find { it.songId == songId }
        }

        val allSingerNames = orderedSongsList.map { it.subtitle }.distinct().joinToString(", ")

        STFCarouselThumbData(id = playlist.playlists.playlistId, imageUrl = playlist.playlists.avatarUrl.orEmpty(), description = allSingerNames)
    }

    Log.d("HomeYourTopMixes", "thumbItem: $thumbItem")

    STFCarouselHorizontal(modifier = modifier,
                          title = stringResource(R.string.your_top_mixes),
                          type = STFCarouselType.Playlist,
                          thumbItem = thumbItem,
                          onThumbClick = { playlistId ->
                              onThumbClick(playlistId)
                          })
}