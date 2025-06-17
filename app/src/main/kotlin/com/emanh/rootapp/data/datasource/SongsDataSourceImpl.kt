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

    override fun getRecommendedSongs(userId: Long): Flow<List<SongsEntity>> {
        return songsDao.getRecommendedSongs(userId)
    }

    override fun getRecentlyListenedSongs(userId: Long): Flow<List<SongsEntity>> {
        return songsDao.getRecentlyListenedSongs(userId)
    }

    override fun getTrendingSongs(): Flow<List<SongsEntity>> {
        return songsDao.getTrendingSongs()
    }

    override fun getSimilarSongs(): Flow<List<SongsEntity>> {
        return songsDao.getSimilarSongs()
    }

    override fun getMoreByArtists(songId: Long): Flow<List<SongsEntity>> {
        return songsDao.getMoreByArtists(songId)
    }

    override fun getSongsByArtist(userId: Long): Flow<List<SongsEntity>> {
        return songsDao.getSongsByArtist(userId)
    }

    override fun getSongsById(songId: Long): Flow<SongsEntity> {
        return songsDao.getSongsById(songId)
    }

    override fun getSearchSong(value: String): Flow<List<SongsEntity>> {
        return songsDao.getSearchSong(value)
    }

    override fun getSongsBySearch(listId: List<Long>): Flow<List<SongsEntity>> {
        return songsDao.getSongsBySearch(listId)
    }

    override fun getLikedSongsByUser(userId: Long): Flow<List<SongsEntity>> {
        return songsDao.getLikedSongsByUser(userId)
    }

    override fun getSongsRecommend(): Flow<SongsEntity> {
        return songsDao.getSongsRecommend()
    }

    override fun getRandomSongExcluding(excludeIds: List<Long>): Flow<SongsEntity> {
        return songsDao.getRandomSongExcluding(excludeIds)
    }

    override suspend fun insertSong(song: SongsEntity): Long {
        return songsDao.insertSong(song)
    }

    override suspend fun insertAllSongs(songs: List<SongsEntity>) {
        songsDao.insertAllSongs(songs)
    }
}