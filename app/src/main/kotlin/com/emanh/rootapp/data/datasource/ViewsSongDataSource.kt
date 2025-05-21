package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.ViewsSongEntity
import kotlinx.coroutines.flow.Flow

interface ViewsSongDataSource {
    fun getTotalListenerAlbum(albumId: Int): Flow<Int>

    fun getListenerMonth(userId: Int): Flow<Int>

    suspend fun findViewRecord(userId: Int, songId: Int): ViewsSongEntity?
    
    suspend fun updateViewsSong(viewsSong: ViewsSongEntity)
    
    suspend fun insertViewsSong(viewsSong: ViewsSongEntity)
}