package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.SearchHistoryModel
import com.emanh.rootapp.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchHistoryUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) {
    fun getSearchHistory(userId: Int): Flow<List<SearchHistoryModel>> {
        return searchHistoryRepository.getSearchHistory(userId)
    }

    suspend fun deleteDuplicate(userId: Int, tableId: Int, type: String?) {
        searchHistoryRepository.deleteDuplicate(userId, tableId, type)
    }

    suspend fun insertSearchHistory(searchHistory: SearchHistoryModel) {
        searchHistoryRepository.insertSearchHistory(searchHistory)
    }
}