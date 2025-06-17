package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.GenresModel
import com.emanh.rootapp.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {
    fun getAllGenres(): Flow<List<GenresModel>> {
        return genresRepository.getAllGenres()
    }

    fun getGenreNameByArtist(userId: Long): Flow<List<String>> {
        return genresRepository.getGenreNameByArtist(userId)
    }
}