package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlbumsUseCase @Inject constructor(
    private val albumsRepository: AlbumsRepository
) {
    fun getQuickAlbum(): Flow<List<AlbumsModel>> {
        return albumsRepository.getQuickAlbum()
    }

    fun getSimilarAlbums(): Flow<List<AlbumsModel>> {
        return albumsRepository.getSimilarAlbums()
    }

    fun getSearchAlbums(value: String): Flow<List<AlbumsModel>> {
        return albumsRepository.getSearchAlbums(value)
    }

    fun getAlbumsBySearch(listId: List<Long>): Flow<List<AlbumsModel>> {
        return albumsRepository.getAlbumsBySearch(listId)
    }

    fun getAlbumsById(albumId: Long): Flow<AlbumsModel> {
        return albumsRepository.getAlbumsById(albumId)
    }

    fun getAlbumLikeByUser(userId: Long): Flow<List<AlbumsModel>> {
        return albumsRepository.getAlbumLikeByUser(userId)
    }

    fun getAlbumByArtist(userId: Long): Flow<List<AlbumsModel>> {
        return albumsRepository.getAlbumByArtist(userId)
    }
}