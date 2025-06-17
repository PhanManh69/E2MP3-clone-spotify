package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.dao.GenresDao
import com.emanh.rootapp.data.db.entity.GenresEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenresDataSourceImpl @Inject constructor(
    private val genresDao: GenresDao
) : GenresDataSource {
    override fun getAllGenres(): Flow<List<GenresEntity>> {
        return genresDao.getAllGenres()
    }

    override fun getGenreById(genreId: Long): Flow<GenresEntity> {
        return genresDao.getGenreById(genreId)
    }

    override fun getGenreNameByArtist(userId: Long): Flow<List<String>> {
        return genresDao.getGenreNameByArtist(userId)
    }

    override suspend fun insertAllGenres(genres: List<GenresEntity>) {
        genresDao.insertAllGenres(genres)
    }
}