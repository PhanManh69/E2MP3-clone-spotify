package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.SongsModel
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    fun getAllSongs(): Flow<List<SongsModel>>

    fun getRecommendedSongs(userId: Long): Flow<List<SongsModel>>

    fun getRecentlyListenedSongs(userId: Long): Flow<List<SongsModel>>

    fun getTrendingSongs(): Flow<List<SongsModel>>

    fun getSimilarSongs(): Flow<List<SongsModel>>

    fun getMoreByArtists(songId: Long): Flow<List<SongsModel>>

    fun getSongsByArtist(userId: Long): Flow<List<SongsModel>>

    fun getSongsById(songId: Long): Flow<SongsModel>

    fun getSearchSong(value: String): Flow<List<SongsModel>>

    fun getSongsBySearch(listId: List<Long>): Flow<List<SongsModel>>

    fun getLikedSongsByUser(userId: Long): Flow<List<SongsModel>>

    fun getSongsRecommend(): Flow<SongsModel>

    fun getRandomSongExcluding(excludeIds: List<Long>): Flow<SongsModel>

    fun getProcessingSongs(userId: Long): Flow<List<SongsModel>>

    suspend fun insertSong(song: SongsModel): Long
}