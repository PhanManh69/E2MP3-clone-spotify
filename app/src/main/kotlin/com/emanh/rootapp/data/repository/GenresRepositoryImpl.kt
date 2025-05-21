package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.GenresDataSource
import com.emanh.rootapp.data.db.entity.GenresEntity
import com.emanh.rootapp.domain.model.GenresModel
import com.emanh.rootapp.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val genresDataSource: GenresDataSource
) : GenresRepository {
    override fun getAllGenres(): Flow<List<GenresModel>> {
        return genresDataSource.getAllGenres().map { entities ->
            entities.map { entity ->
                GenresModel(id = entity.genreId, nameId = entity.nameId)
            }
        }
    }

    override fun getGenreById(genreId: Int): Flow<GenresModel> {
        return genresDataSource.getGenreById(genreId).map { entity ->
            GenresModel(id = entity.genreId, nameId = entity.nameId)
        }
    }

    override fun getGenreNameByArtist(userId: Int): Flow<List<Int>> {
        return genresDataSource.getGenreNameByArtist(userId)
    }

    override suspend fun insertAllGenres(genres: List<GenresModel>) {
        genresDataSource.insertAllGenres(genres.map { mapToEntity(it) })
    }

    private fun mapToEntity(model: GenresModel): GenresEntity {
        return GenresEntity(genreId = model.id, nameId = model.nameId)
    }
}