package com.emanh.rootapp.presentation.ui.home

import androidx.compose.runtime.Immutable

@Immutable
data class HomeSingerData(
    val id: Int, val imageUrl: String, val nameSinger: String
)

@Immutable
data class HomeSongsDataFake(val id: Int, val imageUrl: String, val title: String, val subtitle: String, val singer: List<HomeSingerData>)

@Immutable
data class HomeAlbumsDataFake(
    val id: Int,
    val imageUrl: String,
    val nameAlbum: String,
    val ablumType: String? = null,
    val singer: HomeSingerData,
    val songs: List<HomeSongsDataFake>
)

@Immutable
data class HomePlaylistsData(
    val id: Int, val imageUrl: String, val namePlaylist: String, val creator: String, val songs: List<HomeSongsDataFake>
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

@Immutable
data class HomeGenreData(
    val id: Int, val nameId: Int
)

@Immutable
data class HomeSongsData(
    val id: Int,
    val avatarUrl: String,
    val songUrl: String,
    val title: String,
    val subtitle: String,
    val timeline: String,
    val releaseDate: String,
    val genres: List<Int>,
    val likes: List<Int>,
    val artists: List<Int>
)

@Immutable
data class HomeUsersData(
    val id: Int,
    val isArtist: Boolean,
    val username: String,
    val email: String,
    val password: String,
    val avatarUrl: String,
    val name: String,
    val followers: Int,
    val following: List<Int>,
)

@Immutable
data class HomeAlbumsData(
    val id: Int,
    val avatarUrl: String,
    val title: String,
    val subtitle: String,
    val albumType: String,
    val releaseDate: String,
    val artist: List<Int>,
    val songs: List<Int>
)

@Immutable
data class HomeViewsSongData(
    val viewsSongId: Int, val userId: Int, val songId: Int, val numberListener: Int, val dateTime: String
)

data class HomeUiState(
    val isLiked: Boolean = false,
    val quickPlaylistSongs:  List<Any> = emptyList(),
    val recommendedSongs: List<HomeSongsData> = emptyList(),
    val recentlyListenedSongs: List<HomeSongsData> = emptyList(),
    val trendingSongs: List<HomeSongsData> = emptyList(),
    val insertViewsSong: HomeViewsSongData = HomeViewsSongData(0, 0, 0, 0, "")
)