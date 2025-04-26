package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.dao.SongsDao
import com.emanh.rootapp.data.db.entity.SongsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongsDataSourceImpl @Inject constructor(
    private val songsDao: SongsDao
) : SongsDataSource {
    override fun getAllSongs(): Flow<List<SongsEntity>> {
        return songsDao.getAllSongs()
    }

    override fun getRecommendedSongs(userId: Int): Flow<List<SongsEntity>> {
        return songsDao.getRecommendedSongs(userId)
    }

    override fun getRecentlyListenedSongs(userId: Int): Flow<List<SongsEntity>> {
        return songsDao.getRecentlyListenedSongs(userId)
    }

    override suspend fun insertAllSongs(songs: List<SongsEntity>) {
        songsDao.insertAllSongs(songs)
    }
}