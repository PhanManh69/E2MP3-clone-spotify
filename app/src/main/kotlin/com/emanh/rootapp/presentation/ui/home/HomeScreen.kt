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
import com.emanh.rootapp.presentation.composable.STFHeader
import com.emanh.rootapp.presentation.composable.STFHeaderType
import com.emanh.rootapp.presentation.ui.home.composable.HomeCardImage
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

@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val uiState by homeViewModel.uiState.collectAsState()
    val isLiked = uiState.isLiked
    val initialRecommendedSongs = remember {
        mutableStateOf<List<HomeSongsData>>(emptyList())
    }

    if (initialRecommendedSongs.value.isEmpty() && uiState.recommendedSongs.isNotEmpty()) {
        initialRecommendedSongs.value = uiState.recommendedSongs
    }

    HomeScaffold(isLikedImage = isLiked,
                 isLikedPodcast = isLiked,
                 playlistSongList = uiState.quickPlaylistSongs,
                 recentlyListenedSongs = uiState.recentlyListenedSongs,
                 recommendedSongs = initialRecommendedSongs.value,
                 trendingSongs = uiState.trendingSongs,
                 onViewAll = {},
                 onPlayRecommendedAll = {},
                 onPlayTrendingAll = {},
                 onQuickPlayClick = {},
                 onTopMixesClick = {},
                 onRecentylClick = {},
                 onRecommendedlClick = { homeViewModel.onRecommendedlClick(it) },
                 onTrendingClick = {},
                 onAvatarClick = {},
                 onSimilarClick = {},
                 onRadioClick = {})
}

@Composable
private fun HomeScaffold(
    modifier: Modifier = Modifier,
    isLikedImage: Boolean = false,
    isLikedPodcast: Boolean = false,
    playlistSongList: List<Any>,
    recentlyListenedSongs: List<HomeSongsData>,
    recommendedSongs: List<HomeSongsData>,
    trendingSongs: List<HomeSongsData>,
    onViewAll: () -> Unit,
    onPlayRecommendedAll: () -> Unit,
    onPlayTrendingAll: () -> Unit,
    onQuickPlayClick: (Int) -> Unit,
    onTopMixesClick: (Int) -> Unit,
    onRecentylClick: (Int) -> Unit,
    onRecommendedlClick: (Int) -> Unit,
    onTrendingClick: (Int) -> Unit,
    onAvatarClick: (Int) -> Unit,
    onSimilarClick: (Int) -> Unit,
    onRadioClick: (Int) -> Unit
) {
    var selectedChip by remember { mutableIntStateOf(0) }
    var visibleItemCount by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    val quickPlaylistState = remember(playlistSongList) {
        @Composable {
            HomeQuickPlaylist(quickPlaylistList = playlistSongList, onCardClick = onQuickPlayClick)
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
    val staticSections: List<@Composable () -> Unit> = remember {
        listOf({ HomeYourTopMixes(onThumbClick = onTopMixesClick) },
               { HomeRadioForYou(onThumbClick = onRadioClick) },
               { HomeSimilarContent(onThumbClick = onSimilarClick, onAvatarClick = onAvatarClick) })
    }
    val commonSections = remember(recentlyListenedState, recommendedState, trendingState) {
        listOf(quickPlaylistState, staticSections[0], recentlyListenedState, recommendedState, trendingState, staticSections[1], staticSections[2])
    }
    val podcastSections: List<@Composable () -> Unit> = remember {
        listOf({ HomeVideoShort() }, { HomePodcats(isLiked = isLikedPodcast) })
    }
    val displayItems by remember(selectedChip, commonSections) {
        derivedStateOf {
            when (selectedChip) {
                0 -> commonSections + listOf({ HomeCardImage(isLiked = isLikedImage, maxItems = 2) },
                                             { HomePodcats(isLiked = isLikedPodcast, maxItems = 2) })

                1 -> commonSections + listOf { HomeCardImage(isLiked = isLikedImage) }
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

    STFHeader(modifier = modifier, userName = "emanh", type = STFHeaderType.HeaderHome, onChipsHomeClick = { selectedChip = it }, content = {
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