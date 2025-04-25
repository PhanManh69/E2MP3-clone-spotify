package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.AlbumsEntity
import kotlinx.coroutines.flow.Flow

interface AlbumsDataSource {
    fun getAllAlbums(): Flow<List<AlbumsEntity>>

    suspend fun insertAlbums(albums: List<AlbumsEntity>)
}