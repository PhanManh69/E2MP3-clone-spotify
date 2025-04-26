package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.dao.crossref.SongGenreDao
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.domain.model.crossref.SongsWithGenresModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongGenreDataSourceImpl @Inject constructor(
    private val crossRefSongGenreDao: SongGenreDao
) : SongGenreDataSource {
    override fun getAllSongsWithGenres(): Flow<List<SongsWithGenresModel>> {
        return crossRefSongGenreDao.getAllSongsWithGenres()
    }

    override suspend fun insertAllCrossRefSongGenre(songGenreEntity: List<SongGenreEntity>) {
        crossRefSongGenreDao.insertAllCrossRefSongGenre(songGenreEntity)
    }
}