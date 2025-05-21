package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.UsersModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getAllUsers(): Flow<List<UsersModel>>

    fun getYourFavoriteArtists(userId: Int): Flow<UsersModel>

    fun getSimilarArtists(userId: Int): Flow<List<UsersModel>>

    fun getOwnerPlaylist(userId: Int): Flow<UsersModel>

    fun getOwnerAlbum(albumId: Int): Flow<List<UsersModel>>

    fun getArtistById(userId: Int): Flow<UsersModel>

    suspend fun insertAllUsers(users: List<UsersModel>)
}