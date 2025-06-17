package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.entity.crossref.PlaylistLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefPlaylistDataSource {
    fun getAllCrossRefPlaylists(): Flow<List<CrossRefPlaylistsModel>>

    fun getYourTopMixes(): Flow<List<CrossRefPlaylistsModel>>

    fun getPlaylistCard(): Flow<List<CrossRefPlaylistsModel>>

    fun getPlaylistDetailsById(playlistId: Long): Flow<CrossRefPlaylistsModel>

    fun getPlaylistLike(playlistLikeEntity: PlaylistLikeEntity): Flow<PlaylistLikeEntity?>

    suspend fun deletePlaylistLike(playlistLikeEntity: PlaylistLikeEntity)

    suspend fun deletePlaylistSong(playlistSongEntity: PlaylistSongEntity)

    suspend fun insertPlaylistLike(playlistLikeEntity: PlaylistLikeEntity)

    suspend fun insertSongToPlaylist(playlistSongEntity: PlaylistSongEntity)

    suspend fun insertAllCrossRefPlaylistSong(playlistSongEntity: List<PlaylistSongEntity>)
}