package com.emanh.rootapp.presentation.ui.artist.composable

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body7Bold
import com.emanh.rootapp.presentation.theme.Body7Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundDark
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.IconSecondary
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceSecondaryInvert
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary

@Composable
fun ArtistButton(
    modifier: Modifier = Modifier,
    modifierPausePlay: Modifier = Modifier,
    viewMonth: Long,
    avatarSongUrl: String,
    isFollowing: Boolean,
    onFollowClick: () -> Unit,
    onMoreClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onPausePlayClick: () -> Unit
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = "$viewMonth ${stringResource(R.string.view_month)}", color = TextSecondary, style = Body7Regular)

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
                               .data(avatarSongUrl)
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

            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = IconPrimary, shape = RoundedCornerShape(8.dp))
                .clickable(onClick = onFollowClick)) {
                Text(text = if (isFollowing) stringResource(R.string.unfollow) else stringResource(R.string.follow),
                     color = TextPrimary,
                     style = Body7Bold,
                     modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
            }

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
private fun PlaylistInfoButtonPreview() {
    E2MP3Theme {
        ArtistButton(viewMonth = 10283,
                     avatarSongUrl = "",
                     isFollowing = true,
                     onFollowClick = {},
                     onMoreClick = {},
                     onShuffleClick = {},
                     onPausePlayClick = {})
    }
}