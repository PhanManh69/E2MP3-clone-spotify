package com.emanh.rootapp.presentation.ui.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.emanh.rootapp.presentation.composable.STFCardPodcat
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.utils.MyConstant.fakePoscatsList

@Composable
fun HomePodcats(
    modifier: Modifier = Modifier, isLiked: Boolean = false, maxItems: Int = fakePoscatsList.size
) {
    val limitedList = fakePoscatsList.shuffled().take(maxItems)

    Column(modifier = modifier) {
        limitedList.forEach { podcast ->
            STFCardPodcat(title = podcast.namePoscast,
                          subtitle = podcast.ep,
                          description = podcast.owner,
                          date = podcast.date,
                          time = podcast.time,
                          content = podcast.content,
                          imageUrl = podcast.imageUrl,
                          isLiked = isLiked)
        }
    }
}

@Preview
@Composable
private fun HomePoscatsPreview() {
    E2MP3Theme {
        HomePodcats()
    }
}