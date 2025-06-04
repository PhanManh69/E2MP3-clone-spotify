package com.emanh.rootapp.presentation.ui.lyrics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.Title5Bold
import com.emanh.rootapp.presentation.theme.Title6Bold
import com.emanh.rootapp.presentation.ui.lyrics.composable.LyricsButtonControl
import com.emanh.rootapp.presentation.ui.lyrics.composable.LyricsTopbar
import com.emanh.rootapp.presentation.ui.player.PlayerViewModel
import com.emanh.rootapp.utils.faunchedEffectAvatar
import com.emanh.rootapp.utils.formatTime
import kotlinx.coroutines.launch
import kotlin.math.roundToLong

@Composable
fun LyricsScreen(
    lyrics: String?,
    totalDuration: Long,
    currentProgress: Float,
    isPlayed: Boolean,
    song: SongsModel,
    onPlayPauseClick: (Boolean) -> Unit,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (Float) -> Unit,
) {
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val currentPosition = (currentProgress * totalDuration).roundToLong()
    val remainingTime = totalDuration - currentPosition
    val currentTimeFormatted = formatTime(currentPosition)
    val remainingTimeFormatted = "-" + formatTime(remainingTime)

    LyricsBottomSheet(lyrics = lyrics,
                      currentTime = currentTimeFormatted,
                      remainingTime = remainingTimeFormatted,
                      valueSlider = currentProgress,
                      isPlayed = isPlayed,
                      song = song,
                      onDismiss = playerViewModel::hideLyrics,
                      onDownClick = playerViewModel::onDownLyricsClick,
                      onFlagClick = {},
                      onPlayPauseClick = onPlayPauseClick,
                      onShareClick = {},
                      onValueChange = onValueChange,
                      onValueChangeFinished = onValueChangeFinished)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LyricsBottomSheet(
    modifier: Modifier = Modifier,
    lyrics: String?,
    currentTime: String,
    remainingTime: String,
    valueSlider: Float,
    isPlayed: Boolean,
    song: SongsModel,
    onDismiss: () -> Unit,
    onDownClick: () -> Unit,
    onFlagClick: () -> Unit,
    onShareClick: () -> Unit,
    onPlayPauseClick: (Boolean) -> Unit,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (Float) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val backgroundColor = faunchedEffectAvatar(song.avatarUrl)

    ModalBottomSheet(modifier = modifier,
                     sheetState = sheetState,
                     onDismissRequest = onDismiss,
                     contentColor = SurfacePrimary,
                     scrimColor = SurfacePrimary,
                     shape = RoundedCornerShape(8.dp),
                     contentWindowInsets = { WindowInsets(0) },
                     dragHandle = null) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(top = statusBarHeight + 16.dp, bottom = 32.dp),
               verticalArrangement = Arrangement.spacedBy(16.dp)) {
            LyricsTopbar(gradientEdgeColor = backgroundColor, song = song, onDownClick = {
                scope.launch {
                    sheetState.hide()
                    onDownClick()
                }
            }, onFlagClick = onFlagClick)

            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                    item {
                        if (lyrics == null) {
                            Text(text = stringResource(R.string.not_lyrics_song),
                                 color = TextPrimary,
                                 style = Title5Bold,
                                 textAlign = TextAlign.Center,
                                 modifier = Modifier
                                     .padding(horizontal = 32.dp)
                                     .padding(top = 48.dp))
                        } else {
                            Text(text = lyrics, color = TextBackgroundPrimary, style = Title6Bold, modifier = Modifier.padding(horizontal = 32.dp))
                        }
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .align(Alignment.TopCenter)
                    .background(brush = Brush.verticalGradient(0f to backgroundColor, 1f to Color.Transparent)))

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .align(Alignment.BottomCenter)
                    .background(brush = Brush.verticalGradient(0f to Color.Transparent, 1f to backgroundColor)))
            }

            LyricsButtonControl(isPlayed = isPlayed,
                                currentTime = currentTime,
                                remainingTime = remainingTime,
                                valueSlider = valueSlider,
                                onPlayPauseClick = onPlayPauseClick,
                                onShareClick = onShareClick,
                                onValueChange = onValueChange,
                                onValueChangeFinished = onValueChangeFinished)
        }
    }
}

@Preview
@Composable
private fun LyricsScreenPreview() {
    E2MP3Theme {
        var progress by remember { mutableFloatStateOf(0f) }
        val totalDuration = 175f
        val currentPosition = (progress * totalDuration).roundToLong()
        val remainingTime = totalDuration.roundToLong() - currentPosition
        val currentTimeFormatted = formatTime(currentPosition)
        val remainingTimeFormatted = "-" + formatTime(remainingTime)

        LyricsBottomSheet(lyrics = null,
                          currentTime = currentTimeFormatted,
                          remainingTime = remainingTimeFormatted,
                          valueSlider = progress,
                          isPlayed = false,
                          song = SongsModel(title = "Nơi này có anh",
                                            subtitle = "Sơn Tùng MTP",
                                            avatarUrl = "https://lh3.googleusercontent.com/guPkUMfq6XoStEBVJwwWMD5dttVFgi0OXpzHZ0hvPD0kWxdVkrMbMCBNRDZlUy_N953vMI_r-6x1X_IEWQ=w544-h544-l90-rj"),
                          onDismiss = {},
                          onDownClick = {},
                          onFlagClick = {},
                          onPlayPauseClick = {},
                          onShareClick = {},
                          onValueChange = { progress = it },
                          onValueChangeFinished = {})
    }
}