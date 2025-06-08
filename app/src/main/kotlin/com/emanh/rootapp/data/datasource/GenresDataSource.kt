package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.GenresEntity
import kotlinx.coroutines.flow.Flow

interface GenresDataSource {
    fun getAllGenres(): Flow<List<GenresEntity>>

    fun getGenreById(genreId: Long): Flow<GenresEntity>

    fun getGenreNameByArtist(userId: Long): Flow<List<String>>

    suspend fun insertAllGenres(genres: List<GenresEntity>)
}