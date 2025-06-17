package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.SearchHistoryModel
import com.emanh.rootapp.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchHistoryUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) {
    fun getSearchHistory(userId: Long): Flow<List<SearchHistoryModel>> {
        return searchHistoryRepository.getSearchHistory(userId)
    }

    suspend fun deleteDuplicate(userId: Long, tableId: Long, type: String?) {
        searchHistoryRepository.deleteDuplicate(userId, tableId, type)
    }

    suspend fun insertSearchHistory(searchHistory: SearchHistoryModel) {
        searchHistoryRepository.insertSearchHistory(searchHistory)
    }
}