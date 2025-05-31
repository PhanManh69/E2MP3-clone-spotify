package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.AlbumsModel
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    fun getAllAlbums(): Flow<List<AlbumsModel>>

    fun getQuickAlbum(): Flow<List<AlbumsModel>>

    fun getSimilarAlbums(): Flow<List<AlbumsModel>>

    fun getSearchAlbums(value: String): Flow<List<AlbumsModel>>

    fun getAlbumsBySearch(listId: List<Int>): Flow<List<AlbumsModel>>

    fun getAlbumsById(albumId: Int): Flow<AlbumsModel>

    fun getAlbumLikeByUser(userId: Int): Flow<List<AlbumsModel>>

    suspend fun insertAllAlbums(albums: List<AlbumsModel>)
}