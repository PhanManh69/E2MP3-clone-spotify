package com.emanh.rootapp.presentation.ui.single.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.theme.Body7Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextPrimary

@Composable
fun SingleInfomation(
    modifier: Modifier = Modifier, date: String, time: String, artistList: List<UsersModel>, onItemClick: (Int) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = date, color = TextPrimary, style = Body7Regular, modifier = Modifier.padding(horizontal = 16.dp))

        Text(text = "1${stringResource(R.string.one_song)} - $time",
             color = TextPrimary,
             style = Body7Regular,
             modifier = Modifier.padding(horizontal = 16.dp))

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
        SingleInfomation(date = "24-02-2025",
                         time = "2min20s",
                         artistList = listOf(UsersModel(name = "Artist 1", avatarUrl = ""), UsersModel(name = "Artist 2", avatarUrl = "")),
                         onItemClick = {})
    }
}