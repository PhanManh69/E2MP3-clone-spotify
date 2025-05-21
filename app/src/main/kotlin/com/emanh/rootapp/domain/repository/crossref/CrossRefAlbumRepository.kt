package com.emanh.rootapp.domain.repository.crossref

import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefAlbumRepository {
    fun getAllCrossRefAlbums(): Flow<List<CrossRefAlbumsModel>>

    fun getAlbumDetailsById(albumId: Int): Flow<CrossRefAlbumsModel>
}