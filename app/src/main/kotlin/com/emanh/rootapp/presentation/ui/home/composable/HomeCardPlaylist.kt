package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import com.emanh.rootapp.presentation.composable.STFCardImage

@Composable
fun HomeCardPlaylist(
    modifier: Modifier = Modifier, maxItems: Int = 2, isLiked: Boolean = false, playlistCard: List<CrossRefPlaylistsModel>, onClick: (Int) -> Unit
) {
    val limitedList = playlistCard.shuffled().take(maxItems)
    val cardId = limitedList.map { it.playlists.playlistId }
    val cardTitle = limitedList.map { it.playlists.title }
    val cardAavatarUrl = limitedList.map { it.playlists.avatarUrl }

    val cardSubtitle = limitedList.map { item ->
        var totalSeconds = 0

        item.songsList.forEach { song ->
            val parts = song.timeline?.split(":")
            if (parts?.size == 3) {
                val hours = parts[0].toIntOrNull() ?: 0
                val minutes = parts[1].toIntOrNull() ?: 0
                val seconds = parts[2].toIntOrNull() ?: 0

                totalSeconds += hours * 3600 + minutes * 60 + seconds
            }
        }

        val totalHours = totalSeconds / 3600
        val totalMinutes = (totalSeconds % 3600) / 60

        "${item.songsList.size} bài, ${totalHours}h ${totalMinutes}min"
    }

    val cardImageUrlList = limitedList.map {
        val orderedSongsList = it.playlists.songsIdList.mapNotNull { songId ->
            it.songsList.find { it.songId == songId }
        }

        orderedSongsList.mapNotNull { song -> song.avatarUrl }
    }

    val cardDescription = limitedList.map {
        val orderedSongsList = it.playlists.songsIdList.mapNotNull { songId ->
            it.songsList.find { it.songId == songId }
        }

        orderedSongsList.map { song -> song.subtitle }.distinct().joinToString(", ")
    }

    Column(modifier = modifier) {
        limitedList.forEachIndexed { index, _ ->
            STFCardImage(modifier = modifier,
                         title = cardTitle[index].orEmpty(),
                         subtitle = cardSubtitle[index],
                         type = stringResource(R.string.playlist),
                         description = cardDescription[index],
                         avatarUrl = cardAavatarUrl[index].orEmpty(),
                         imageUrlList = cardImageUrlList[index],
                         isLiked = isLiked,
                         onClick = {
                             onClick(cardId[index])
                         })
        }
    }
}