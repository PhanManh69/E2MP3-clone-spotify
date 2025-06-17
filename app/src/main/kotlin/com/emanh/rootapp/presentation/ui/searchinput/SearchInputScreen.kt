package com.emanh.rootapp.presentation.ui.searchinput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFHeader
import com.emanh.rootapp.presentation.composable.STFHeaderType
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.Title3Bold
import com.emanh.rootapp.presentation.ui.searchinput.composable.SearchListItem
import com.emanh.rootapp.utils.MyConstant.ALBUMS_SEARCH
import com.emanh.rootapp.utils.MyConstant.ARTISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.NOT_AVATAR
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import com.emanh.rootapp.utils.MyConstant.PLAYLISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.SONGS_SEARCH
import com.emanh.rootapp.utils.MyConstant.chipSearchInputList
import kotlinx.coroutines.delay

@Composable
fun SearchInputScreen(currentUser: UserInfo, onItemClick: (Long, String) -> Unit) {
    val viewModel = hiltViewModel<SearchInputViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(currentUser) {
        viewModel.setCurrentUserId(currentUser.id)
    }

    SearchInputScaffold(currentMessage = uiState.currentMessage,
                        searchList = uiState.searchList,
                        searchHistoryList = uiState.searchHistoryList,
                        onBackClick = viewModel::goToBack,
                        onMessageChange = { viewModel.updateMessage(it) },
                        onCloseClick = { id, type ->
                            viewModel.onRemovedSearchHistory(id, type, currentUser.id)
                        },
                        onItemClick = { id, type, title ->
                            when (type) {
                                SONGS_SEARCH -> {
                                    onItemClick(id, title)
                                }

                                else -> {
                                    viewModel.onIconClick(id, type)
                                }
                            }

                            viewModel.insertSearchHistory(id, type, currentUser.id)
                        },
                        onIconClick = { id, type ->
                            viewModel.onIconClick(id, type)
                        },
                        onChipsSearchInputClick = { viewModel.onChipsSearchInputClick(it) })

    Spacer(modifier = Modifier.height(PADDING_BOTTOM_BAR.dp))
}

@Composable
private fun SearchInputScaffold(
    modifier: Modifier = Modifier,
    currentMessage: String,
    searchList: List<Any>,
    searchHistoryList: List<Any>,
    onBackClick: () -> Unit,
    onMessageChange: (String) -> Unit,
    onChipsSearchInputClick: (Int) -> Unit,
    onCloseClick: (Long, String) -> Unit,
    onIconClick: (Long, String) -> Unit,
    onItemClick: (Long, String, String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val isKeyboardOpen = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

    STFHeader(inputText = currentMessage,
              searchChipsList = if (searchList.isNotEmpty()) chipSearchInputList else null,
              type = STFHeaderType.HeaderSearchInput,
              onInputTextChange = onMessageChange,
              onBackClick = onBackClick,
              onChipsSearchInputClick = onChipsSearchInputClick,
              focusRequester = focusRequester,
              modifier = modifier.debounceClickable(indication = null) { focusManager.clearFocus() },
              content = {
                  LazyColumn(modifier = it
                      .fillMaxSize()
                      .imePadding()
                      .debounceClickable(indication = null) { focusManager.clearFocus() }) {
                      if (searchHistoryList.isNotEmpty()) {
                          items(count = searchHistoryList.size, key = { index ->
                              val item = searchHistoryList[index]
                              when (item) {
                                  is SongsModel -> "song-${item.id}"
                                  is UsersModel -> "user-${item.id}"
                                  is AlbumsModel -> "album-${item.id}"
                                  is PlaylistsModel -> "playlist-${item.id}"
                                  else -> "unknown-${item.hashCode()}"
                              }
                          }) { index ->
                              val item = searchHistoryList[index]

                              val id = when (item) {
                                  is SongsModel -> item.id
                                  is UsersModel -> item.id
                                  is AlbumsModel -> item.id
                                  is PlaylistsModel -> item.id
                                  else -> -1
                              }

                              val type = when (item) {
                                  is SongsModel -> SONGS_SEARCH
                                  is UsersModel -> ARTISTS_SEARCH
                                  is AlbumsModel -> ALBUMS_SEARCH
                                  is PlaylistsModel -> PLAYLISTS_SEARCH
                                  else -> ""
                              }

                              val avatarUrl = when (item) {
                                  is SongsModel -> item.avatarUrl
                                  is UsersModel -> item.avatarUrl
                                  is AlbumsModel -> item.avatarUrl
                                  is PlaylistsModel -> item.avatarUrl
                                  else -> ""
                              }

                              val title = when (item) {
                                  is SongsModel -> item.title
                                  is UsersModel -> item.name
                                  is AlbumsModel -> item.title
                                  is PlaylistsModel -> item.title
                                  else -> ""
                              }

                              val label = when (item) {
                                  is SongsModel -> item.subtitle
                                  is UsersModel -> item.name
                                  is AlbumsModel -> item.subtitle
                                  is PlaylistsModel -> item.subtitle
                                  else -> ""
                              }

                              val typeItem = when (item) {
                                  is UsersModel -> STFItemType.Artists
                                  else -> STFItemType.Music
                              }

                              STFItem(imageUrl = avatarUrl ?: NOT_AVATAR,
                                      title = title.orEmpty(),
                                      label = label.orEmpty(),
                                      type = typeItem,
                                      size = STFItemSize.Small,
                                      onItemClick = { onItemClick(id, type, title.orEmpty()) },
                                      onIconClick = { onCloseClick(id, type) })
                          }

                          if (!isKeyboardOpen) {
                              item {
                                  Spacer(modifier = Modifier.height(PADDING_BOTTOM_BAR.dp))
                              }
                          }
                      } else {
                          item {
                              Column(modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(horizontal = 16.dp),
                                     horizontalAlignment = Alignment.CenterHorizontally) {
                                  Spacer(modifier = Modifier.height(164.dp))

                                  Text(text = stringResource(R.string.search_what_you_love), color = TextPrimary, style = Title3Bold)

                                  Spacer(modifier = Modifier.height(8.dp))

                                  Text(text = stringResource(R.string.search_for_all), color = TextPrimary, style = Body6Regular)
                              }
                          }
                      }
                  }

                  if (!searchList.isEmpty()) {
                      SearchListItem(modifier = it, searchList = searchList, onItemClick = onItemClick, onIconClick = onIconClick)
                  }
              })
}