package com.emanh.rootapp.presentation.ui.artist

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.composable.STFLoading
import com.emanh.rootapp.presentation.theme.Body2Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextBackgroundDark
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.Title1Bold
import com.emanh.rootapp.presentation.ui.artist.composable.ArtistAbout
import com.emanh.rootapp.presentation.ui.artist.composable.ArtistAvatar
import com.emanh.rootapp.presentation.ui.artist.composable.ArtistHeader
import com.emanh.rootapp.presentation.ui.artist.composable.ArtistInfo
import com.emanh.rootapp.utils.MyConstant.NOT_AVATAR
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import com.emanh.rootapp.utils.MyConstant.fakeSongs
import com.emanh.rootapp.utils.faunchedEffectAvatar

@Composable
fun ArtistScreen(currentUser: UserInfo, onItemClick: (Long, String) -> Unit) {
    val artistViewModel = hiltViewModel<ArtistViewMode>()
    val uiState by artistViewModel.uiState.collectAsState()

    LaunchedEffect(currentUser) {
        artistViewModel.setCurrentUserId(currentUser.id)
    }

    if (uiState.isLoading || uiState.artist == null) {
        STFLoading()
    } else {
        ArtistScaffold(
                genre = artistViewModel.getGenreName(uiState.genreNameList),
                viewMonth = uiState.viewsMonth ?: 0,
                isFollowing = uiState.isFollowing,
                artist = uiState.artist!!,
                songsList = uiState.songsList,
                onFollowClick = {
                    artistViewModel.onFollowClick(currentUser.id)
                },
                onMoreClick = {},
                onShuffleClick = {},
                onPausePlayClick = {},
                onItemClick = {
                    onItemClick(it, uiState.artist!!.name.orEmpty())
                },
                onIconClick = {},
                onBackClick = artistViewModel::onBackClick,
        )
    }
}

@Composable
private fun ArtistScaffold(
    modifier: Modifier = Modifier,
    genre: String,
    viewMonth: Long,
    isFollowing: Boolean,
    artist: UsersModel,
    songsList: List<SongsModel>,
    onFollowClick: () -> Unit,
    onMoreClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onPausePlayClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onIconClick: (Long) -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalDensity.current
    val backgroundColor = faunchedEffectAvatar(artist.avatarUrl)
    var positionY by remember { mutableStateOf(0.dp) }
    var scrollDirection by remember { mutableIntStateOf(0) }
    var position by remember { mutableStateOf(Offset.Zero) }

    val headerAlpha by animateFloatAsState(targetValue = if ((scrollDirection == 1 || scrollDirection == -1) && positionY <= 138.dp) 1f else 0f,
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
        ArtistAvatar(modifier = Modifier.align(Alignment.TopCenter), avatarUrl = artist.avatarUrl)

        LazyColumn(modifier = Modifier.nestedScroll(nestedScrollConnection), contentPadding = PaddingValues(bottom = PADDING_BOTTOM_BAR.dp)) {
            item {
                Box(modifier
                        .padding(top = 256.dp)
                        .background(TextBackgroundDark)) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .background(brush = Brush.verticalGradient(0f to backgroundColor, 1f to Color.Transparent)))

                    Box(modifier = Modifier.height(120.dp)) {
                        Text(text = artist.name.orEmpty(),
                             color = TextPrimary,
                             style = Title1Bold,
                             maxLines = 2,
                             overflow = TextOverflow.Ellipsis,
                             modifier = Modifier
                                 .padding(horizontal = 12.dp)
                                 .align(Alignment.BottomStart)
                                 .offset(y = (-132).dp))
                    }

                    ArtistInfo(genre = genre,
                               viewMonth = viewMonth,
                               isFollowing = isFollowing,
                               songsList = songsList,
                               onFollowClick = onFollowClick,
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

            items(songsList) { item ->
                STFItem(modifier = Modifier.background(TextBackgroundDark),
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
                            onIconClick(item.id)
                        })
            }

            item {
                Column(modifier = Modifier.background(TextBackgroundDark)) {
                    Text(text = stringResource(R.string.about),
                         color = TextPrimary,
                         style = Body2Bold,
                         modifier = Modifier
                             .padding(horizontal = 16.dp)
                             .padding(top = 24.dp, bottom = 16.dp))

                    ArtistAbout(modifier = Modifier.padding(horizontal = 16.dp), avatarUrl = artist.avatarUrl ?: NOT_AVATAR, viewMonth = viewMonth)
                }
            }
        }

        ArtistHeader(modifier = Modifier.align(Alignment.TopStart),
                     modifierIcon = Modifier.align(Alignment.TopCenter),
                     headerAlpha = headerAlpha,
                     positionY = positionY,
                     backgroundColor = backgroundColor,
                     artist = artist,
                     nestedScrollConnection = nestedScrollConnection,
                     onBackClick = onBackClick,
                     onPausePlayClick = onPausePlayClick)
    }
}

@Preview
@Composable
private fun ArtistScreenPreview() {
    E2MP3Theme {
        ArtistScaffold(genre = "From The Ritz To The Rubble • 505 • Mardy Bum • Hello You • Bigger Boys and Stolen Sweethearts • RU Mine? • No Buses • Do Me a Favour • Arabella • Only Ones Who Know My Propeller • Do I Wanna Know? • and more",
                       viewMonth = 19283943,
                       isFollowing = true,
                       artist = UsersModel(name = "emanh",
                                           avatarUrl = "https://thumbs.dreamstime.com/b/summer-travel-destination-design-summer-vacation-holiday-concept-perfect-tranquil-beach-scene-soft-sunlight-white-sand-111325841.jpg"),
                       songsList = fakeSongs,
                       onFollowClick = {},
                       onMoreClick = {},
                       onShuffleClick = {},
                       onPausePlayClick = {},
                       onItemClick = {},
                       onIconClick = {},
                       onBackClick = {})
    }
}