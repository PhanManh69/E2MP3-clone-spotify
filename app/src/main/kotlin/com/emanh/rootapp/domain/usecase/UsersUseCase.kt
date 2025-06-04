package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    fun getYourFavoriteArtists(userId: Long): Flow<UsersModel> {
        return usersRepository.getYourFavoriteArtists(userId)
    }

    fun getSimilarArtists(userId: Long): Flow<List<UsersModel>> {
        return usersRepository.getSimilarArtists(userId)
    }

    fun getOwnerPlaylist(userId: Long): Flow<UsersModel> {
        return usersRepository.getOwnerPlaylist(userId)
    }

    fun getOwnerAlbum(albumId: Long): Flow<List<UsersModel>> {
        return usersRepository.getOwnerAlbum(albumId)
    }

    fun getArtistById(userId: Long): Flow<UsersModel> {
        return usersRepository.getArtistById(userId)
    }

    fun getSearchArtists(value: String): Flow<List<UsersModel>> {
        return usersRepository.getSearchArtists(value)
    }

    fun getArtistsBySearch(listId: List<Long>): Flow<List<UsersModel>> {
        return usersRepository.getArtistsBySearch(listId)
    }

    fun getFoveriteArtistsByUser(userId: Long): Flow<List<UsersModel>> {
        return usersRepository.getFoveriteArtistsByUser(userId)
    }

    fun getOwnerPlaylistYour(playlistId: Long): Flow<UsersModel> {
        return usersRepository.getOwnerPlaylistYour(playlistId)
    }
}