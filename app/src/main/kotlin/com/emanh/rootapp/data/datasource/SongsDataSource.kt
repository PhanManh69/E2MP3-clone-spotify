package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.SongsEntity
import kotlinx.coroutines.flow.Flow

interface SongsDataSource {
    fun getAllSongs(): Flow<List<SongsEntity>>

    fun getRecommendedSongs(userId: Int): Flow<List<SongsEntity>>

    fun getRecentlyListenedSongs(userId: Int): Flow<List<SongsEntity>>

    fun getTrendingSongs(): Flow<List<SongsEntity>>

    suspend fun insertAllSongs(songs: List<SongsEntity>)
}