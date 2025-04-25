package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.composable.STFCardSlim
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.ui.home.HomeAlbumsDataFake
import com.emanh.rootapp.presentation.ui.home.HomePlaylistsData
import com.emanh.rootapp.utils.MyConstant.fakeQuickPlaylistList

@Composable
fun HomeQuickPlaylist(modifier: Modifier = Modifier, onCardClick: (Int) -> Unit) {
    Column(modifier = modifier.padding(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val chunkSize = 2
        val chunkedList = fakeQuickPlaylistList.chunked(chunkSize)

        chunkedList.forEach { rowItems ->
            Row(modifier = Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowItems.forEach { item ->
                    val id = when (item) {
                        is HomeAlbumsDataFake -> item.id
                        is HomePlaylistsData -> item.id
                        else -> -1
                    }

                    val title = when (item) {
                        is HomeAlbumsDataFake -> item.nameAlbum
                        is HomePlaylistsData -> item.namePlaylist
                        else -> ""
                    }

                    val imageUrl = when (item) {
                        is HomeAlbumsDataFake -> item.imageUrl
                        is HomePlaylistsData -> item.imageUrl
                        else -> ""
                    }

                    STFCardSlim(modifier = Modifier.weight(1f), imageUrl = imageUrl, title = title, onClick = { onCardClick(id) })
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeGridCardPreview() {
    E2MP3Theme {
        HomeQuickPlaylist(onCardClick = {})
    }
}