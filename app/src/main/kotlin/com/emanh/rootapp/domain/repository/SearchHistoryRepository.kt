package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.SearchHistoryModel
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getAllSearchHistory(): Flow<List<SearchHistoryModel>>

    fun getSearchHistory(userId: Long): Flow<List<SearchHistoryModel>>

    suspend fun deleteDuplicate(userId: Long, tableId: Long, type: String?)

    suspend fun insertSearchHistory(searchHistory: SearchHistoryModel)
}