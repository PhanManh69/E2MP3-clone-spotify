package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body3Regular
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceSecondary
import com.emanh.rootapp.presentation.theme.SurfaceSecondaryInvert
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.utils.MyConstant.IMAGE_URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun STFButtonSheet(
    modifier: Modifier = Modifier,
    song: SongsModel,
    onDismissRequest: () -> Unit,
    onAddPlaylistClick: () -> Unit,
    onRemovePlaylistClick: () -> Unit,
    onAddToQueueClick: () -> Unit,
    onGoToArtistClick: () -> Unit,
    onShareClick: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(modifier = modifier, onDismissRequest = {
        onDismissRequest()
    }, sheetState = bottomSheetState, containerColor = SurfaceSecondary, dragHandle = {
        Box(contentAlignment = Alignment.Center) {
            HorizontalDivider(modifier = Modifier
                .width(32.dp)
                .padding(vertical = 16.dp)
                .clip(CircleShape),
                              thickness = 4.dp,
                              color = SurfaceSecondaryInvert)
        }
    }) {
        STFButtonSheetContent(song = song,
                              onAddPlaylistClick = onAddPlaylistClick,
                              onRemovePlaylistClick = onRemovePlaylistClick,
                              onAddToQueueClick = onAddToQueueClick,
                              onGoToArtistClick = onGoToArtistClick,
                              onShareClick = onShareClick)
    }
}

@Composable
private fun STFButtonSheetContent(
    modifier: Modifier = Modifier,
    song: SongsModel,
    onAddPlaylistClick: () -> Unit,
    onRemovePlaylistClick: () -> Unit,
    onAddToQueueClick: () -> Unit,
    onGoToArtistClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var isImageLoaded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AsyncImage(modifier = Modifier
                .size(48.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                       model = ImageRequest.Builder(LocalContext.current)
                           .data(song.avatarUrl)
                           .crossfade(true)
                           .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> })
                           .build(),
                       contentDescription = null,
                       contentScale = ContentScale.Crop)

            Column(modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)) {
                Text(text = song.title.orEmpty(), color = TextPrimary, style = Body3Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)

                Text(text = song.subtitle.orEmpty(), color = TextSecondary, style = Body6Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }

        HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp), thickness = 1.dp, color = SurfaceSecondaryInvert)

        STFButtonSheetButtonItem(iconId = R.drawable.ic_24_plus_circle, titleId = R.string.add_to_other_playlist, onClick = onAddPlaylistClick)

        STFButtonSheetButtonItem(iconId = R.drawable.ic_32_minus_circle,
                                 titleId = R.string.remove_from_this_playlist,
                                 onClick = onRemovePlaylistClick)

        STFButtonSheetButtonItem(iconId = R.drawable.ic_24_forms_add, titleId = R.string.add_to_queue, onClick = onAddToQueueClick)

        STFButtonSheetButtonItem(iconId = R.drawable.ic_24_artist, titleId = R.string.go_to_artist, onClick = onGoToArtistClick)

        STFButtonSheetButtonItem(iconId = R.drawable.ic_24_share, titleId = R.string.share, onClick = onShareClick)
    }
}

@Composable
private fun STFButtonSheetButtonItem(modifier: Modifier = Modifier, iconId: Int, titleId: Int, onClick: () -> Unit) {
    Box(modifier = modifier.clickable(onClick = onClick)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(painter = painterResource(iconId), contentDescription = stringResource(titleId), tint = IconPrimary, modifier = Modifier.size(24.dp))

            Text(text = stringResource(titleId), color = TextPrimary, style = Body6Regular)
        }
    }
}

@Preview
@Composable
private fun STFButtonSheetPreview() {
    E2MP3Theme {
        STFButtonSheetContent(song = SongsModel(title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP", avatarUrl = IMAGE_URL),
                              onAddPlaylistClick = {},
                              onRemovePlaylistClick = {},
                              onAddToQueueClick = {},
                              onGoToArtistClick = {},
                              onShareClick = {})
    }
}

@Preview
@Composable
private fun STFButtonSheetButtonItemPreview() {
    E2MP3Theme {
        STFButtonSheetButtonItem(iconId = R.drawable.ic_32_plus_circle, titleId = R.string.spotify, onClick = {})
    }
}