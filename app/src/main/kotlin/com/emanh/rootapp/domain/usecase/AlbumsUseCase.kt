package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlbumsUseCase @Inject constructor(
    private val albumsRepository: AlbumsRepository
) {
    fun getAllAlbums(): Flow<List<AlbumsModel>> {
        return albumsRepository.getAllAlbums()
    }
}