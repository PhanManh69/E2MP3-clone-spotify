package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistsDataSource {
    fun getAllPlaylists(): Flow<List<PlaylistsEntity>>

    fun getQuickPlaylist(userId: Long): Flow<List<PlaylistsEntity>>

    fun getRadioForYou(): Flow<List<PlaylistsEntity>>

    fun getSearchPlaylists(value: String): Flow<List<PlaylistsEntity>>

    fun getPlaylistsBySearch(listId: List<Long>): Flow<List<PlaylistsEntity>>

    fun getPlaylistsById(playlistId: Long): Flow<PlaylistsEntity>

    fun getPlaylistsYourByUser(userId: Long): Flow<List<PlaylistsEntity>>

    fun getPlaylistsForYouByUser(userId: Long): Flow<List<PlaylistsEntity>>

    suspend fun updatePlaylist(playlists: PlaylistsEntity)

    suspend fun insertPlaylistYour(playlists: PlaylistsEntity): Long

    suspend fun insertAllPlaylists(playlists: List<PlaylistsEntity>)
}