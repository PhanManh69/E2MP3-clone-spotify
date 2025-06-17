package com.emanh.rootapp.domain.repository.crossref

import com.emanh.rootapp.data.db.entity.crossref.AlbumLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefAlbumRepository {
    fun getAllCrossRefAlbums(): Flow<List<CrossRefAlbumsModel>>

    fun getAlbumDetailsById(albumId: Long): Flow<CrossRefAlbumsModel>

    fun getAlbumLike(albumLikeEntity: AlbumLikeEntity): Flow<AlbumLikeEntity?>

    suspend fun deleteAlbumLike(albumLikeEntity: AlbumLikeEntity)

    suspend fun insertAlbumLike(albumLikeEntity: AlbumLikeEntity)
}