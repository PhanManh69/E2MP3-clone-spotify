package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface SearchHistoryDataSource {
    fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>>

    fun getSearchHistory(userId: Long): Flow<List<SearchHistoryEntity>>

    suspend fun deleteDuplicate(userId: Long, tableId: Long, type: String?)

    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity)
}