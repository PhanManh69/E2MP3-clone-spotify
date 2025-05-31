package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.AlbumsEntity
import kotlinx.coroutines.flow.Flow

interface AlbumsDataSource {
    fun getAllAlbums(): Flow<List<AlbumsEntity>>

    fun getQuickAlbum(): Flow<List<AlbumsEntity>>

    fun getSimilarAlbums(): Flow<List<AlbumsEntity>>

    fun getSearchAlbums(value: String): Flow<List<AlbumsEntity>>

    fun getAlbumsBySearch(listId: List<Int>): Flow<List<AlbumsEntity>>

    fun getAlbumsById(albumId: Int): Flow<AlbumsEntity>

    fun getAlbumLikeByUser(userId: Int): Flow<List<AlbumsEntity>>

    suspend fun insertAlbums(albums: List<AlbumsEntity>)
}