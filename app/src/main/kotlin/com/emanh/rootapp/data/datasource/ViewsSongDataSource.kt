package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.ViewsSongEntity
import kotlinx.coroutines.flow.Flow

interface ViewsSongDataSource {
    fun getTotalListenerAlbum(albumId: Long): Flow<Long>

    fun getListenerMonth(userId: Long): Flow<Long>

    suspend fun findViewRecord(userId: Long, songId: Long): ViewsSongEntity?
    
    suspend fun updateViewsSong(viewsSong: ViewsSongEntity)
    
    suspend fun insertViewsSong(viewsSong: ViewsSongEntity)
}