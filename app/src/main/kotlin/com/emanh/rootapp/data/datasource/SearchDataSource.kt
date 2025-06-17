package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

interface SearchDataSource {
    fun getAllSearch(): Flow<List<SearchEntity>>

    fun getAllSearch(value: String): Flow<List<SearchEntity>>

    suspend fun insertSearch(search: SearchEntity): Long

    suspend fun insertAllSearch(searchList: List<SearchEntity>)
}