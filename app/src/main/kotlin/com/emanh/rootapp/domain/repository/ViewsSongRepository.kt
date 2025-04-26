package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.ViewsSongModel

interface ViewsSongRepository {
    suspend fun findViewRecord(userId: Int, songId: Int): ViewsSongModel?
    
    suspend fun updateViewsSong(viewsSong: ViewsSongModel)
    
    suspend fun insertViewsSong(viewsSong: ViewsSongModel)
}