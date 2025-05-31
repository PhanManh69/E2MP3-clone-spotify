package com.emanh.rootapp.domain.usecase.crossref

import com.emanh.rootapp.data.db.entity.crossref.AlbumLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import com.emanh.rootapp.domain.repository.crossref.CrossRefAlbumRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefAlbumUseCase @Inject constructor(
    private val crossRefAlbumRepository: CrossRefAlbumRepository
) {
    fun getAlbumDetailsById(albumId: Int): Flow<CrossRefAlbumsModel> {
        return crossRefAlbumRepository.getAlbumDetailsById(albumId)
    }

    fun getAlbumLike(albumLikeEntity: AlbumLikeEntity): Flow<AlbumLikeEntity?> {
        return crossRefAlbumRepository.getAlbumLike(albumLikeEntity)
    }

    suspend fun deleteAlbumLike(albumLikeEntity: AlbumLikeEntity) {
        return crossRefAlbumRepository.deleteAlbumLike(albumLikeEntity)
    }

    suspend fun insertAlbumLike(albumLikeEntity: AlbumLikeEntity) {
        return crossRefAlbumRepository.insertAlbumLike(albumLikeEntity)
    }
}