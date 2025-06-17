package com.emanh.rootapp.presentation.ui.yourlibrary

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.emanh.rootapp.presentation.composable.STFLoading
import com.emanh.rootapp.presentation.composable.STFMenuLibraryType
import com.emanh.rootapp.presentation.composable.SecondaryLibraryData
import com.emanh.rootapp.utils.MyConstant.YOUR
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import com.emanh.rootapp.utils.MyConstant.FOR_YOUR
import com.emanh.rootapp.utils.MyConstant.NOT_AVATAR
import com.emanh.rootapp.utils.MyConstant.sampleLibraryData

@Composable
fun YourLibraryScreen(currentUser: UserInfo, onNavigationDrawerClick: () -> Unit) {
    val viewModel = hiltViewModel<YourLibraryViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(currentUser) {
        viewModel.setCurrentUserId(currentUser.id)
    }

    if (uiState.isLoading || uiState.user == null) {
        STFLoading()
    } else {
        YourLibraryScaffold(currentUser = currentUser,
                            user = uiState.user!!,
                            primaryChips = uiState.primaryChips,
                            secondaryChips = uiState.secondaryChips,
                            currentType = uiState.currentType,
                            listLikedSongs = uiState.listLikedSongs,
                            listYourSongs = uiState.listYourSongs,
                            listAlbumYour = uiState.listAlbumsYour,
                            listPlaylistYour = uiState.listPlaylistYour,
                            listPlaylistForYou = uiState.listPlaylistForYou,
                            listFavoriteArtist = uiState.listFavoriteArtist,
                            listLikedAlbum = uiState.listLikedAlbum,
                            onNavigationDrawerClick = onNavigationDrawerClick,
                            goToSearchInput = viewModel::goToSearchInput,
                            onCloseClick = viewModel::onCloseClick,
                            onLikedSongsClick = viewModel::onLikedSongsClick,
                            onYourSongsClick = viewModel::onYourSongsClick,
                            onCreatePlaylist = viewModel::onCreatePlaylist,
                            onPrimaryChipsClick = { item, type ->
                                viewModel.onPrimaryChipsClick(item, type)
                            },
                            onSecondaryChipsClick = { item, type ->
                                viewModel.onSecondaryChipsClick(item, type)
                            },
                            onPlaylistYourClick = {
                                viewModel.onPlaylistYourClick(it)
                            },
                            onPlaylistForYouClick = {
                                viewModel.onPlaylistForYouClick(it)
                            },
                            onFavoriteArtistClick = {
                                viewModel.onFavoriteArtistClick(it)
                            },
                            onLikedAlbumClick = {
                                viewModel.onLikedAlbumClick(it)
                            })
    }
}

@Composable
fun YourLibraryScaffold(
    modifier: Modifier = Modifier,
    primaryChips: SecondaryLibraryData?,
    secondaryChips: String,
    currentType: STFMenuLibraryType,
    currentUser: UserInfo,
    user: UsersModel,
    listLikedSongs: List<SongsModel>?,
    listYourSongs: List<SongsModel>?,
    listAlbumYour: List<AlbumsModel>?,
    listPlaylistYour: List<PlaylistsModel>?,
    listPlaylistForYou: List<PlaylistsModel>?,
    listFavoriteArtist: List<UsersModel>?,
    listLikedAlbum: List<AlbumsModel>?,
    onNavigationDrawerClick: () -> Unit,
    goToSearchInput: () -> Unit,
    onLikedSongsClick: () -> Unit,
    onYourSongsClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCreatePlaylist: () -> Unit,
    onPrimaryChipsClick: (SecondaryLibraryData, STFMenuLibraryType) -> Unit = { _, _ -> },
    onSecondaryChipsClick: (String, STFMenuLibraryType) -> Unit = { _, _ -> },
    onPlaylistYourClick: (PlaylistsModel) -> Unit,
    onPlaylistForYouClick: (PlaylistsModel) -> Unit,
    onFavoriteArtistClick: (UsersModel) -> Unit,
    onLikedAlbumClick: (AlbumsModel) -> Unit,
) {
    val mergedLibraryItems = remember(primaryChips,
                                      secondaryChips,
                                      listLikedSongs,
                                      listYourSongs,
                                      listAlbumYour,
                                      listPlaylistYour,
                                      listPlaylistForYou,
                                      listFavoriteArtist) {
        buildList<LibraryItem> {
            when (primaryChips) {
                sampleLibraryData.primaryLibrary[0] -> {
                    when (secondaryChips) {
                        YOUR -> {
                            listLikedSongs?.takeIf { it.isNotEmpty() }?.let { songs ->
                                add(LibraryItem.LikedSongs(songs))
                            }

                            listYourSongs?.takeIf { it.isNotEmpty() }?.let { songs ->
                                add(LibraryItem.YourSongs(songs))
                            }

                            listAlbumYour?.forEach { album ->
                                add(LibraryItem.YourAlbums(album))
                            }

                            listPlaylistYour?.forEach { playlist ->
                                add(LibraryItem.YourPlaylist(playlist))
                            }
                        }

                        FOR_YOUR -> {
                            listPlaylistForYou?.forEach { playlist ->
                                add(LibraryItem.ForYouPlaylist(playlist))
                            }

                            listLikedAlbum?.forEach { album ->
                                add(LibraryItem.LikedAlbum(album))
                            }
                        }

                        "" -> {
                            listLikedSongs?.takeIf { it.isNotEmpty() }?.let { songs ->
                                add(LibraryItem.LikedSongs(songs))
                            }

                            listYourSongs?.takeIf { it.isNotEmpty() }?.let { songs ->
                                add(LibraryItem.YourSongs(songs))
                            }

                            listAlbumYour?.forEach { album ->
                                add(LibraryItem.YourAlbums(album))
                            }

                            listPlaylistYour?.forEach { playlist ->
                                add(LibraryItem.YourPlaylist(playlist))
                            }

                            listPlaylistForYou?.forEach { playlist ->
                                add(LibraryItem.ForYouPlaylist(playlist))
                            }

                            listLikedAlbum?.forEach { album ->
                                add(LibraryItem.LikedAlbum(album))
                            }
                        }
                    }
                }

                sampleLibraryData.primaryLibrary[1] -> {
                    listFavoriteArtist?.forEach { artist ->
                        add(LibraryItem.FavoriteArtist(artist))
                    }
                }

                null -> {
                    listLikedSongs?.takeIf { it.isNotEmpty() }?.let { songs ->
                        add(LibraryItem.LikedSongs(songs))
                    }

                    listYourSongs?.takeIf { it.isNotEmpty() }?.let { songs ->
                        add(LibraryItem.YourSongs(songs))
                    }

                    listAlbumYour?.forEach { album ->
                        add(LibraryItem.YourAlbums(album))
                    }

                    listPlaylistYour?.forEach { playlist ->
                        add(LibraryItem.YourPlaylist(playlist))
                    }

                    listPlaylistForYou?.forEach { playlist ->
                        add(LibraryItem.ForYouPlaylist(playlist))
                    }

                    listFavoriteArtist?.forEach { artist ->
                        add(LibraryItem.FavoriteArtist(artist))
                    }

                    listLikedAlbum?.forEach { album ->
                        add(LibraryItem.LikedAlbum(album))
                    }
                }
            }
        }.sortedBy { it.sortKey.lowercase() }
    }

    STFHeader(modifier = modifier,
              userName = currentUser.username,
              avatarUrl = currentUser.avatarUrl,
              secondaryChips = secondaryChips,
              primaryChips = primaryChips,
              currentType = currentType,
              type = STFHeaderType.HeaderLibrary,
              libraryList = sampleLibraryData,
              onAvatarClick = onNavigationDrawerClick,
              onSearchClick = goToSearchInput,
              onPlusClick = onCreatePlaylist,
              onCloseClick = onCloseClick,
              onPrimaryChipsClick = onPrimaryChipsClick,
              onSecondaryChipsClick = onSecondaryChipsClick,
              content = { paddingModifier ->
                  LazyColumn(modifier = paddingModifier, contentPadding = PaddingValues(bottom = PADDING_BOTTOM_BAR.dp)) {
                      items(count = mergedLibraryItems.size, key = { index ->
                          val item = mergedLibraryItems[index]
                          when (item) {
                              is LibraryItem.LikedSongs -> "song-${item.title}"
                              is LibraryItem.YourSongs -> "song-your-${item.title}"
                              is LibraryItem.YourAlbums -> "album-your-${item.title}"
                              is LibraryItem.YourPlaylist -> "playlist-${item.title}"
                              is LibraryItem.ForYouPlaylist -> "playlist-${item.title}"
                              is LibraryItem.FavoriteArtist -> "artist-${item.title}"
                              is LibraryItem.LikedAlbum -> "album-${item.title}"
                          }
                      }) { index ->
                          val item = mergedLibraryItems[index]
                          when (item) {
                              is LibraryItem.LikedSongs -> {
                                  STFItem(imageUrl = NOT_AVATAR,
                                          title = stringResource(R.string.liked_songs),
                                          label = "${stringResource(R.string.playlist)} · ${item.songs.size}",
                                          isLiked = true,
                                          type = STFItemType.Music,
                                          size = STFItemSize.Medium,
                                          onItemClick = onLikedSongsClick)
                              }

                              is LibraryItem.YourSongs -> {
                                  STFItem(imageUrl = NOT_AVATAR,
                                          title = stringResource(R.string.your_songs),
                                          label = "${stringResource(R.string.playlist)} · ${item.songs.size}",
                                          yourIconId = R.drawable.ic_24_artist,
                                          isLiked = true,
                                          type = STFItemType.Music,
                                          size = STFItemSize.Medium,
                                          onItemClick = onYourSongsClick)
                              }

                              is LibraryItem.YourAlbums -> {
                                  STFItem(imageUrl = item.album.avatarUrl ?: NOT_AVATAR,
                                          title = item.title,
                                          label = "${stringResource(R.string.album)} · ${stringResource(R.string.for_you)} ${user.name}",
                                          type = STFItemType.Music,
                                          size = STFItemSize.Medium,
                                          onItemClick = { onLikedAlbumClick(item.album) })
                              }

                              is LibraryItem.YourPlaylist -> {
                                  STFItem(imageUrl = item.playlist.avatarUrl ?: NOT_AVATAR,
                                          title = item.title,
                                          label = "${stringResource(R.string.playlist)} · ${user.name}",
                                          type = STFItemType.Music,
                                          size = STFItemSize.Medium,
                                          onItemClick = { onPlaylistYourClick(item.playlist) })
                              }

                              is LibraryItem.ForYouPlaylist -> {
                                  STFItem(imageUrl = item.playlist.avatarUrl ?: NOT_AVATAR,
                                          title = item.title,
                                          label = "${stringResource(R.string.playlist)} · ${stringResource(R.string.for_you)} ${user.name}",
                                          type = STFItemType.Music,
                                          size = STFItemSize.Medium,
                                          onItemClick = { onPlaylistForYouClick(item.playlist) })
                              }

                              is LibraryItem.FavoriteArtist -> {
                                  STFItem(imageUrl = item.artist.avatarUrl ?: NOT_AVATAR,
                                          title = item.title,
                                          label = stringResource(R.string.artist),
                                          type = STFItemType.ArtistsLibrary,
                                          size = STFItemSize.Medium,
                                          onItemClick = { onFavoriteArtistClick(item.artist) })
                              }

                              is LibraryItem.LikedAlbum -> {
                                  STFItem(imageUrl = item.album.avatarUrl ?: NOT_AVATAR,
                                          title = item.title,
                                          label = "${stringResource(R.string.album)} · ${stringResource(R.string.for_you)} ${user.name}",
                                          type = STFItemType.Music,
                                          size = STFItemSize.Medium,
                                          onItemClick = { onLikedAlbumClick(item.album) })
                              }
                          }
                      }
                  }
              })
}