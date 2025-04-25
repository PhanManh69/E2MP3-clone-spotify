package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.GenresModel
import kotlinx.coroutines.flow.Flow

interface GenresRepository {
    fun getAllGenres(): Flow<List<GenresModel>>

    fun getGenreById(genreId: Int): Flow<GenresModel>

    suspend fun insertAllGenres(genres: List<GenresModel>)
}