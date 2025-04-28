package com.emanh.rootapp.domain.usecase.crossref

import com.emanh.rootapp.domain.model.crossref.SongsWithGenresModel
import com.emanh.rootapp.domain.repository.crossref.SongGenreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongGenreUseCase @Inject constructor(
    private val songGenreRepository: SongGenreRepository
) {
    fun getAllSongsWithGenres(): Flow<List<SongsWithGenresModel>> {
        return songGenreRepository.getAllSongsWithGenres()
    }
}