package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.SearchDataSource
import com.emanh.rootapp.data.db.entity.SearchEntity
import com.emanh.rootapp.domain.model.SearchModel
import com.emanh.rootapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepository {
    override fun getAllSearch(): Flow<List<SearchModel>> {
        return searchDataSource.getAllSearch().map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getAllSearch(value: String): Flow<List<SearchModel>> {
        return searchDataSource.getAllSearch(value).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override suspend fun insertAllSearch(searchList: List<SearchModel>) {
        searchDataSource.insertAllSearch(searchList.map { mapToEntity(it) })
    }

    private fun mapToModel(entity: SearchEntity): SearchModel {
        return SearchModel(id = entity.idSearch,
                           idTable = entity.idTable,
                           isTable = entity.isTable,
                           normalizedSearchValue = entity.normalizedSearchValue)
    }

    private fun mapToEntity(model: SearchModel): SearchEntity {
        return SearchEntity(idSearch = model.id,
                            idTable = model.idTable,
                            isTable = model.isTable,
                            normalizedSearchValue = model.normalizedSearchValue)
    }
}