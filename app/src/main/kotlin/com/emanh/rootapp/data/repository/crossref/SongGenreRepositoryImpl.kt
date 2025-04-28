package com.emanh.rootapp.data.repository.crossref

import com.emanh.rootapp.data.datasource.crossref.SongGenreDataSource
import com.emanh.rootapp.domain.model.crossref.SongsWithGenresModel
import com.emanh.rootapp.domain.repository.crossref.SongGenreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongGenreRepositoryImpl @Inject constructor(
    private val songGenreDataSource: SongGenreDataSource
) : SongGenreRepository {
    override fun getAllSongsWithGenres(): Flow<List<SongsWithGenresModel>> {
        return songGenreDataSource.getAllSongsWithGenres()
    }
}