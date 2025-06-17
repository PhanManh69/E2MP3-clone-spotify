package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.SearchHistoryDataSource
import com.emanh.rootapp.data.db.entity.SearchHistoryEntity
import com.emanh.rootapp.domain.model.SearchHistoryModel
import com.emanh.rootapp.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDataSource: SearchHistoryDataSource
) : SearchHistoryRepository {
    override fun getAllSearchHistory(): Flow<List<SearchHistoryModel>> {
        return searchHistoryDataSource.getAllSearchHistory().map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getSearchHistory(userId: Long): Flow<List<SearchHistoryModel>> {
        return searchHistoryDataSource.getSearchHistory(userId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override suspend fun deleteDuplicate(userId: Long, tableId: Long, type: String?) {
        return searchHistoryDataSource.deleteDuplicate(userId, tableId, type)
    }

    override suspend fun insertSearchHistory(searchHistory: SearchHistoryModel) {
        return searchHistoryDataSource.insertSearchHistory(mapToEntity(searchHistory))
    }

    private fun mapToModel(entity: SearchHistoryEntity): SearchHistoryModel {
        return SearchHistoryModel(id = entity.searchHistoryId, userId = entity.userId, tableId = entity.tableId, type = entity.type)
    }

    private fun mapToEntity(model: SearchHistoryModel): SearchHistoryEntity {
        return SearchHistoryEntity(searchHistoryId = model.id, userId = model.userId, tableId = model.tableId, type = model.type)
    }
}