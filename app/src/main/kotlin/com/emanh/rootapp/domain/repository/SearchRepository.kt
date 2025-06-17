package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getAllSearch(): Flow<List<SearchModel>>

    fun getAllSearch(value: String): Flow<List<SearchModel>>

    suspend fun insertSearch(search: SearchModel): Long

    suspend fun insertAllSearch(searchList: List<SearchModel>)
}