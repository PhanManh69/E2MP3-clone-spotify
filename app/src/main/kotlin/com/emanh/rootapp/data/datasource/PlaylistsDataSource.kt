package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistsDataSource {
    fun getAllPlaylists(): Flow<List<PlaylistsEntity>>

    fun getQuickPlaylist(userId: Int): Flow<List<PlaylistsEntity>>

    fun getRadioForYou(): Flow<List<PlaylistsEntity>>

    fun getSearchPlaylists(value: String): Flow<List<PlaylistsEntity>>

    fun getPlaylistsBySearch(listId: List<Int>): Flow<List<PlaylistsEntity>>

    fun getPlaylistsById(playlistId: Int): Flow<PlaylistsEntity>

    fun getPlaylistsYourByUser(userId: Int): Flow<List<PlaylistsEntity>>

    fun getPlaylistsForYouByUser(userId: Int): Flow<List<PlaylistsEntity>>

    suspend fun insertAllPlaylists(playlists: List<PlaylistsEntity>)
}