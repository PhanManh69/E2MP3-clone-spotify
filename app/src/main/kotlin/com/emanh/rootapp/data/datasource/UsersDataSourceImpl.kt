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

    override suspend fun insertAllUsers(users: List<UsersEntity>) {
        return usersDao.insertAllUsers(users)
    }
}