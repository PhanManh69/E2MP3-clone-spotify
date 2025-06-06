package com.emanh.rootapp.presentation.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.composable.STFHeader
import com.emanh.rootapp.presentation.composable.STFHeaderType
import com.emanh.rootapp.presentation.ui.search.composable.SearchCardGenres
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR

@Composable
fun SearchScreen(currentUser: UserInfo, onNavigationDrawerClick: () -> Unit) {
    val viewModel = hiltViewModel<SearchViewModel>()

    SearchScaffold(
            currentUser = currentUser,
            onNavigationDrawerClick = onNavigationDrawerClick,
            onSearchClick = {
                viewModel.goToSearchInput()
            },
            onGenreClick = {},
    )
}

@Composable
private fun SearchScaffold(
    modifier: Modifier = Modifier,
    currentUser: UserInfo,
    onNavigationDrawerClick: () -> Unit,
    onSearchClick: () -> Unit,
    onGenreClick: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    STFHeader(modifier = modifier,
              avatarUrl = currentUser.avatarUrl,
              userName = currentUser.username,
              type = STFHeaderType.HeaderSearch,
              onSearchClick = onSearchClick,
              onAvatarClick = onNavigationDrawerClick,
              content = {
                  Column(modifier = it.verticalScroll(scrollState)) {
                      SearchCardGenres(onClick = onGenreClick)

                      Spacer(modifier = Modifier.height(PADDING_BOTTOM_BAR.dp))
                  }
              })
}