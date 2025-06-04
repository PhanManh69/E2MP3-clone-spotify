package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

interface UsersDataSource {
    fun getAllUsers(): Flow<List<UsersEntity>>

    fun getYourFavoriteArtists(userId: Long): Flow<UsersEntity>

    fun getSimilarArtists(userId: Long): Flow<List<UsersEntity>>

    fun getOwnerPlaylist(userId: Long): Flow<UsersEntity>

    fun getOwnerAlbum(albumId: Long): Flow<List<UsersEntity>>

    fun getArtistById(userId: Long): Flow<UsersEntity>

    fun getSearchArtists(value: String): Flow<List<UsersEntity>>

    fun getArtistsBySearch(listId: List<Long>): Flow<List<UsersEntity>>

    fun getFoveriteArtistsByUser(userId: Long): Flow<List<UsersEntity>>

    fun getOwnerPlaylistYour(playlistId: Long): Flow<UsersEntity>

    suspend fun insertAllUsers(users: List<UsersEntity>)
}