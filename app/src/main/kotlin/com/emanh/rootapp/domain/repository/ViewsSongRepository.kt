package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.ViewsSongModel
import kotlinx.coroutines.flow.Flow

interface ViewsSongRepository {
    fun getTotalListenerAlbum(albumId: Long): Flow<Long>

    fun getListenerMonth(userId: Long): Flow<Long>

    suspend fun findViewRecord(userId: Long, songId: Long): ViewsSongModel?
    
    suspend fun updateViewsSong(viewsSong: ViewsSongModel)
    
    suspend fun insertViewsSong(viewsSong: ViewsSongModel)
}