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

    HomeScaffold(isLiked = uiState.isLiked,
                 onCardClick = {},
                 onThumbTopMixesClick = {},
                 onThumbRecentylClick = {},
                 onThumbRecommendedlClick = {},
                 onAvatarClick = {},
                 onThumbSimilarClick = {},
                 onThumbRadioClick = {},
                 onViewAll = {},
                 onPlayAll = {})
}

@Composable
private fun HomeScaffold(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onCardClick: (Int) -> Unit,
    onThumbTopMixesClick: (Int) -> Unit,
    onThumbRecentylClick: (Int) -> Unit,
    onThumbRecommendedlClick: (Int) -> Unit,
    onAvatarClick: (Int) -> Unit,
    onThumbSimilarClick: (Int) -> Unit,
    onThumbRadioClick: (Int) -> Unit,
    onViewAll: () -> Unit,
    onPlayAll: () -> Unit
) {
    var selectedChip by remember { mutableIntStateOf(0) }
    var visibleItemCount by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    val commonSections: List<@Composable () -> Unit> = remember {
        listOf({ HomeQuickPlaylist(onCardClick = onCardClick) },
               { HomeYourTopMixes(onThumbClick = onThumbTopMixesClick) },
               { HomeRecentlyListened(onThumbClick = onThumbRecentylClick, onViewAll = onViewAll) },
               { HomeRecommended(onThumbClick = onThumbRecommendedlClick, onPlayAll = onPlayAll) },
               { HomeTrendingSong(onThumbClick = onThumbRecommendedlClick, onPlayAll = onPlayAll) },
               { HomeRadioForYou(onThumbClick = onThumbRadioClick) },
               { HomeSimilarContent(onThumbClick = onThumbSimilarClick, onAvatarClick = onAvatarClick) })
    }

    val podcastSections: List<@Composable () -> Unit> = remember {
        listOf({ HomeVideoShort() }, { HomePodcats(isLiked = isLiked) })
    }

    val displayItems by remember(selectedChip) {
        derivedStateOf {
            when (selectedChip) {
                0 -> commonSections + listOf({ HomeCardImage(maxItems = 2) }, { HomePodcats(isLiked = isLiked, maxItems = 2) })
                1 -> commonSections + listOf { HomeCardImage() }
                2 -> podcastSections
                else -> emptyList()
            }
        }
    }

    LaunchedEffect(displayItems) {
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