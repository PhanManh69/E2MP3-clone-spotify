package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistsDataSource {
    fun getAllPlaylists(): Flow<List<PlaylistsEntity>>

    fun getQuickPlaylist(userId: Int): Flow<List<PlaylistsEntity>>

    fun getRadioForYou(): Flow<List<PlaylistsEntity>>

    suspend fun insertAllPlaylists(playlists: List<PlaylistsEntity>)
}