package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.SongsModel
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    fun getAllSongs(): Flow<List<SongsModel>>

    fun getSongByGenreId(genreId: String): Flow<List<SongsModel>>

    suspend fun insertAllSongs(songs: List<SongsModel>)
}