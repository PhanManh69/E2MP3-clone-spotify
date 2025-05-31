package com.emanh.rootapp.presentation.ui.single.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFAvatar
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body7Bold
import com.emanh.rootapp.presentation.theme.Body7Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundDark
import com.emanh.rootapp.presentation.theme.IconBackgroundPrimary
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.IconProduct
import com.emanh.rootapp.presentation.theme.IconSecondary
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceSecondaryInvert
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.theme.Title4Bold

@Composable
fun SingleButton(
    modifier: Modifier = Modifier,
    modifierPausePlay: Modifier = Modifier,
    isAddSong: Boolean,
    single: SongsModel,
    artistList: List<UsersModel>,
    onOwnerClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onMoreClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onPausePlayClick: () -> Unit
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = single.title.orEmpty(), color = TextPrimary, style = Title4Bold)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {

            artistList.forEach {
                STFAvatar(modifier = Modifier.size(20.dp), avatarUrl = it.avatarUrl, userName = it.name.orEmpty(), onClick = {
                    onOwnerClick(it.id)
                })
            }

            Text(text = artistList.map { it.name }.distinct().joinToString(", "),
                 color = TextPrimary,
                 style = Body7Bold,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis)
        }

        Text(text = "${stringResource(R.string.single)} · ${single.releaseDate?.takeLast(4).orEmpty()}", color = TextSecondary, style = Body7Regular)

        Row(modifier = Modifier.padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically) {
            if (!isLoadFailed) {
                AsyncImage(modifier = Modifier
                    .width(32.dp)
                    .height(40.dp)
                    .border(width = 2.dp, color = SurfaceSecondaryInvert, shape = RoundedCornerShape(8.dp))
                    .clip(shape = RoundedCornerShape(8.dp))
                    .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                           model = ImageRequest.Builder(LocalContext.current)
                               .data(single.avatarUrl)
                               .crossfade(true)
                               .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> })
                               .build(),
                           contentDescription = null,
                           contentScale = ContentScale.Crop)
            } else {
                Box(modifier = Modifier
                    .width(32.dp)
                    .height(40.dp)
                    .border(width = 2.dp, color = SurfaceSecondaryInvert, shape = RoundedCornerShape(8.dp))
                    .background(color = IconInvert, shape = RoundedCornerShape(8.dp))
                    .clip(shape = RoundedCornerShape(8.dp))) {
                    Image(painter = painterResource(R.drawable.img_loading_failed),
                          contentDescription = null,
                          contentScale = ContentScale.Crop,
                          modifier = Modifier.padding(16.dp))
                }
            }

            Icon(painter = painterResource(if (isAddSong) R.drawable.ic_24_plus_check else R.drawable.ic_24_plus_circle),
                 contentDescription = null,
                 tint = if (isAddSong) IconProduct else IconBackgroundPrimary,
                 modifier = Modifier
                     .clip(CircleShape)
                     .clickable(onClick = onAddClick))

            Icon(painter = painterResource(R.drawable.ic_24_plus_arrrow_down),
                 contentDescription = null,
                 tint = IconSecondary,
                 modifier = Modifier
                     .clip(shape = RoundedCornerShape(8.dp))
                     .debounceClickable(onClick = onDownloadClick))

            Icon(painter = painterResource(R.drawable.ic_24_bullet),
                 contentDescription = null,
                 tint = IconSecondary,
                 modifier = Modifier
                     .clip(shape = RoundedCornerShape(8.dp))
                     .debounceClickable(onClick = onMoreClick))

            Spacer(modifier = Modifier.weight(1f))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(R.drawable.ic_32_remdom),
                     contentDescription = null,
                     tint = IconSecondary,
                     modifier = Modifier
                         .clip(shape = RoundedCornerShape(8.dp))
                         .debounceClickable(onClick = onShuffleClick))

                Box(modifier = modifierPausePlay
                    .background(color = SurfaceProduct, shape = CircleShape)
                    .clip(CircleShape)
                    .debounceClickable(onClick = onPausePlayClick)) {
                    Icon(painter = painterResource(R.drawable.ic_32_play),
                         contentDescription = null,
                         tint = IconBackgroundDark,
                         modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun AlbumInfoButtonPreview() {
    E2MP3Theme {
        SingleButton(isAddSong = false,
                     single = SongsModel(title = "Album Title", releaseDate = "24-02-2025"),
                     artistList = listOf(UsersModel(name = "Artist 1", avatarUrl = ""), UsersModel(name = "Artist 2", avatarUrl = "")),
                     onOwnerClick = {},
                     onAddClick = {},
                     onDownloadClick = {},
                     onMoreClick = {},
                     onShuffleClick = {},
                     onPausePlayClick = {})
    }
}