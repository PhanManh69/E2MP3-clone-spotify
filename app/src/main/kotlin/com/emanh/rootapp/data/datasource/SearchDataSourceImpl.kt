package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.dao.SearchDao
import com.emanh.rootapp.data.db.entity.SearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchDao: SearchDao
) : SearchDataSource {
    override fun getAllSearch(): Flow<List<SearchEntity>> {
        return searchDao.getAllSearch()
    }

    override fun getAllSearch(value: String): Flow<List<SearchEntity>> {
        return searchDao.getAllSearch(value)
    }

    override suspend fun insertSearch(search: SearchEntity): Long {
        return searchDao.insertSearch(search)
    }

    override suspend fun insertAllSearch(searchList: List<SearchEntity>) {
        return searchDao.insertAllSearch(searchList)
    }
}