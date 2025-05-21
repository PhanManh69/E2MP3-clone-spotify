package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.ViewsSongModel
import kotlinx.coroutines.flow.Flow

interface ViewsSongRepository {
    fun getTotalListenerAlbum(albumId: Int): Flow<Int>

    fun getListenerMonth(userId: Int): Flow<Int>

    suspend fun findViewRecord(userId: Int, songId: Int): ViewsSongModel?
    
    suspend fun updateViewsSong(viewsSong: ViewsSongModel)
    
    suspend fun insertViewsSong(viewsSong: ViewsSongModel)
}