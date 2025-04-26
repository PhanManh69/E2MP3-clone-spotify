package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.ViewsSongEntity

interface ViewsSongDataSource {
    suspend fun findViewRecord(userId: Int, songId: Int): ViewsSongEntity?
    
    suspend fun updateViewsSong(viewsSong: ViewsSongEntity)
    
    suspend fun insertViewsSong(viewsSong: ViewsSongEntity)
}