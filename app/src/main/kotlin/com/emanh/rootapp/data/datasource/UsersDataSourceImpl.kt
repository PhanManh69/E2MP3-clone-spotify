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

    override fun getYourFavoriteArtists(userId: Int): Flow<UsersEntity> {
        return usersDao.getYourFavoriteArtists(userId)
    }

    override fun getSimilarArtists(userId: Int): Flow<List<UsersEntity>> {
        return usersDao.getSimilarArtists(userId)
    }

    override fun getOwnerPlaylist(userId: Int): Flow<UsersEntity> {
        return usersDao.getOwnerPlaylist(userId)
    }

    override fun getOwnerAlbum(albumId: Int): Flow<List<UsersEntity>> {
        return usersDao.getOwnerAlbum(albumId)
    }

    override fun getArtistById(userId: Int): Flow<UsersEntity> {
        return usersDao.getArtistById(userId)
    }

    override suspend fun insertAllUsers(users: List<UsersEntity>) {
        return usersDao.insertAllUsers(users)
    }
}