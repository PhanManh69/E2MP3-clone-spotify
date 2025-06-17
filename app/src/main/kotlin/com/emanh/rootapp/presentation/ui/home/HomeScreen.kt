package com.emanh.rootapp.presentation.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import com.emanh.rootapp.presentation.composable.STFHeader
import com.emanh.rootapp.presentation.composable.STFHeaderType
import com.emanh.rootapp.presentation.composable.STFLoading
import com.emanh.rootapp.presentation.ui.home.composable.HomeCardPlaylist
import com.emanh.rootapp.presentation.ui.home.composable.HomePodcats
import com.emanh.rootapp.presentation.ui.home.composable.HomeQuickPlaylist
import com.emanh.rootapp.presentation.ui.home.composable.HomeRadioForYou
import com.emanh.rootapp.presentation.ui.home.composable.HomeRecentlyListened
import com.emanh.rootapp.presentation.ui.home.composable.HomeRecommended
import com.emanh.rootapp.presentation.ui.home.composable.HomeSimilarContent
import com.emanh.rootapp.presentation.ui.home.composable.HomeTrendingSong
import com.emanh.rootapp.presentation.ui.home.composable.HomeVideoShort
import com.emanh.rootapp.presentation.ui.home.composable.HomeYourTopMixes
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import kotlinx.coroutines.delay
import kotlin.Any

@Composable
fun HomeScreen(currentUser: UserInfo, onNavigationDrawerClick: () -> Unit) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val uiState by homeViewModel.uiState.collectAsState()
    val isLiked = uiState.isLiked
    val initialRecommendedSongs = remember {
        mutableStateOf<List<SongsModel>>(emptyList())
    }

    if (initialRecommendedSongs.value.isEmpty() && uiState.recommendedSongs.isNotEmpty()) {
        initialRecommendedSongs.value = uiState.recommendedSongs
    }

    LaunchedEffect(currentUser) {
        homeViewModel.setCurrentUserId(currentUser.id)
    }

    if (uiState.isLoading || uiState.yourFavoriteArtists == null) {
        STFLoading()
    } else {
        HomeScaffold(isLikedImage = isLiked,
                     isLikedPodcast = isLiked,
                     currentUser = currentUser,
                     quickPlaylistList = uiState.quickPlaylistsList,
                     yourTopMixesList = uiState.yourTopMixesPlaylist,
                     recentlyListenedSongs = uiState.recentlyListenedSongs,
                     recommendedSongs = initialRecommendedSongs.value,
                     trendingSongs = uiState.trendingSongs,
                     radioForYouPlaylist = uiState.radioForYouPlaylist,
                     yourFavoriteArtists = uiState.yourFavoriteArtists!!,
                     similarContent = uiState.similarContent,
                     playlistCard = uiState.playlistCard,
                     onNavigationDrawerClick = onNavigationDrawerClick,
                     onViewAll = homeViewModel::onViewAllHistory,
                     onPlayRecommendedAll = {},
                     onPlayTrendingAll = {},
                     onQuickPlayClick = { id, type ->
                         homeViewModel.onQuickPlayClick(id, type)
                     },
                     onTopMixesClick = { homeViewModel.goToPlaylist(it) },
                     onRecentylClick = { homeViewModel.goToSingle(it) },
                     onRecommendedlClick = { homeViewModel.goToSingle(it) },
                     onTrendingClick = { homeViewModel.goToSingle(it) },
                     onAvatarClick = {},
                     onSimilarClick = { id, type ->
                         homeViewModel.onSimilarClick(id, type)
                     },
                     onRadioClick = { homeViewModel.goToPlaylist(it) },
                     onCardPlaylistClick = { homeViewModel.goToPlaylist(it) })
    }
}

@Composable
private fun HomeScaffold(
    modifier: Modifier = Modifier,
    isLikedImage: Boolean = false,
    isLikedPodcast: Boolean = false,
    currentUser: UserInfo,
    quickPlaylistList: List<Any>,
    yourTopMixesList: List<CrossRefPlaylistsModel>,
    recentlyListenedSongs: List<SongsModel>,
    recommendedSongs: List<SongsModel>,
    trendingSongs: List<SongsModel>,
    radioForYouPlaylist: List<PlaylistsModel>,
    yourFavoriteArtists: UsersModel,
    similarContent: List<Any>,
    playlistCard: List<CrossRefPlaylistsModel>,
    onNavigationDrawerClick: () -> Unit,
    onViewAll: () -> Unit,
    onPlayRecommendedAll: () -> Unit,
    onPlayTrendingAll: () -> Unit,
    onQuickPlayClick: (Long, String) -> Unit,
    onTopMixesClick: (Long) -> Unit,
    onRecentylClick: (Long) -> Unit,
    onRecommendedlClick: (Long) -> Unit,
    onTrendingClick: (Long) -> Unit,
    onAvatarClick: (Long) -> Unit,
    onSimilarClick: (Long, String) -> Unit,
    onRadioClick: (Long) -> Unit,
    onCardPlaylistClick: (Long) -> Unit
) {
    var selectedChip by remember { mutableIntStateOf(0) }
    var visibleItemCount by remember { mutableIntStateOf(0) }

    val scrollState = rememberScrollState()

    val quickPlaylistState = remember(quickPlaylistList) {
        @Composable {
            HomeQuickPlaylist(quickPlaylistList = quickPlaylistList, onCardClick = onQuickPlayClick)
        }
    }

    val yourTopMixesState = remember(yourTopMixesList) {
        @Composable {
            HomeYourTopMixes(yourTopMixesList = yourTopMixesList, onThumbClick = onTopMixesClick)
        }
    }

    val recentlyListenedState = remember(recentlyListenedSongs) {
        @Composable {
            HomeRecentlyListened(recentlyLestenedList = recentlyListenedSongs, onThumbClick = onRecentylClick, onViewAll = onViewAll)
        }
    }

    val recommendedState = remember(recommendedSongs) {
        @Composable {
            HomeRecommended(recommendedList = recommendedSongs, onThumbClick = onRecommendedlClick, onPlayAll = onPlayRecommendedAll)
        }
    }

    val trendingState = remember(trendingSongs) {
        @Composable {
            HomeTrendingSong(trendingList = trendingSongs, onThumbClick = onTrendingClick, onPlayAll = onPlayTrendingAll)
        }
    }

    val radioForYouState = remember(radioForYouPlaylist) {
        @Composable {
            HomeRadioForYou(radioForYouPlaylist = radioForYouPlaylist, onThumbClick = onRadioClick)
        }
    }

    val similarContentState = remember(yourFavoriteArtists, similarContent) {
        @Composable {
            HomeSimilarContent(yourFavoriteArtists = yourFavoriteArtists,
                               similarContent = similarContent,
                               onThumbClick = onSimilarClick,
                               onAvatarClick = onAvatarClick)
        }
    }

    val commonSections = remember(quickPlaylistState,
                                  yourTopMixesState,
                                  recentlyListenedState,
                                  recommendedState,
                                  trendingState,
                                  radioForYouState,
                                  similarContentState) {
        listOf(quickPlaylistState, yourTopMixesState, recentlyListenedState, recommendedState, trendingState, radioForYouState, similarContentState)
    }

    val podcastSections: List<@Composable () -> Unit> = remember {
        listOf({ HomeVideoShort() }, { HomePodcats(isLiked = isLikedPodcast) })
    }

    val displayItems by remember(selectedChip, commonSections, playlistCard) {
        derivedStateOf {
            when (selectedChip) {
                0 -> commonSections + listOf({ HomeCardPlaylist(playlistCard = playlistCard, isLiked = isLikedImage, onClick = onCardPlaylistClick) },
                                             { HomePodcats(isLiked = isLikedPodcast, maxItems = 2) })

                1 -> commonSections + listOf {
                    HomeCardPlaylist(isLiked = isLikedImage, maxItems = playlistCard.size, playlistCard = playlistCard, onClick = onCardPlaylistClick)
                }

                2 -> podcastSections
                else -> emptyList()
            }
        }
    }

    LaunchedEffect(displayItems.size, selectedChip) {
        visibleItemCount = 0
        while (visibleItemCount < displayItems.size) {
            delay(100L)
            visibleItemCount++
        }
    }

    STFHeader(modifier = modifier,
              avatarUrl = currentUser.avatarUrl,
              userName = currentUser.username,
              type = STFHeaderType.HeaderHome,
              onChipsHomeClick = { selectedChip = it },
              onAvatarClick = onNavigationDrawerClick,
              content = {
                  Column(modifier = it.verticalScroll(scrollState)) {
                      repeat(minOf(visibleItemCount, displayItems.size)) { index ->
                          AnimatedVisibility(visible = true, enter = fadeIn(animationSpec = tween(durationMillis = 300))) {
                              displayItems[index].invoke()
                          }
                      }

                      Spacer(modifier = Modifier.height(PADDING_BOTTOM_BAR.dp))
                  }
              })
}