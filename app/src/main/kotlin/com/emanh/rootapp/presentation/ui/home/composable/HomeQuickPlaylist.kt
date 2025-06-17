package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.presentation.composable.STFCardSlim
import com.emanh.rootapp.utils.MyConstant.ALBUM_TYPE
import com.emanh.rootapp.utils.MyConstant.NOT_AVATAR
import com.emanh.rootapp.utils.MyConstant.PLAYLIST_TYPE

@Composable
fun HomeQuickPlaylist(modifier: Modifier = Modifier, quickPlaylistList: List<Any>, onCardClick: (Long, String) -> Unit) {
    Column(modifier = modifier.padding(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val chunkSize = 2
        val chunkedList = quickPlaylistList.chunked(chunkSize)

        chunkedList.forEach { rowItems ->
            Row(modifier = Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowItems.forEach { item ->
                    val id = when (item) {
                        is AlbumsModel -> item.id
                        is PlaylistsModel -> item.id
                        else -> -1
                    }

                    val title = when (item) {
                        is AlbumsModel -> item.title
                        is PlaylistsModel -> item.title
                        else -> ""
                    }

                    val imageUrl = when (item) {
                        is AlbumsModel -> item.avatarUrl
                        is PlaylistsModel -> item.avatarUrl
                        else -> ""
                    }

                    val type = when (item) {
                        is AlbumsModel -> ALBUM_TYPE
                        is PlaylistsModel -> PLAYLIST_TYPE
                        else -> ""
                    }

                    STFCardSlim(modifier = Modifier.weight(1f), imageUrl = imageUrl ?: NOT_AVATAR, title = title.orEmpty(), onClick = { onCardClick(id, type) })
                }
            }
        }
    }
}