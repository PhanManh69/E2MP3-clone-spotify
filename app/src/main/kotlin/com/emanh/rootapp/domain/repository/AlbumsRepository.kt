package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.AlbumsModel
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    fun getAllAlbums(): Flow<List<AlbumsModel>>

    fun getQuickAlbum(): Flow<List<AlbumsModel>>

    fun getSimilarAlbums(): Flow<List<AlbumsModel>>

    suspend fun insertAllAlbums(albums: List<AlbumsModel>)
}