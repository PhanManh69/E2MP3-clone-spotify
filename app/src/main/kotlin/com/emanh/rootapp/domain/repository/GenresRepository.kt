package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.GenresModel
import kotlinx.coroutines.flow.Flow

interface GenresRepository {
    fun getAllGenres(): Flow<List<GenresModel>>

    fun getGenreById(genreId: Long): Flow<GenresModel>

    fun getGenreNameByArtist(userId: Long): Flow<List<String>>

    suspend fun insertAllGenres(genres: List<GenresModel>)
}