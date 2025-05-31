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

    override fun getTrendingSongs(): Flow<List<SongsEntity>> {
        return songsDao.getTrendingSongs()
    }

    override fun getSimilarSongs(): Flow<List<SongsEntity>> {
        return songsDao.getSimilarSongs()
    }

    override fun getMoreByArtists(songId: Int): Flow<List<SongsEntity>> {
        return songsDao.getMoreByArtists(songId)
    }

    override fun getSongsByArtist(userId: Int): Flow<List<SongsEntity>> {
        return songsDao.getSongsByArtist(userId)
    }

    override fun getSongsById(songId: Int): Flow<SongsEntity> {
        return songsDao.getSongsById(songId)
    }

    override fun getSearchSong(value: String): Flow<List<SongsEntity>> {
        return songsDao.getSearchSong(value)
    }

    override fun getSongsBySearch(listId: List<Int>): Flow<List<SongsEntity>> {
        return songsDao.getSongsBySearch(listId)
    }

    override fun getLikedSongsByUser(userId: Int): Flow<List<SongsEntity>> {
        return songsDao.getLikedSongsByUser(userId)
    }

    override suspend fun insertAllSongs(songs: List<SongsEntity>) {
        songsDao.insertAllSongs(songs)
    }
}