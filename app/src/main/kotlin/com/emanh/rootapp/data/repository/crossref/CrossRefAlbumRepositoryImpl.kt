package com.emanh.rootapp.data.repository.crossref

import com.emanh.rootapp.data.datasource.crossref.CrossRefAlbumDataSource
import com.emanh.rootapp.data.db.entity.crossref.AlbumLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import com.emanh.rootapp.domain.repository.crossref.CrossRefAlbumRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefAlbumRepositoryImpl @Inject constructor(
    private val crossRefAlbumDataSource: CrossRefAlbumDataSource
) : CrossRefAlbumRepository {
    override fun getAllCrossRefAlbums(): Flow<List<CrossRefAlbumsModel>> {
        return crossRefAlbumDataSource.getAllCrossRefAlbums()
    }

    override fun getAlbumDetailsById(albumId: Int): Flow<CrossRefAlbumsModel> {
        return crossRefAlbumDataSource.getAlbumDetailsById(albumId)
    }

    override fun getAlbumLike(albumLikeEntity: AlbumLikeEntity): Flow<AlbumLikeEntity?> {
        return crossRefAlbumDataSource.getAlbumLike(albumLikeEntity)
    }

    override suspend fun deleteAlbumLike(albumLikeEntity: AlbumLikeEntity) {
        return crossRefAlbumDataSource.deleteAlbumLike(albumLikeEntity)
    }

    override suspend fun insertAlbumLike(albumLikeEntity: AlbumLikeEntity) {
        return crossRefAlbumDataSource.insertAlbumLike(albumLikeEntity)
    }
}