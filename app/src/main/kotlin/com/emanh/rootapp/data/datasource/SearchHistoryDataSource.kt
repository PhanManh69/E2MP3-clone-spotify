package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface SearchHistoryDataSource {
    fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>>

    fun getSearchHistory(userId: Int): Flow<List<SearchHistoryEntity>>

    suspend fun deleteDuplicate(userId: Int, tableId: Int, type: String?)

    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity)
}