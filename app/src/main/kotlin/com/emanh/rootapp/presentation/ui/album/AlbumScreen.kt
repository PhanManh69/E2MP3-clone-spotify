package com.emanh.rootapp.presentation.ui.album

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.composable.STFPlaylistAvatar
import com.emanh.rootapp.presentation.composable.STFPlaylistHeader
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundDark
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceSecondaryInvert
import com.emanh.rootapp.presentation.ui.album.composable.AlbumButton
import com.emanh.rootapp.presentation.ui.album.composable.AlbumInfomation
import com.emanh.rootapp.utils.MyConstant.NOT_AVATAR
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import com.emanh.rootapp.utils.MyConstant.fakeSongs
import com.emanh.rootapp.utils.faunchedEffectAvatar

@Composable
fun AlbumScreen(currentUser: UserInfo, onItemClick: (Long, String) -> Unit) {
    val albumViewModel = hiltViewModel<AlbumViewModel>()
    val uiState by albumViewModel.uiState.collectAsState()

    LaunchedEffect(currentUser) {
        albumViewModel.setCurrentUserId(currentUser.id)
    }

    if (uiState.isLoading || uiState.album == null) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(SurfacePrimary)) {
            CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    color = SurfaceProduct,
                    trackColor = SurfaceSecondaryInvert,
            )
        }
    } else {
        AlbumScaffold(time = albumViewModel.totalTime(uiState.songList),
                      views = uiState.views ?: 0,
                      isAddAlbum = uiState.isAddAlbum,
                      album = uiState.album!!,
                      songsList = uiState.songList,
                      artistList = uiState.artistList,
                      onOwnerClick = {
                          albumViewModel.goToArtist(it)
                      },
                      onAddClick = {
                          albumViewModel.onAddClick(currentUser.id)
                      },
                      onDownloadClick = {},
                      onMoreClick = {},
                      onShuffleClick = {},
                      onPausePlayClick = {},
                      onItemClick = {
                          onItemClick(it, uiState.album!!.title.orEmpty())
                      },
                      onIconClick = {},
                      onArtistClick = {
                          albumViewModel.goToArtist(it)
                      },
                      onBackClick = albumViewModel::onBackClick)
    }
}

@Composable
private fun AlbumScaffold(
    modifier: Modifier = Modifier,
    time: String,
    views: Long,
    isAddAlbum: Boolean,
    album: AlbumsModel,
    songsList: List<SongsModel>,
    artistList: List<UsersModel>,
    onOwnerClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onMoreClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onPausePlayClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onIconClick: (Long) -> Unit,
    onArtistClick: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalDensity.current
    val maxImageSize = LocalConfiguration.current.screenWidthDp.dp - 96.dp
    val minImageSize = 72.dp
    val backgroundColor = faunchedEffectAvatar(album.avatarUrl)
    val avatarUrl = remember(songsList) {
        if (songsList.isNotEmpty()) {
            songsList.random().avatarUrl ?: NOT_AVATAR
        } else {
            NOT_AVATAR
        }
    }

    var position by remember { mutableStateOf(Offset.Zero) }
    var positionY by remember { mutableStateOf(0.dp) }
    var currentImageSize by remember { mutableStateOf(maxImageSize) }
    var imageScale by remember { mutableFloatStateOf(1f) }
    var scrollDirection by remember { mutableIntStateOf(0) }

    val calculatedAlpha = (currentImageSize - minImageSize) / (maxImageSize - minImageSize)
    val imageAlpha by animateFloatAsState(targetValue = (calculatedAlpha + 0.3f).coerceIn(0f, 1f), animationSpec = tween(durationMillis = 300))
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

                val newImageSize = currentImageSize + delta.dp
                val previousImageSize = currentImageSize
                currentImageSize = newImageSize.coerceIn(minImageSize, maxImageSize)
                val consumed = currentImageSize - previousImageSize
                imageScale = currentImageSize / maxImageSize

                return Offset(0f, consumed.value)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.nestedScroll(nestedScrollConnection),
                   contentPadding = PaddingValues(top = 88.dp, bottom = PADDING_BOTTOM_BAR.dp)) {
            item {
                Box {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(currentImageSize + 120.dp)
                        .offset(y = (-88).dp)
                        .background(brush = Brush.verticalGradient(0f to backgroundColor, 1f to Color.Transparent)))

                    Column {
                        STFPlaylistAvatar(imageUrl = album.avatarUrl ?: NOT_AVATAR,
                                          currentImageSize = currentImageSize,
                                          imageAlpha = imageAlpha,
                                          imageScale = imageScale)

                        Spacer(modifier = Modifier.height(16.dp))

                        AlbumButton(modifier = Modifier.padding(horizontal = 16.dp),
                                    views = views,
                                    isAddAlbum = isAddAlbum,
                                    avatarSongUrl = avatarUrl,
                                    album = album,
                                    artistList = artistList,
                                    onOwnerClick = onOwnerClick,
                                    onAddClick = onAddClick,
                                    onDownloadClick = onDownloadClick,
                                    onMoreClick = onMoreClick,
                                    onShuffleClick = onShuffleClick,
                                    onPausePlayClick = onPausePlayClick,
                                    modifierPausePlay = Modifier.onGloballyPositioned { coordinates ->
                                        position = coordinates.positionInWindow()
                                        with(context) {
                                            positionY = position.y.toDp()
                                        }
                                    })
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            items(songsList) { item ->
                STFItem(imageUrl = item.avatarUrl ?: NOT_AVATAR,
                        title = item.title.orEmpty(),
                        label = item.subtitle.orEmpty(),
                        iconId = R.drawable.ic_24_bullet,
                        type = STFItemType.Music,
                        size = STFItemSize.Small,
                        onItemClick = {
                            onItemClick(item.id)
                        },
                        onIconClick = {
                            onIconClick(item.id)
                        })
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                AlbumInfomation(date = album.releaseDate.orEmpty(),
                                time = time,
                                songs = songsList.size,
                                artistList = artistList,
                                onItemClick = onArtistClick)
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
                          title = album.title.orEmpty(),
                          headerAlpha = headerAlpha,
                          nestedScrollConnection = nestedScrollConnection,
                          onBackClick = onBackClick)
    }
}

@Preview
@Composable
private fun AlbumScreenPreview() {
    E2MP3Theme {
        AlbumScaffold(views = 240203,
                      time = "2h03min",
                      isAddAlbum = true,
                      album = AlbumsModel(title = "Album Title", albumType = "EP", releaseDate = "24-02-2025"),
                      artistList = listOf(UsersModel(name = "Artist 1", avatarUrl = ""), UsersModel(name = "Artist 2", avatarUrl = "")),
                      songsList = fakeSongs,
                      onOwnerClick = {},
                      onAddClick = {},
                      onDownloadClick = {},
                      onMoreClick = {},
                      onShuffleClick = {},
                      onPausePlayClick = {},
                      onItemClick = {},
                      onIconClick = {},
                      onArtistClick = {},
                      onBackClick = {})
    }
}