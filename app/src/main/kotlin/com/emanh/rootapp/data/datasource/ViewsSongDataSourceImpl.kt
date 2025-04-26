package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.dao.ViewsSongDao
import com.emanh.rootapp.data.db.entity.ViewsSongEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ViewsSongDataSourceImpl @Inject constructor(
    private val viewsSongDao: ViewsSongDao
) : ViewsSongDataSource {
    override suspend fun findViewRecord(userId: Int, songId: Int): ViewsSongEntity? {
        return viewsSongDao.findViewRecord(userId, songId)
    }
    
    override suspend fun updateViewsSong(viewsSong: ViewsSongEntity) {
        viewsSongDao.updateViewsSong(viewsSong)
    }
    
    override suspend fun insertViewsSong(viewsSong: ViewsSongEntity) {
        viewsSongDao.insertViewsSong(viewsSong)
    }
}