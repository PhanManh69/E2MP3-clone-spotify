package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.SongsModel
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    fun getAllSongs(): Flow<List<SongsModel>>

    fun getRecommendedSongs(userId: Int): Flow<List<SongsModel>>

    fun getRecentlyListenedSongs(userId: Int): Flow<List<SongsModel>>

    fun getTrendingSongs(): Flow<List<SongsModel>>

    fun getSimilarSongs(): Flow<List<SongsModel>>

    fun getMoreByArtists(songId: Int): Flow<List<SongsModel>>

    fun getSongsByArtist(userId: Int): Flow<List<SongsModel>>

    fun getSongsById(songId: Int): Flow<SongsModel>

    fun getSearchSong(value: String): Flow<List<SongsModel>>

    fun getSongsBySearch(listId: List<Int>): Flow<List<SongsModel>>

    fun getLikedSongsByUser(userId: Int): Flow<List<SongsModel>>

    suspend fun insertAllSongs(songs: List<SongsModel>)
}