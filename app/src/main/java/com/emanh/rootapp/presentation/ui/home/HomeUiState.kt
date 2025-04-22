package com.emanh.rootapp.presentation.ui.home

import androidx.compose.runtime.Immutable

@Immutable
data class HomeSingerData(
    val id: Int, val imageUrl: String, val nameSinger: String
)

@Immutable
data class HomeSongsData(val id: Int, val imageUrl: String, val title: String, val subtitle: String, val singer: List<HomeSingerData>)

@Immutable
data class HomeAlbumsData(
    val id: Int,
    val imageUrl: String,
    val nameAlbum: String,
    val ablumType: String? = null,
    val singer: HomeSingerData,
    val songs: List<HomeSongsData>
)

@Immutable
data class HomePlaylistsData(
    val id: Int, val imageUrl: String, val namePlaylist: String, val creator: String, val songs: List<HomeSongsData>
)

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
    val isLiked: Boolean = false
)