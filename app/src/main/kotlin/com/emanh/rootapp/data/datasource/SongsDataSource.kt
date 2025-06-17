package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.SongsEntity
import kotlinx.coroutines.flow.Flow

interface SongsDataSource {
    fun getAllSongs(): Flow<List<SongsEntity>>

    fun getRecommendedSongs(userId: Long): Flow<List<SongsEntity>>

    fun getRecentlyListenedSongs(userId: Long): Flow<List<SongsEntity>>

    fun getTrendingSongs(): Flow<List<SongsEntity>>

    fun getSimilarSongs(): Flow<List<SongsEntity>>

    fun getMoreByArtists(songId: Long): Flow<List<SongsEntity>>

    fun getSongsByArtist(userId: Long): Flow<List<SongsEntity>>

    fun getSongsById(songId: Long): Flow<SongsEntity>

    fun getSearchSong(value: String): Flow<List<SongsEntity>>

    fun getSongsBySearch(listId: List<Long>): Flow<List<SongsEntity>>

    fun getLikedSongsByUser(userId: Long): Flow<List<SongsEntity>>

    fun getSongsRecommend(): Flow<SongsEntity>

    fun getRandomSongExcluding(excludeIds: List<Long>): Flow<SongsEntity>

    suspend fun insertSong(song: SongsEntity): Long

    suspend fun insertAllSongs(songs: List<SongsEntity>)
}