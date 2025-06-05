package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.dao.UsersDao
import com.emanh.rootapp.data.db.entity.UsersEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersDataSourceImpl @Inject constructor(
    private val usersDao: UsersDao
) : UsersDataSource {
    override fun getAllUsers(): Flow<List<UsersEntity>> {
        return usersDao.getAllUsers()
    }

    override fun getYourFavoriteArtists(userId: Long): Flow<UsersEntity> {
        return usersDao.getYourFavoriteArtists(userId)
    }

    override fun getSimilarArtists(userId: Long): Flow<List<UsersEntity>> {
        return usersDao.getSimilarArtists(userId)
    }

    override fun getOwnerPlaylist(userId: Long): Flow<UsersEntity> {
        return usersDao.getOwnerPlaylist(userId)
    }

    override fun getOwnerAlbum(albumId: Long): Flow<List<UsersEntity>> {
        return usersDao.getOwnerAlbum(albumId)
    }

    override fun getArtistById(userId: Long): Flow<UsersEntity> {
        return usersDao.getArtistById(userId)
    }

    override fun getSearchArtists(value: String): Flow<List<UsersEntity>> {
        return usersDao.getSearchArtists(value)
    }

    override fun getArtistsBySearch(listId: List<Long>): Flow<List<UsersEntity>> {
        return usersDao.getArtistsBySearch(listId)
    }

    override fun getFoveriteArtistsByUser(userId: Long): Flow<List<UsersEntity>> {
        return usersDao.getFoveriteArtistsByUser(userId)
    }

    override fun getOwnerPlaylistYour(playlistId: Long): Flow<UsersEntity> {
        return usersDao.getOwnerPlaylistYour(playlistId)
    }

    override suspend fun getGetUserLogin(account: String, password: String): UsersEntity? {
        return usersDao.getGetUserLogin(account, password)
    }

    override suspend fun insertAllUsers(users: List<UsersEntity>) {
        return usersDao.insertAllUsers(users)
    }
}