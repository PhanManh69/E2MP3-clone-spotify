package com.emanh.rootapp.domain.usecase.crossref

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
}