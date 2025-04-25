package com.emanh.rootapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.theme.Body2Bold
import com.emanh.rootapp.presentation.theme.Body6Medium
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.utils.MyConstant.IMAGE_URL

@Composable
fun STFItemArtists(
    modifier: Modifier = Modifier, title: String, label: String, imageUrl: String, titleItem: String, labelItem: String, onItemClick: () -> Unit = {}
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, color = TextPrimary, style = Body2Bold, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))

            Text(text = label, color = TextSecondary, style = Body6Medium, textAlign = TextAlign.End, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

        STFItem(imageUrl = imageUrl,
                title = titleItem,
                label = labelItem,
                type = STFItemType.Music,
                size = STFItemSize.Big,
                onItemClick = onItemClick)
    }
}

@Preview
@Composable
fun ItemArtistsPreview() {
    E2MP3Theme {
        STFItemArtists(title = "Title",
                       label = "Label",
                       imageUrl = IMAGE_URL,
                       titleItem = "Nơi này có anh",
                       labelItem = "Sơn Tùng M-TP",
                       onItemClick = {})
    }
}