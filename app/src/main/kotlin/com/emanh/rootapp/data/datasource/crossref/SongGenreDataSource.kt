package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.domain.model.crossref.SongsWithGenresModel
import kotlinx.coroutines.flow.Flow

interface SongGenreDataSource {
    fun getAllSongsWithGenres(): Flow<List<SongsWithGenresModel>>

    suspend fun insertAllCrossRefSongGenre(songGenreEntity: List<SongGenreEntity>)
}