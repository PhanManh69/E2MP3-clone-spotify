package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.GenresEntity
import kotlinx.coroutines.flow.Flow

interface GenresDataSource {
    fun getAllGenres(): Flow<List<GenresEntity>>

    fun getGenreById(genreId: Int): Flow<GenresEntity>

    fun getGenreNameByArtist(userId: Int): Flow<List<Int>>

    suspend fun insertAllGenres(genres: List<GenresEntity>)
}