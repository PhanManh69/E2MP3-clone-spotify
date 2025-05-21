package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {
    fun getGenreNameByArtist(userId: Int): Flow<List<Int>> {
        return genresRepository.getGenreNameByArtist(userId)
    }
}