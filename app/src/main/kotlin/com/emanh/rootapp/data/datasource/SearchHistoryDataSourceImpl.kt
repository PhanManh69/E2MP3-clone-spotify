package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.dao.SearchHistoryDao
import com.emanh.rootapp.data.db.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchHistoryDataSourceImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) : SearchHistoryDataSource {
    override fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>> {
        return searchHistoryDao.getAllSearchHistory()
    }

    override fun getSearchHistory(userId: Int): Flow<List<SearchHistoryEntity>> {
        return searchHistoryDao.getSearchHistory(userId)
    }

    override suspend fun deleteDuplicate(userId: Int, tableId: Int, type: String?) {
        return searchHistoryDao.deleteDuplicate(userId, tableId, type)
    }

    override suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity) {
        searchHistoryDao.deleteDuplicate(userId = searchHistory.userId, tableId = searchHistory.tableId, type = searchHistory.type)
        return searchHistoryDao.insertSearchHistory(searchHistory)
    }
}