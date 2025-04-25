package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

interface UsersDataSource {
    fun getAllUsers(): Flow<List<UsersEntity>>

    suspend fun insertAllUsers(users: List<UsersEntity>)
}