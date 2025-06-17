package com.emanh.rootapp.presentation.ui.searchinput.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.utils.MyConstant.ALBUMS_SEARCH
import com.emanh.rootapp.utils.MyConstant.ARTISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import com.emanh.rootapp.utils.MyConstant.PLAYLISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.SONGS_SEARCH

@Composable
fun SearchListItem(
    modifier: Modifier = Modifier, searchList: List<Any>, onItemClick: (Long, String, String) -> Unit, onIconClick: (Long, String) -> Unit
) {
    val isKeyboardOpen = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .imePadding()
        .background(SurfacePrimary)) {
        items(count = searchList.size, key = { index ->
            val item = searchList[index]
            when (item) {
                is SongsModel -> "song-${item.id}"
                is UsersModel -> "user-${item.id}"
                is AlbumsModel -> "album-${item.id}"
                is PlaylistsModel -> "playlist-${item.id}"
                else -> "unknown-${item.hashCode()}"
            }
        }) { index ->
            val item = searchList[index]

            val id = when (item) {
                is SongsModel -> item.id
                is UsersModel -> item.id
                is AlbumsModel -> item.id
                is PlaylistsModel -> item.id
                else -> -1
            }

            val type = when (item) {
                is SongsModel -> SONGS_SEARCH
                is UsersModel -> ARTISTS_SEARCH
                is AlbumsModel -> ALBUMS_SEARCH
                is PlaylistsModel -> PLAYLISTS_SEARCH
                else -> ""
            }

            val avatarUrl = when (item) {
                is SongsModel -> item.avatarUrl
                is UsersModel -> item.avatarUrl
                is AlbumsModel -> item.avatarUrl
                is PlaylistsModel -> item.avatarUrl
                else -> ""
            }

            val title = when (item) {
                is SongsModel -> item.title
                is UsersModel -> item.name
                is AlbumsModel -> item.title
                is PlaylistsModel -> item.title
                else -> ""
            }

            val label = when (item) {
                is SongsModel -> item.subtitle
                is UsersModel -> item.name
                is AlbumsModel -> item.subtitle
                is PlaylistsModel -> item.subtitle
                else -> ""
            }

            val typeItem = when (item) {
                is UsersModel -> STFItemType.Artists
                else -> STFItemType.Music
            }

            STFItem(imageUrl = avatarUrl.orEmpty(),
                    title = title.orEmpty(),
                    label = label.orEmpty(),
                    iconId = R.drawable.ic_24_musical_chevron_rt,
                    type = typeItem,
                    size = STFItemSize.Small,
                    onItemClick = { onItemClick(id, type, title.orEmpty()) },
                    onIconClick = { onIconClick(id, type) })
        }

        if (!isKeyboardOpen) {
            item {
                Spacer(modifier = Modifier.height(PADDING_BOTTOM_BAR.dp))
            }
        }
    }
}