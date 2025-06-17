package com.emanh.rootapp.presentation.ui.playlistyour

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFChips
import com.emanh.rootapp.presentation.composable.STFChipsSize
import com.emanh.rootapp.presentation.composable.STFChipsType
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.composable.STFLoading
import com.emanh.rootapp.presentation.composable.STFPlaylistHeader
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body2Bold
import com.emanh.rootapp.presentation.theme.Body7Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundDark
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.ui.playlistyour.composable.PlaylistYourInfoButton
import com.emanh.rootapp.utils.MyConstant.NOT_AVATAR
import com.emanh.rootapp.utils.MyConstant.fakeSongs
import com.emanh.rootapp.utils.faunchedEffectAvatar

@Composable
fun PlaylistYourScreen(onItemClick: (Long, String) -> Unit) {
    val viewModel = hiltViewModel<PlaylistYourViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading || uiState.owner == null || uiState.playlist == null) {
        STFLoading()
    } else {
        PlaylistYourScaffold(owner = uiState.owner!!,
                             playlist = uiState.playlist!!,
                             time = viewModel.totalTime(uiState.songsList),
                             songsList = uiState.songsList,
                             songsRecommendList = uiState.songsRecommendList,
                             onOwnerClick = {},
                             onDownloadClick = {},
                             onMoreClick = {},
                             onShuffleClick = {},
                             onPausePlayClick = {},
                             onEditClick = {},
                             onItemClick = { onItemClick(it, uiState.playlist!!.title.orEmpty()) },
                             onAddSongClick = { viewModel.onIconClick(it) },
                             onMoreSongClick = {},
                             onRefreshClick = viewModel::onRefreshClick,
                             onBackClick = {})
    }
}

@Composable
private fun PlaylistYourScaffold(
    modifier: Modifier = Modifier,
    time: String,
    owner: UsersModel,
    playlist: PlaylistsModel,
    songsList: List<SongsModel>,
    songsRecommendList: List<SongsModel>,
    onOwnerClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onMoreClick: () -> Unit,
    onEditClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onPausePlayClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onAddSongClick: (Long) -> Unit,
    onMoreSongClick: (Long) -> Unit,
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit,
) {
    val context = LocalDensity.current
    val backgroundColor = faunchedEffectAvatar(playlist.avatarUrl)
    val avatarUrl = remember(songsList) {
        if (songsList.isNotEmpty()) {
            songsList.random().avatarUrl ?: NOT_AVATAR
        } else {
            NOT_AVATAR
        }
    }

    var position by remember { mutableStateOf(Offset.Zero) }
    var positionY by remember { mutableStateOf(0.dp) }
    var scrollDirection by remember { mutableIntStateOf(0) }
    val headerAlpha by animateFloatAsState(targetValue = if (scrollDirection == 1 || positionY <= 64.dp) 1f else 0f,
                                           animationSpec = tween(durationMillis = 0))
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y

                scrollDirection = when {
                    delta > 0 -> -1
                    delta < 0 -> 1
                    else -> scrollDirection
                }

                return Offset(0f, 0f)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.nestedScroll(nestedScrollConnection), contentPadding = PaddingValues(top = 88.dp)) {
            item {
                Box {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(398.dp)
                        .offset(y = (-88).dp)
                        .background(brush = Brush.verticalGradient(0f to backgroundColor, 1f to Color.Transparent)))
                    PlaylistYourInfoButton(modifier = Modifier.padding(horizontal = 16.dp),
                                           time = time,
                                           avatarSongUrl = avatarUrl,
                                           owner = owner,
                                           playlist = playlist,
                                           onDownloadClick = onDownloadClick,
                                           onMoreClick = onMoreClick,
                                           onShuffleClick = onShuffleClick,
                                           onPausePlayClick = onPausePlayClick,
                                           onOwnerClick = onOwnerClick,
                                           onEditClick = onEditClick,
                                           modifierPausePlay = Modifier.onGloballyPositioned { coordinates ->
                                               position = coordinates.positionInWindow()
                                               with(context) {
                                                   positionY = position.y.toDp()
                                               }
                                           })
                }
            }

            items(count = songsList.size, key = { index ->
                val item = songsList[index]
                "playlist-songs-${item.id}-${index}"
            }) { index ->
                val item = songsList[index]

                STFItem(modifier = Modifier.offset(y = (-168).dp),
                        imageUrl = item.avatarUrl ?: NOT_AVATAR,
                        title = item.title.orEmpty(),
                        label = item.subtitle.orEmpty(),
                        iconId = R.drawable.ic_24_bullet,
                        type = STFItemType.Music,
                        size = STFItemSize.Small,
                        onItemClick = {
                            onItemClick(item.id)
                        },
                        onIconClick = {
                            onMoreSongClick(item.id)
                        })
            }

            item {
                Column(modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 36.dp, bottom = 16.dp)
                    .offset(y = (-168).dp),
                       verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = stringResource(R.string.song_recomment), color = TextPrimary, style = Body2Bold)

                    Text(text = stringResource(R.string.based_songs_in_this_playlist), color = TextSecondary, style = Body7Regular)
                }
            }

            items(count = songsRecommendList.size, key = { index ->
                val item = songsRecommendList[index]
                "recommend-songs-${item.id}-${index}"
            }) { index ->
                val item = songsRecommendList[index]

                STFItem(modifier = Modifier.offset(y = (-168).dp),
                        imageUrl = item.avatarUrl ?: NOT_AVATAR,
                        title = item.title.orEmpty(),
                        label = item.subtitle.orEmpty(),
                        iconId = R.drawable.ic_24_plus_circle,
                        type = STFItemType.Music,
                        size = STFItemSize.Small,
                        onItemClick = {
                            onItemClick(item.id)
                        },
                        onIconClick = {
                            onAddSongClick(item.id)
                        })
            }

            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp), contentAlignment = Alignment.Center) {
                    STFChips(modifier = Modifier.offset(y = (-168).dp),
                             text = stringResource(R.string.refresh),
                             size = STFChipsSize.Normal,
                             type = STFChipsType.Stroke,
                             onClick = onRefreshClick)
                }
            }
        }

        ConstraintLayout(modifier = Modifier.align(Alignment.TopCenter)) {
            val (header, icon) = createRefs()
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .alpha(headerAlpha)
                .background(backgroundColor)
                .nestedScroll(nestedScrollConnection)
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            if (positionY <= 88.dp) {
                Box(modifier = Modifier
                    .offset(y = (if (positionY <= 64.dp) (-24).dp else positionY - 88.dp))
                    .padding(end = 16.dp)
                    .background(color = SurfaceProduct, shape = CircleShape)
                    .clip(CircleShape)
                    .constrainAs(icon) {
                        top.linkTo(header.bottom)
                        end.linkTo(parent.end)
                    }
                    .debounceClickable(onClick = onPausePlayClick)) {
                    Icon(painter = painterResource(R.drawable.ic_32_play),
                         contentDescription = null,
                         tint = IconBackgroundDark,
                         modifier = Modifier.padding(8.dp))
                }
            }
        }

        STFPlaylistHeader(modifier = Modifier.align(Alignment.TopStart),
                          title = playlist.title.orEmpty(),
                          headerAlpha = headerAlpha,
                          nestedScrollConnection = nestedScrollConnection,
                          onBackClick = onBackClick)
    }
}

@Preview
@Composable
private fun PlaylistYourScreenPreview() {
    E2MP3Theme {
        PlaylistYourScaffold(owner = UsersModel(name = "emanh", avatarUrl = ""),
                             playlist = PlaylistsModel(title = "Tên playlist", subtitle = "Giới thiệu về playlist"),
                             time = "2h24",
                             songsList = fakeSongs,
                             songsRecommendList = fakeSongs.take(5),
                             onOwnerClick = {},
                             onDownloadClick = {},
                             onMoreClick = {},
                             onShuffleClick = {},
                             onPausePlayClick = {},
                             onItemClick = {},
                             onAddSongClick = {},
                             onMoreSongClick = {},
                             onBackClick = {},
                             onRefreshClick = {},
                             onEditClick = {})
    }
}