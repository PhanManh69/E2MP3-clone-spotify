package com.emanh.rootapp.presentation.ui.player.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFChips
import com.emanh.rootapp.presentation.composable.STFChipsSize
import com.emanh.rootapp.presentation.composable.STFChipsType
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body5Bold
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconInvert
import com.emanh.rootapp.presentation.theme.SurfaceSecondary
import com.emanh.rootapp.presentation.theme.SurfaceShadow
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.theme.Title5Bold

@Composable
fun PlayerArtistAbout(
    modifier: Modifier = Modifier,
    artistsList: List<UsersModel>,
    viewMonthArtists: Map<Int, Int>,
    onFollowClick: (Int) -> Unit,
    onArtistsClick: (Int) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(24.dp)) {
        artistsList.forEach { artist ->
            var isImageLoaded by remember(artist.id) { mutableStateOf(false) }
            var isLoadFailed by remember(artist.id) { mutableStateOf(false) }
            val monthlyListeners = viewMonthArtists[artist.id] ?: 0

            Column(modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = SurfaceSecondary, shape = RoundedCornerShape(16.dp))
                .debounceClickable(indication = null, onClick = { onArtistsClick(artist.id) })) {
                Box {
                    if (!isLoadFailed) {
                        AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                    .align(Alignment.Center)
                                    .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(artist.avatarUrl)
                                    .crossfade(true)
                                    .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> isLoadFailed = true })
                                    .build(),
                                contentDescription = "Artist image: ${artist.name}",
                                contentScale = ContentScale.Crop,
                        )
                    } else {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .background(color = IconInvert, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .align(Alignment.Center)) {
                            Image(painter = painterResource(R.drawable.img_loading_failed),
                                  contentDescription = "Image load failed",
                                  contentScale = ContentScale.Crop,
                                  modifier = Modifier.padding(16.dp))
                        }
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .align(Alignment.TopCenter)
                        .background(brush = Brush.verticalGradient(0f to SurfaceShadow, 1f to Color.Transparent)))

                    Text(text = stringResource(R.string.about_the_artist),
                         color = TextBackgroundPrimary,
                         style = Body5Bold,
                         modifier = Modifier
                             .padding(16.dp)
                             .align(Alignment.TopStart))
                }

                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = artist.name.orEmpty(), color = TextPrimary, style = Title5Bold)

                        Text(text = "$monthlyListeners ${stringResource(R.string.monthly_listeners)}", color = TextSecondary, style = Body6Regular)
                    }

                    STFChips(text = stringResource(R.string.follow),
                             size = STFChipsSize.Normal,
                             type = STFChipsType.Stroke,
                             onClick = { onFollowClick(artist.id) })
                }
            }
        }
    }
}

@Preview
@Composable
private fun PlayerArtistAboutPreview() {
    E2MP3Theme {
        PlayerArtistAbout(artistsList = listOf(UsersModel(isArtist = true,
                                                          username = "tlinh",
                                                          email = "tlinh@gmail.com",
                                                          password = "Phanmanh24",
                                                          avatarUrl = "https://lh3.googleusercontent.com/duvPkHjgosJKoOXe9xztYOREcD_kKHgB4naWRWlx6o44-Zy_35ZxlWQYFGVAvrz7Br7qFQnyI4hnIpM=w1920-h800-p-l90-rj",
                                                          name = "tlinh",
                                                          followers = 648394),
                                               UsersModel(isArtist = true,
                                                          username = "tlinh",
                                                          email = "tlinh@gmail.com",
                                                          password = "Phanmanh24",
                                                          avatarUrl = "https://lh3.googleusercontent.com/duvPkHjgosJKoOXe9xztYOREcD_kKHgB4naWRWlx6o44-Zy_35ZxlWQYFGVAvrz7Br7qFQnyI4hnIpM=w1920-h800-p-l90-rj",
                                                          name = "tlinh",
                                                          followers = 648394)),
                          viewMonthArtists = mapOf(Pair(3980, 2), Pair(18298, 29)),
                          onFollowClick = {},
                          onArtistsClick = {})
    }
}