package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFCarouselHorizontal
import com.emanh.rootapp.presentation.composable.STFCarouselThumbData
import com.emanh.rootapp.presentation.composable.STFCarouselType
import com.emanh.rootapp.presentation.composable.STFThumbType
import com.emanh.rootapp.utils.MyConstant.ALBUM_TYPE
import com.emanh.rootapp.utils.MyConstant.ARTIST_TYPE
import com.emanh.rootapp.utils.MyConstant.SINGLE_TYPE

@Composable
fun HomeSimilarContent(
    modifier: Modifier = Modifier,
    yourFavoriteArtists: UsersModel,
    similarContent: List<Any>,
    onAvatarClick: (Int) -> Unit,
    onThumbClick: (Int, String) -> Unit
) {
    val thumbList = similarContent.map { item ->
        val id = when (item) {
            is AlbumsModel -> item.id
            is UsersModel -> item.id
            is SongsModel -> item.id
            else -> -1
        }

        val imageUrl = when (item) {
            is AlbumsModel -> item.avatarUrl
            is UsersModel -> item.avatarUrl
            is SongsModel -> item.avatarUrl
            else -> ""
        }

        val title = when (item) {
            is AlbumsModel -> item.title
            is UsersModel -> item.name
            is SongsModel -> item.title
            else -> ""
        }

        val subtitle = when (item) {
            is AlbumsModel -> item.subtitle
            is SongsModel -> item.subtitle
            else -> ""
        }

        val description = when (item) {
            is AlbumsModel -> item.albumType
            is SongsModel -> stringResource(R.string.cd_single)
            else -> ""
        }

        STFCarouselThumbData(id = id,
                             imageUrl = imageUrl.orEmpty(),
                             title = title.orEmpty(),
                             subtitle = subtitle.orEmpty(),
                             description = description.orEmpty())
    }

    val thumbTypeList = similarContent.map { item ->
        when (item) {
            is UsersModel -> STFThumbType.Artists
            else -> STFThumbType.Music
        }
    }

    val type = similarContent.map { item ->
        when (item) {
            is AlbumsModel -> ALBUM_TYPE
            is UsersModel -> ARTIST_TYPE
            is SongsModel -> SINGLE_TYPE
            else -> ""
        }
    }

    STFCarouselHorizontal(modifier = modifier,
                          avatarUrl = yourFavoriteArtists.avatarUrl,
                          userName = yourFavoriteArtists.username.orEmpty(),
                          table = stringResource(R.string.other_similar_content),
                          title = yourFavoriteArtists.name.orEmpty(),
                          type = STFCarouselType.MusicBig,
                          typeThumb = thumbTypeList,
                          thumbItem = thumbList,
                          onAvatarClick = { onAvatarClick(0) },
                          onThumbClick = { clickedId ->
                              val similarItem = similarContent.indexOfFirst { item ->
                                  when (item) {
                                      is AlbumsModel -> item.id == clickedId
                                      is UsersModel -> item.id == clickedId
                                      is SongsModel -> item.id == clickedId
                                      else -> false
                                  }
                              }
                              if (similarItem != -1 && similarItem < type.size) {
                                  onThumbClick(clickedId, type[similarItem])
                              }
                          })
}