package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shadowCustom
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.AlphaN100_20
import com.emanh.rootapp.presentation.theme.AlphaN100_60
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.GreyN100
import com.emanh.rootapp.presentation.theme.IconBackgroundPrimary
import com.emanh.rootapp.presentation.theme.SurfaceSecondary
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary
import com.emanh.rootapp.utils.MyConstant.IMAGE_URL
import com.emanh.rootapp.utils.faunchedEffectAvatar
import com.emanh.rootapp.utils.loadProgress
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun STFPlayerSticky(
    modifier: Modifier = Modifier,
    song: SongsModel,
    isPlayed: Boolean,
    currentProgress: Float,
    onPlayPauseClick: (Boolean) -> Unit,
    onMusicalClick: () -> Unit,
    onPlayerStickyClick: () -> Unit
) {
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val played = remember(isPlayed) { mutableStateOf(isPlayed) }
    val animatedPadding by animateDpAsState(if (toggled) 2.dp else 0.dp)
    val backgroundColor = faunchedEffectAvatar(song.avatarUrl)

    Box(modifier = modifier
        .padding(horizontal = 8.dp)
        .clip(shape = RoundedCornerShape(8.dp))
        .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
        .debounceClickable(indication = null) {
            coroutineScope.launch {
                toggled = true
                delay(100L)
                toggled = false
                onPlayerStickyClick()
            }
        }) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            SubcomposeAsyncImage(model = song.avatarUrl, contentDescription = null, contentScale = ContentScale.Crop, loading = {
                Box(modifier = Modifier
                    .shimmerEffect()
                    .shadowCustom(shapeRadius = 8.dp)
                    .size(40.dp)
                    .clip(shape = RoundedCornerShape(8.dp)))
            }, modifier = Modifier
                .shadowCustom(shapeRadius = 8.dp)
                .size(40.dp)
                .padding(animatedPadding)
                .clip(shape = RoundedCornerShape(8.dp)))

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                STFMarqueeText(text = song.title.orEmpty(),
                               textColor = TextBackgroundPrimary,
                               textStyle = Body6Regular,
                               gradientEdgeColor = backgroundColor)

                Text(text = song.subtitle.orEmpty(), color = AlphaN100_60, style = Body6Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Icon(painter = painterResource(R.drawable.ic_32_musical_box),
                 contentDescription = null,
                 tint = AlphaN100_60,
                 modifier = Modifier
                     .size(32.dp)
                     .clip(shape = RoundedCornerShape(8.dp))
                     .debounceClickable { onMusicalClick() })

            Spacer(modifier = Modifier.width(16.dp))

            Icon(painter = painterResource(if (played.value) R.drawable.ic_32_pause else R.drawable.ic_32_play),
                 contentDescription = null,
                 tint = IconBackgroundPrimary,
                 modifier = Modifier
                     .size(32.dp)
                     .clip(shape = RoundedCornerShape(8.dp))
                     .clickable {
                         played.value = !played.value
                         onPlayPauseClick(played.value)
                     })
        }

        if (currentProgress != 0f) {
            LinearProgressIndicator(
                    progress = { currentProgress },
                    color = GreyN100,
                    trackColor = AlphaN100_20,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .padding(horizontal = 8.dp)
                        .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
fun STFPlayerStickyLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal = 8.dp)
        .clip(shape = RoundedCornerShape(8.dp))
        .shimmerEffect())
}

@Composable
fun STFPlayerStickyEmpty(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .padding(horizontal = 8.dp)
        .clip(shape = RoundedCornerShape(8.dp))
        .background(color = SurfaceSecondary, shape = RoundedCornerShape(8.dp))
        .debounceClickable(indication = null, onClick = {})) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .shadowCustom(shapeRadius = 8.dp)
                .size(40.dp)
                .clip(shape = RoundedCornerShape(8.dp)))

            Spacer(modifier = Modifier.weight(1f))

            Icon(painter = painterResource(R.drawable.ic_32_musical_box),
                 contentDescription = null,
                 tint = AlphaN100_60,
                 modifier = Modifier
                     .size(32.dp)
                     .clip(shape = RoundedCornerShape(8.dp)))

            Spacer(modifier = Modifier.width(16.dp))

            Icon(painter = painterResource(R.drawable.ic_32_pause),
                 contentDescription = null,
                 tint = IconBackgroundPrimary,
                 modifier = Modifier
                     .size(32.dp)
                     .clip(shape = RoundedCornerShape(8.dp)))
        }
    }
}

@Preview
@Composable
fun PlayerSticlyPreview() {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var isPlayed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var progressJob by remember { mutableStateOf<Job?>(null) }

    E2MP3Theme {
        STFPlayerSticky(song = SongsModel(avatarUrl = IMAGE_URL, title = "Nơi này có anh", subtitle = "Sơn Tùng M-TP"),
                        isPlayed = isPlayed,
                        currentProgress = currentProgress,
                        onMusicalClick = {},
                        onPlayerStickyClick = {},
                        onPlayPauseClick = { played ->
                            isPlayed = played

                            if (played && progressJob == null) {
                                progressJob = scope.launch {
                                    loadProgress(timeSeconds = 2000,
                                                 isPlaying = { isPlayed },
                                                 updateProgress = { progress -> currentProgress = progress },
                                                 onFinish = {
                                                     isPlayed = false
                                                     progressJob = null
                                                 })
                                }
                            }
                        })
    }
}