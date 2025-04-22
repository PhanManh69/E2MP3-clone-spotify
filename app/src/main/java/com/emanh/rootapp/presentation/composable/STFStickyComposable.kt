package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.AlphaN100_20
import com.emanh.rootapp.presentation.theme.Body5Bold
import com.emanh.rootapp.presentation.theme.Body5Regular
import com.emanh.rootapp.presentation.theme.Body7Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.GreyN100
import com.emanh.rootapp.presentation.theme.IconBackgroundDark
import com.emanh.rootapp.presentation.theme.IconBackgroundPrimary
import com.emanh.rootapp.presentation.theme.IconProduct
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.StickyDefault
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceProductSuperDark
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary
import com.emanh.rootapp.presentation.theme.TextBackgroundSecondary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.utils.loadProgress
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class STFStickyType {
    Default, Player
}

@Composable
fun STFSticky(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    isPlayed: Boolean = false,
    isLiked: Boolean = false,
    currentProgress: Float = 0f,
    type: STFStickyType = STFStickyType.Default,
    onBackClick: () -> Unit = {},
    onAddLikeSong: (Boolean) -> Unit = {},
    onPlayPauseClick: (Boolean) -> Unit = {}
) {
    when (type) {
        STFStickyType.Default -> STFStickyDefault(modifier = modifier,
                                                  title = title,
                                                  isPlayed = isPlayed,
                                                  onBackClick = onBackClick,
                                                  onPlayPauseClick = onPlayPauseClick)

        STFStickyType.Player -> STFStickyPlayer(modifier = modifier,
                                                title = title,
                                                subtitle = subtitle,
                                                isPlayed = isPlayed,
                                                isLiked = isLiked,
                                                currentProgress = currentProgress,
                                                onAddLikeSong = onAddLikeSong,
                                                onPlayPauseClick = onPlayPauseClick)
    }
}

@Composable
private fun STFStickyDefault(
    modifier: Modifier = Modifier, title: String, isPlayed: Boolean, onBackClick: () -> Unit, onPlayPauseClick: (Boolean) -> Unit
) {
    val played = remember(isPlayed) { mutableStateOf(isPlayed) }

    ConstraintLayout(modifier = modifier) {
        val (header, icon) = createRefs()

        Box(modifier = Modifier
            .background(color = SurfaceProductSuperDark)
            .constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            Box(modifier = Modifier.background(brush = StickyDefault)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 60.dp, bottom = 18.dp)) {
                    Icon(painter = painterResource(R.drawable.ic_24_musical_chevron_lt),
                         contentDescription = null,
                         tint = IconPrimary,
                         modifier = Modifier
                             .align(Alignment.CenterStart)
                             .clip(shape = RoundedCornerShape(8.dp))
                             .debounceClickable { onBackClick() })

                    Text(text = title,
                         color = TextPrimary,
                         style = Body5Bold,
                         textAlign = TextAlign.Center,
                         modifier = Modifier
                             .padding(horizontal = 48.dp)
                             .align(Alignment.Center))
                }
            }
        }

        Row(modifier = Modifier.constrainAs(icon) {
            top.linkTo(header.bottom)
            bottom.linkTo(header.bottom)
            end.linkTo(parent.end)
        }) {
            IconButton(modifier = Modifier
                .size(48.dp)
                .background(color = SurfaceProduct, shape = CircleShape)
                .clip(CircleShape), onClick = {
                played.value = !played.value
                onPlayPauseClick(played.value)
            }) {
                Icon(painter = painterResource(if (played.value) R.drawable.ic_32_pause else R.drawable.ic_32_play),
                     contentDescription = null,
                     tint = IconBackgroundDark,
                     modifier = Modifier.size(32.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
private fun STFStickyPlayer(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    isPlayed: Boolean,
    isLiked: Boolean,
    currentProgress: Float,
    onAddLikeSong: (Boolean) -> Unit,
    onPlayPauseClick: (Boolean) -> Unit
) {
    val played = remember(isPlayed) { mutableStateOf(isPlayed) }
    val liked = remember(isLiked) { mutableStateOf(isLiked) }

    Box(modifier = modifier.background(color = SurfaceProductSuperDark)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 60.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = title, color = TextBackgroundPrimary, style = Body5Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)

                Text(text = subtitle, color = TextBackgroundSecondary, style = Body7Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            Icon(painter = painterResource(if (liked.value) R.drawable.ic_24_plus_check else R.drawable.ic_24_plus_circle),
                 contentDescription = null,
                 tint = if (liked.value) IconProduct else IconBackgroundPrimary,
                 modifier = Modifier
                     .clip(shape = RoundedCornerShape(8.dp))
                     .debounceClickable {
                         liked.value = true
                         onAddLikeSong(liked.value)
                     })

            Icon(painter = painterResource(if (played.value) R.drawable.ic_24_pause else R.drawable.ic_24_play),
                 contentDescription = null,
                 tint = IconBackgroundPrimary,
                 modifier = Modifier
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
                    gapSize = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .align(Alignment.BottomCenter),
            )
        }
    }
}

@Preview
@Composable
fun StickyDefaultPreview() {
    E2MP3Theme {
        STFSticky(title = "Title", isPlayed = false, type = STFStickyType.Default, onBackClick = {}, onPlayPauseClick = {})
    }
}

@Preview
@Composable
fun StickyPlayerPreview() {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var isPlayed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var progressJob by remember { mutableStateOf<Job?>(null) }

    E2MP3Theme {
        STFSticky(title = "Title",
                  subtitle = "Subtitle",
                  isPlayed = isPlayed,
                  currentProgress = currentProgress,
                  type = STFStickyType.Player,
                  onAddLikeSong = {},
                  onPlayPauseClick = { played ->
                      isPlayed = played

                      if (played && progressJob == null) {
                          progressJob = scope.launch {
                              loadProgress(isPlaying = { isPlayed }, updateProgress = { progress -> currentProgress = progress }, onFinish = {
                                  isPlayed = false
                                  progressJob = null
                              })
                          }
                      }
                  })
    }
}