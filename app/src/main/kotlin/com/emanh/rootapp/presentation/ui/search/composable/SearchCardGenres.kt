package com.emanh.rootapp.presentation.ui.search.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.STFCardSearch
import com.emanh.rootapp.presentation.theme.Body3Medium
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.utils.MyConstant.cardGenresSearchList

@Composable
fun SearchCardGenres(modifier: Modifier = Modifier, onClick: (Int) -> Unit) {
    Column(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(top = 24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = stringResource(R.string.browse_all), color = TextPrimary, style = Body3Medium, modifier = Modifier.padding(bottom = 8.dp))

        val chunkSize = 2
        val chunkedList = cardGenresSearchList.chunked(chunkSize)

        chunkedList.forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                rowItems.forEachIndexed { index, item ->
                    STFCardSearch(modifier = Modifier.weight(1f), titleId = item.first, imageId = item.second, onClick = { onClick(index) })
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchCardGenresPreview() {
    E2MP3Theme {
        SearchCardGenres(onClick = {})
    }
}