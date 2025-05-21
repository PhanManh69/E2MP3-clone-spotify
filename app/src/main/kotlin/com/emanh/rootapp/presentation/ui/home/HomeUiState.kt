package com.emanh.rootapp.presentation.ui.home

import androidx.compose.runtime.Immutable
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel

@Immutable
data class HomePoscastData(
    val id: Int,
    val imageUrl: String,
    val namePoscast: String,
    val ep: String,
    val owner: String,
    val date: String,
    val time: String,
    val content: String
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val isLiked: Boolean = false,
    val quickPlaylistsList: List<Any> = emptyList(),
    val yourTopMixesPlaylist: List<CrossRefPlaylistsModel> = emptyList(),
    val recommendedSongs: List<SongsModel> = emptyList(),
    val recentlyListenedSongs: List<SongsModel> = emptyList(),
    val radioForYouPlaylist: List<PlaylistsModel> = emptyList(),
    val trendingSongs: List<SongsModel> = emptyList(),
    val yourFavoriteArtists: UsersModel? = null,
    val similarContent: List<Any> = emptyList(),
    val playlistCard: List<CrossRefPlaylistsModel> = emptyList()
)