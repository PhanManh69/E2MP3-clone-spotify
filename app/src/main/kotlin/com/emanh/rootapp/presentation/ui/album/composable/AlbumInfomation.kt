package com.emanh.rootapp.presentation.ui.album.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.theme.Body7Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextPrimary

@Composable
fun AlbumInfomation(modifier: Modifier = Modifier, date: String, time: String, songs: Int, artistList: List<UsersModel>, onItemClick: (Int) -> Unit) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = date, color = TextPrimary, style = Body7Regular, modifier = Modifier.padding(horizontal = 16.dp))

        Text(text = "$songs bài hát - $time", color = TextPrimary, style = Body7Regular, modifier = Modifier.padding(horizontal = 16.dp))

        artistList.forEach { artist ->
            STFItem(imageUrl = artist.avatarUrl.orEmpty(),
                    title = artist.name.orEmpty(),
                    label = "",
                    type = STFItemType.Artists,
                    size = STFItemSize.Medium,
                    onItemClick = {
                        onItemClick(artist.id)
                    })
        }
    }
}

@Preview
@Composable
private fun AlbumInfomationPreview() {
    E2MP3Theme {
        AlbumInfomation(date = "24-02-2025", time = "24 h 02 min", songs = 4, artistList = listOf(UsersModel(), UsersModel()), onItemClick = {})
    }
}