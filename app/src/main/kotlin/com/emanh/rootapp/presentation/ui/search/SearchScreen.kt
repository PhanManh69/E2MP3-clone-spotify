package com.emanh.rootapp.presentation.ui.search

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.rootapp.presentation.composable.STFHeader
import com.emanh.rootapp.presentation.composable.STFHeaderType
import com.emanh.rootapp.presentation.ui.search.composable.SearchCardGenres
import com.emanh.rootapp.presentation.ui.search.composable.SearchVideoShort
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import kotlinx.coroutines.delay

@Composable
fun SearchScreen() {
    val viewModel = hiltViewModel<SearchViewModel>()

    SearchScaffold(onSearchClick = {
        viewModel.goToSearchInput()
    }, onGenreClick = {})
}

@Composable
private fun SearchScaffold(modifier: Modifier = Modifier, onSearchClick: () -> Unit, onGenreClick: (Int) -> Unit) {
    var visibleItemCount by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    val displayItems: List<@Composable () -> Unit> = remember {
        listOf({ SearchVideoShort() }, { SearchCardGenres(onClick = onGenreClick) })
    }

    LaunchedEffect(displayItems) {
        while (visibleItemCount < displayItems.size) {
            delay(100L)
            visibleItemCount++
        }
    }

    STFHeader(modifier = modifier, userName = "emanh", type = STFHeaderType.HeaderSearch, onSearchClick = onSearchClick, content = {
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