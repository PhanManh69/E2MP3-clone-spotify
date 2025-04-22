package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.STFCarouselHorizontal
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselType
import com.emanh.rootapp.presentation.composable.STFThumbType
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.ui.home.HomeAlbumsData
import com.emanh.rootapp.presentation.ui.home.HomeSingerData
import com.emanh.rootapp.presentation.ui.home.HomeSongsData
import com.emanh.rootapp.utils.MyConstant.fakeSimilarContentList

@Composable
fun HomeSimilarContent(modifier: Modifier = Modifier, onAvatarClick: (Int) -> Unit, onThumbClick: (Int) -> Unit) {
    val thumbList = fakeSimilarContentList.drop(1).map { item ->
        val id = when (item) {
            is HomeAlbumsData -> item.id
            is HomeSingerData -> item.id
            is HomeSongsData -> item.id
            else -> -1
        }

        val imageUrl = when (item) {
            is HomeAlbumsData -> item.imageUrl
            is HomeSingerData -> item.imageUrl
            is HomeSongsData -> item.imageUrl
            else -> ""
        }

        val title = when (item) {
            is HomeAlbumsData -> item.nameAlbum
            is HomeSingerData -> item.nameSinger
            is HomeSongsData -> item.title
            else -> ""
        }

        val subtitle = when (item) {
            is HomeAlbumsData -> item.singer.nameSinger
            is HomeSongsData -> item.subtitle
            else -> ""
        }

        val description = when (item) {
            is HomeAlbumsData -> item.ablumType
            is HomeSongsData -> stringResource(R.string.cd_single)
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
        is HomeAlbumsData -> firstItem.imageUrl
        is HomeSingerData -> firstItem.imageUrl
        is HomeSongsData -> firstItem.imageUrl
        else -> ""
    }

    val userName = when (firstItem) {
        is HomeAlbumsData -> firstItem.nameAlbum
        is HomeSingerData -> firstItem.nameSinger
        is HomeSongsData -> firstItem.title
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