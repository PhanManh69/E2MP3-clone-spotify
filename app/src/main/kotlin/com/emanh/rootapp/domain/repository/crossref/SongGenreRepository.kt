package com.emanh.rootapp.domain.repository.crossref

import com.emanh.rootapp.domain.model.crossref.SongsWithGenresModel
import kotlinx.coroutines.flow.Flow

interface SongGenreRepository {
    fun getAllSongsWithGenres(): Flow<List<SongsWithGenresModel>>
}