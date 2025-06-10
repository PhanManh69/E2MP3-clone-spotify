package com.emanh.rootapp.presentation.ui.yourlibrary

import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFMenuLibraryType
import com.emanh.rootapp.presentation.composable.SecondaryLibraryData

sealed class LibraryItem {
    abstract val title: String
    abstract val sortKey: String

    data class LikedSongs(
        val songs: List<SongsModel>, override val title: String = "Bài hát đã thích"
    ) : LibraryItem() {
        override val sortKey: String = title
    }

    data class YourSongs(
        val songs: List<SongsModel>, override val title: String = "Bài hát của bạn"
    ) : LibraryItem() {
        override val sortKey: String = title
    }

    data class YourAlbums(
        val album: AlbumsModel, override val title: String = album.title.orEmpty()
    ) : LibraryItem() {
        override val sortKey: String = title
    }

    data class YourPlaylist(
        val playlist: PlaylistsModel, override val title: String = playlist.title.orEmpty()
    ) : LibraryItem() {
        override val sortKey: String = title
    }

    data class ForYouPlaylist(
        val playlist: PlaylistsModel, override val title: String = playlist.title.orEmpty()
    ) : LibraryItem() {
        override val sortKey: String = title
    }

    data class FavoriteArtist(
        val artist: UsersModel, override val title: String = artist.name.orEmpty()
    ) : LibraryItem() {
        override val sortKey: String = title
    }

    data class LikedAlbum(
        val album: AlbumsModel, override val title: String = album.title.orEmpty()
    ) : LibraryItem() {
        override val sortKey: String = title
    }
}

data class YourLibraryUiState(
    val isLoading: Boolean = false,
    val primaryChips: SecondaryLibraryData? = null,
    val secondaryChips: String = "",
    val currentType: STFMenuLibraryType = STFMenuLibraryType.Default,
    val user: UsersModel? = null,
    val listLikedSongs: List<SongsModel>? = null,
    val listYourSongs: List<SongsModel>? = null,
    val listAlbumsYour: List<AlbumsModel>? = null,
    val listPlaylistYour: List<PlaylistsModel>? = null,
    val listPlaylistForYou: List<PlaylistsModel>? = null,
    val listFavoriteArtist: List<UsersModel>? = null,
    val listLikedAlbum: List<AlbumsModel>? = null,
)