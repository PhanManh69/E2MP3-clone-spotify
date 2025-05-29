package com.emanh.rootapp.data.db.fakedata

import com.emanh.rootapp.data.db.entity.AlbumsEntity
import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import com.emanh.rootapp.data.db.entity.SearchEntity
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.utils.MyConstant.ALBUMS_SEARCH
import com.emanh.rootapp.utils.MyConstant.ARTISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.PLAYLISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.SONGS_SEARCH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun createSearchData(
    songsEntities: Flow<List<SongsEntity>>,
    artistsEntities: Flow<List<UsersEntity>>,
    albumsEntities: Flow<List<AlbumsEntity>>,
    playlistsEntities: Flow<List<PlaylistsEntity>>
): Flow<List<SearchEntity>> {
    return combine(songsEntities, artistsEntities, albumsEntities, playlistsEntities) { songs, artists, albums, playlists ->
        val searchList = mutableListOf<SearchEntity>()

        searchList.addAll(songs.map { song ->
            SearchEntity(
                    idTable = song.songId,
                    isTable = SONGS_SEARCH,
                    normalizedSearchValue = song.normalizedSearchValue,
            )
        })

        searchList.addAll(artists.filter { it.isArtist == true }.map { artist ->
            SearchEntity(
                    idTable = artist.userId,
                    isTable = ARTISTS_SEARCH,
                    normalizedSearchValue = artist.normalizedSearchValue,
            )
        })

        searchList.addAll(albums.map { album ->
            SearchEntity(
                    idTable = album.albumId,
                    isTable = ALBUMS_SEARCH,
                    normalizedSearchValue = album.normalizedSearchValue,
            )
        })

        searchList.addAll(playlists.map { playlist ->
            SearchEntity(
                    idTable = playlist.playlistId,
                    isTable = PLAYLISTS_SEARCH,
                    normalizedSearchValue = playlist.normalizedSearchValue,
            )
        })

        searchList.toList()
    }
}

fun SearchEntity.updateWithEntities(
    songsEntity: SongsEntity? = null, usersEntity: UsersEntity? = null, albumsEntity: AlbumsEntity? = null, playlistsEntity: PlaylistsEntity? = null
): SearchEntity {
    return when {
        songsEntity != null -> this.copy(
                idTable = songsEntity.songId,
                isTable = "songs",
                normalizedSearchValue = songsEntity.normalizedSearchValue,
        )

        usersEntity != null -> this.copy(
                idTable = usersEntity.userId,
                isTable = "users",
                normalizedSearchValue = usersEntity.normalizedSearchValue,
        )

        albumsEntity != null -> this.copy(
                idTable = albumsEntity.albumId,
                isTable = "albums",
                normalizedSearchValue = albumsEntity.normalizedSearchValue,
        )

        playlistsEntity != null -> this.copy(
                idTable = playlistsEntity.playlistId,
                isTable = "playlists",
                normalizedSearchValue = playlistsEntity.normalizedSearchValue,
        )

        else -> this
    }
}