package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    fun getYourFavoriteArtists(userId: Int): Flow<UsersModel> {
        return usersRepository.getYourFavoriteArtists(userId)
    }

    fun getSimilarArtists(userId: Int): Flow<List<UsersModel>> {
        return usersRepository.getSimilarArtists(userId)
    }

    fun getOwnerPlaylist(userId: Int): Flow<UsersModel> {
        return usersRepository.getOwnerPlaylist(userId)
    }

    fun getOwnerAlbum(albumId: Int): Flow<List<UsersModel>> {
        return usersRepository.getOwnerAlbum(albumId)
    }

    fun getArtistById(userId: Int): Flow<UsersModel> {
        return usersRepository.getArtistById(userId)
    }

    fun getSearchArtists(value: String): Flow<List<UsersModel>> {
        return usersRepository.getSearchArtists(value)
    }

    fun getArtistsBySearch(listId: List<Int>): Flow<List<UsersModel>> {
        return usersRepository.getArtistsBySearch(listId)
    }

    fun getFoveriteArtistsByUser(userId: Int): Flow<List<UsersModel>> {
        return usersRepository.getFoveriteArtistsByUser(userId)
    }
}