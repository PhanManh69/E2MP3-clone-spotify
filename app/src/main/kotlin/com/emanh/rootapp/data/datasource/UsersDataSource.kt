package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

interface UsersDataSource {
    fun getAllUsers(): Flow<List<UsersEntity>>

    fun getYourFavoriteArtists(userId: Int): Flow<UsersEntity>

    fun getSimilarArtists(userId: Int): Flow<List<UsersEntity>>

    fun getOwnerPlaylist(userId: Int): Flow<UsersEntity>

    fun getOwnerAlbum(albumId: Int): Flow<List<UsersEntity>>

    fun getArtistById(userId: Int): Flow<UsersEntity>

    fun getSearchArtists(value: String): Flow<List<UsersEntity>>

    fun getArtistsBySearch(listId: List<Int>): Flow<List<UsersEntity>>

    suspend fun insertAllUsers(users: List<UsersEntity>)
}