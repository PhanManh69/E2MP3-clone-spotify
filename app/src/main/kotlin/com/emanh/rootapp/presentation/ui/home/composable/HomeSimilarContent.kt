package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.STFCarouselHorizontal
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselType
import com.emanh.rootapp.presentation.composable.STFThumbType
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.ui.home.HomeAlbumsDataFake
import com.emanh.rootapp.presentation.ui.home.HomeSingerData
import com.emanh.rootapp.presentation.ui.home.HomeSongsDataFake
import com.emanh.rootapp.utils.MyConstant.fakeSimilarContentList

@Composable
fun HomeSimilarContent(modifier: Modifier = Modifier, onAvatarClick: (Int) -> Unit, onThumbClick: (Int) -> Unit) {
    val thumbList = fakeSimilarContentList.drop(1).map { item ->
        val id = when (item) {
            is HomeAlbumsDataFake -> item.id
            is HomeSingerData -> item.id
            is HomeSongsDataFake -> item.id
            else -> -1
        }

        val imageUrl = when (item) {
            is HomeAlbumsDataFake -> item.imageUrl
            is HomeSingerData -> item.imageUrl
            is HomeSongsDataFake -> item.imageUrl
            else -> ""
        }

        val title = when (item) {
            is HomeAlbumsDataFake -> item.nameAlbum
            is HomeSingerData -> item.nameSinger
            is HomeSongsDataFake -> item.title
            else -> ""
        }

        val subtitle = when (item) {
            is HomeAlbumsDataFake -> item.singer.nameSinger
            is HomeSongsDataFake -> item.subtitle
            else -> ""
        }

        val description = when (item) {
            is HomeAlbumsDataFake -> item.ablumType
            is HomeSongsDataFake -> stringResource(R.string.cd_single)
            else -> ""
        }

        STFCarouselThumbData(id = id, imageUrl = imageUrl, title = title, subtitle = subtitle, description = description ?: "")
    }

    val thumbTypeList = fakeSimilarContentList.drop(1).map { item ->
        when (item) {
            is HomeSingerData -> STFThumbType.Artists
            else -> STFThumbType.Music
        }
    }

    val firstItem = fakeSimilarContentList.firstOrNull()

    val avatarUrl = when (firstItem) {
        is HomeAlbumsDataFake -> firstItem.imageUrl
        is HomeSingerData -> firstItem.imageUrl
        is HomeSongsDataFake -> firstItem.imageUrl
        else -> ""
    }

    val userName = when (firstItem) {
        is HomeAlbumsDataFake -> firstItem.nameAlbum
        is HomeSingerData -> firstItem.nameSinger
        is HomeSongsDataFake -> firstItem.title
        else -> ""
    }

    STFCarouselHorizontal(modifier = modifier,
                          avatarUrl = avatarUrl,
                          userName = userName,
                          table = stringResource(R.string.other_similar_content),
                          title = userName,
                          type = STFCarouselType.MusicBig,
                          typeThumb = thumbTypeList,
                          thumbItem = thumbList,
                          onAvatarClick = { onAvatarClick(0) },
                          onThumbClick = onThumbClick)
}

@Preview
@Composable
private fun HomeSimilarContentPreview() {
    E2MP3Theme {
        HomeSimilarContent(onAvatarClick = {}, onThumbClick = {})
    }
}