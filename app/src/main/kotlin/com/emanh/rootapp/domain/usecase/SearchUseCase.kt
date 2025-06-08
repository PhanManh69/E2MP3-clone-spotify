package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.SearchModel
import com.emanh.rootapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    fun getAllSearch(value: String): Flow<List<SearchModel>> {
        return searchRepository.getAllSearch(value)
    }

    suspend fun insertSearch(search: SearchModel): Long {
        return searchRepository.insertSearch(search)
    }
}