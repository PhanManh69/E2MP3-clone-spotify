package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.SongsEntity
import kotlinx.coroutines.flow.Flow

interface SongsDataSource {
    fun getAllSongs(): Flow<List<SongsEntity>>

    suspend fun insertAllSongs(songs: List<SongsEntity>)
}