package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.STFCardImage
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.ui.home.HomeAlbumsDataFake
import com.emanh.rootapp.presentation.ui.home.HomePlaylistsData
import com.emanh.rootapp.utils.MyConstant.fakeCardImage

@Composable
fun HomeCardImage(
    modifier: Modifier = Modifier, maxItems: Int = fakeCardImage.size, isLiked: Boolean = false
) {
    val limitedList = fakeCardImage.shuffled().take(maxItems)

    val cardTitle = limitedList.map {
        when (it) {
            is HomeAlbumsDataFake -> it.nameAlbum
            is HomePlaylistsData -> it.namePlaylist
            else -> ""
        }
    }

    val cardSubtitle = limitedList.map {
        when (it) {
            is HomeAlbumsDataFake -> "${it.songs.size}, 1hr 32min"
            is HomePlaylistsData -> "${it.songs.size}, 2hr 24min"
            else -> ""
        }
    }

    val cardType = limitedList.map {
        when (it) {
            is HomeAlbumsDataFake -> R.string.album
            is HomePlaylistsData -> R.string.playlist
            else -> 0
        }
    }

    val cardAavatarUrl = limitedList.map {
        when (it) {
            is HomeAlbumsDataFake -> it.imageUrl
            is HomePlaylistsData -> it.imageUrl
            else -> ""
        }
    }

    val cardImageUrlList = limitedList.map {
        when (it) {
            is HomeAlbumsDataFake -> it.songs.map { song -> song.imageUrl }
            is HomePlaylistsData -> it.songs.map { song -> song.imageUrl }
            else -> emptyList()
        }
    }

    val cardDescription = limitedList.map { item ->
        when (item) {
            is HomePlaylistsData -> item.songs.flatMap { it.singer }.map { it.nameSinger }.distinct().joinToString(", ")
            is HomeAlbumsDataFake -> item.songs.map { song -> song.title }.distinct().joinToString(", ")
            else -> ""
        }
    }

    Column(modifier = modifier) {
        limitedList.forEachIndexed { index, _ ->
            STFCardImage(modifier = modifier,
                         title = cardTitle[index],
                         subtitle = cardSubtitle[index],
                         type = stringResource(cardType[index]),
                         description = cardDescription[index],
                         avatarUrl = cardAavatarUrl[index],
                         imageUrlList = cardImageUrlList[index],
                         isLiked = isLiked)
        }
    }
}

@Preview
@Composable
private fun HomeCardImagePreview() {
    E2MP3Theme {
        HomeCardImage()
    }
}