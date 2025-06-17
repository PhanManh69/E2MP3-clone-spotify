package com.emanh.rootapp.presentation.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.ui.lyrics.LyricsScreen
import com.emanh.rootapp.presentation.ui.player.composable.PlayerArtistAbout
import com.emanh.rootapp.presentation.ui.player.composable.PlayerAvatar
import com.emanh.rootapp.presentation.ui.player.composable.PlayerContentButton
import com.emanh.rootapp.presentation.ui.player.composable.PlayerLyrics
import com.emanh.rootapp.presentation.ui.player.composable.PlayerTopbar
import com.emanh.rootapp.utils.MyConstant.NOT_AVATAR
import com.emanh.rootapp.utils.faunchedEffectAvatar
import com.emanh.rootapp.utils.formatTime
import kotlin.math.roundToLong

@Composable
fun PlayerScreen(
    isPlayed: Boolean,
    headerTitle: String,
    headerSubtitle: String,
    totalDuration: Long,
    currentProgress: Float,
    currentUser: UserInfo,
    song: SongsModel,
    artistsList: List<UsersModel>,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (Float) -> Unit,
    onPlayPauseClick: (Boolean) -> Unit,
) {
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val uiState by playerViewModel.uiState.collectAsState()
    val currentPosition = (currentProgress * totalDuration).roundToLong()
    val remainingTime = totalDuration - currentPosition
    val currentTimeFormatted = formatTime(currentPosition)
    val remainingTimeFormatted = "-" + formatTime(remainingTime)

    LaunchedEffect(song) {
        playerViewModel.getSongLike(songId = song.id, currentUserId = currentUser.id)
        playerViewModel.getUserFollowing(artistsList = artistsList, currentUserId = currentUser.id)
        playerViewModel.getLyrics(trackName = song.title.orEmpty(), artistName = song.subtitle.orEmpty())

        artistsList.forEach { artist ->
            playerViewModel.loadListenerMonth(artist.id)
        }
    }

    PlayerBottomSheet(song = song,
                      isPlayed = isPlayed,
                      isAddSong = uiState.isAddSong,
                      followingArtists = uiState.followingArtists,
                      headerTitle = headerTitle,
                      headerSubtitle = headerSubtitle,
                      viewMonthArtists = uiState.viewMonthArtists,
                      currentTime = currentTimeFormatted,
                      remainingTime = remainingTimeFormatted,
                      lyrics = uiState.lyrics,
                      valueSlider = currentProgress,
                      artistsList = artistsList,
                      onDismiss = playerViewModel::hidePlayer,
                      onDownClick = playerViewModel::onDownPlayerClick,
                      onMoreClick = {},
                      onAddClick = {
                          playerViewModel.onAddClick(song.id, currentUser.id)
                      },
                      onShuffleClick = {},
                      onBackClick = onBackClick,
                      onPlayPauseClick = onPlayPauseClick,
                      onForwardClick = onForwardClick,
                      onLoopClick = {},
                      onMusicalBoxClick = {},
                      onShareClick = {},
                      onListClick = {},
                      onShowLyrics = playerViewModel::showLyrics,
                      onFollowClick = {
                          playerViewModel.onFollowClick(it, currentUser.id)
                      },
                      onArtistsClick = {
                          playerViewModel.hidePlayer()
                          playerViewModel.goToArtist(it)
                      },
                      onValueChange = onValueChange,
                      onValueChangeFinished = onValueChangeFinished)

    if (uiState.showLyricsSheet) {
        LyricsScreen(song = song,
                     isPlayed = isPlayed,
                     lyrics = uiState.lyrics,
                     totalDuration = totalDuration,
                     currentProgress = currentProgress,
                     onPlayPauseClick = onPlayPauseClick,
                     onValueChange = onValueChange,
                     onValueChangeFinished = onValueChangeFinished)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerBottomSheet(
    modifier: Modifier = Modifier,
    headerTitle: String,
    headerSubtitle: String,
    currentTime: String,
    remainingTime: String,
    lyrics: String?,
    viewMonthArtists: Map<Long, Long>,
    valueSlider: Float,
    isPlayed: Boolean,
    isAddSong: Boolean,
    followingArtists: Set<Long>,
    song: SongsModel,
    artistsList: List<UsersModel>,
    onDismiss: () -> Unit,
    onDownClick: () -> Unit,
    onMoreClick: () -> Unit,
    onAddClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit,
    onLoopClick: () -> Unit,
    onMusicalBoxClick: () -> Unit,
    onShareClick: () -> Unit,
    onListClick: () -> Unit,
    onShowLyrics: () -> Unit,
    onFollowClick: (Long) -> Unit,
    onArtistsClick: (Long) -> Unit,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (Float) -> Unit,
    onPlayPauseClick: (Boolean) -> Unit,
) {
    val state = rememberScrollState()
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
            .background(SurfacePrimary)
            .verticalScroll(state)) {
            Box {
                PlayerAvatar(avatarUrl = song.avatarUrl ?: NOT_AVATAR)

                PlayerTopbar(modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = statusBarHeight + 16.dp),
                             title = headerTitle,
                             subtitle = headerSubtitle,
                             onDownClick = onDownClick,
                             onMoreClick = onMoreClick)

                PlayerContentButton(modifier = Modifier
                    .padding(bottom = 86.dp)
                    .align(Alignment.BottomCenter),
                                    isPlayed = isPlayed,
                                    isAddSong = isAddSong,
                                    currentTime = currentTime,
                                    remainingTime = remainingTime,
                                    valueSlider = valueSlider,
                                    song = song,
                                    onAddClick = onAddClick,
                                    onShuffleClick = onShuffleClick,
                                    onBackClick = onBackClick,
                                    onPlayPauseClick = onPlayPauseClick,
                                    onForwardClick = onForwardClick,
                                    onLoopClick = onLoopClick,
                                    onMusicalBoxClick = onMusicalBoxClick,
                                    onShareClick = onShareClick,
                                    onListClick = onListClick,
                                    onValueChange = onValueChange,
                                    onValueChangeFinished = onValueChangeFinished)
            }

            PlayerLyrics(modifier = Modifier
                .padding(horizontal = 16.dp)
                .offset(y = (-72).dp),
                         lyrics = lyrics,
                         backgroundColor = backgroundColor,
                         onShowLyrics = onShowLyrics)

            PlayerArtistAbout(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .offset(y = (-48).dp),
                    followingArtists = followingArtists,
                    artistsList = artistsList,
                    viewMonthArtists = viewMonthArtists,
                    onFollowClick = onFollowClick,
                    onArtistsClick = onArtistsClick,
            )
        }
    }
}

@Preview
@Composable
private fun PlayerScreenPreview() {
    E2MP3Theme {
        var progress by remember { mutableFloatStateOf(0f) }
        val totalDuration = 175f
        val currentPosition = (progress * totalDuration).roundToLong()
        val remainingTime = totalDuration.roundToLong() - currentPosition
        val currentTimeFormatted = formatTime(currentPosition)
        val remainingTimeFormatted = "-" + formatTime(remainingTime)

        PlayerBottomSheet(isPlayed = false,
                          isAddSong = true,
                          followingArtists = setOf(1, 3, 5),
                          headerTitle = "Playlist",
                          headerSubtitle = "Hip Hop Mix",
                          viewMonthArtists = emptyMap(),
                          currentTime = currentTimeFormatted,
                          remainingTime = remainingTimeFormatted,
                          lyrics = "Em là ai từ đâu bước đến nơi đây dịu dàng chân phương (dịu dàng chân phương)\n" + "Em là ai tựa như ánh nắng ban mai ngọt ngào trong sương (ngọt ngào trong sương)\n" + "Ngắm em thật lâu, con tim anh yếu mềm\n" + "Đắm say từ phút đó, từng giây trôi yêu thêm\n" + "\n" + "Bao ngày qua bình minh đánh thức xua tan bộn bề nơi anh (bộn bề nơi anh)\n" + "Bao ngày qua niềm thương nỗi nhớ bay theo bầu trời trong xanh (bầu trời trong xanh)\n" + "Liếc đôi hàng mi mong manh, anh thẫn thờ\n" + "Muốn hôn nhẹ mái tóc, bờ môi em, anh mơ\n" + "\n" + "Cầm tay anh (anh), dựa vai anh (anh)\n" + "Kề bên anh, nơi này có anh\n" + "Gió mang câu tình ca, ngàn ánh sao vụt qua, nhẹ ôm lấy em\n" + "(Yêu em, thương em, con tim anh chân thành)\n" + "Cầm tay anh (anh), dựa vai anh (anh)\n" + "Kề bên anh, nơi này có anh\n" + "Khép đôi mi thật lâu\n" + "Nguyện mãi bên cạnh nhau, yêu say đắm như ngày đầu\n" + "\n" + "Mùa xuân đến bình yên, cho anh những giấc mơ\n" + "Hạ lưu giữ ngày mưa, ngọt ngào nên thơ\n" + "Mùa thu lá vàng rơi, đông sang, anh nhớ em",
                          valueSlider = progress,
                          song = SongsModel(title = "Nơi này có anh",
                                            subtitle = "Sơn Tùng MTP",
                                            timeline = "03:03",
                                            avatarUrl = "https://lh3.googleusercontent.com/5mLb1J5XVQMLi395i1w24u2lC_W4tBznuRrzz8Kw0mxQKOrCHBGCbYx63jxBJI8QHcUwiYsJmDPY0igy=w544-h544-l90-rj"),
                          artistsList = listOf(UsersModel(isArtist = true,
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
                          onDismiss = {},
                          onDownClick = {},
                          onMoreClick = {},
                          onAddClick = {},
                          onShuffleClick = {},
                          onBackClick = {},
                          onPlayPauseClick = {},
                          onForwardClick = {},
                          onLoopClick = {},
                          onMusicalBoxClick = {},
                          onShareClick = {},
                          onListClick = {},
                          onShowLyrics = {},
                          onFollowClick = {},
                          onArtistsClick = {},
                          onValueChange = { progress = it },
                          onValueChangeFinished = {})
    }
}