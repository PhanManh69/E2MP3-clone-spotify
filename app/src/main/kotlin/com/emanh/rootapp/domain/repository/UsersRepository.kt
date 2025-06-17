package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.UsersModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getAllUsers(): Flow<List<UsersModel>>

    fun getYourFavoriteArtists(userId: Long): Flow<UsersModel>

    fun getSimilarArtists(userId: Long): Flow<List<UsersModel>>

    fun getOwnerPlaylist(userId: Long): Flow<UsersModel>

    fun getOwnerAlbum(albumId: Long): Flow<List<UsersModel>>

    fun getArtistById(userId: Long): Flow<UsersModel>

    fun getSearchArtists(value: String): Flow<List<UsersModel>>

    fun getArtistsBySearch(listId: List<Long>): Flow<List<UsersModel>>

    fun getFoveriteArtistsByUser(userId: Long): Flow<List<UsersModel>>

    fun getOwnerPlaylistYour(playlistId: Long): Flow<UsersModel>

    fun getUsername(username: String): Flow<UsersModel?>

    fun getEmail(email: String): Flow<UsersModel?>

    suspend fun getGetUserLogin(account: String, password: String): UsersModel?

    suspend fun insertUser(user: UsersModel): Long

    suspend fun insertAllUsers(users: List<UsersModel>)
}