package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.UsersModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getAllUsers(): Flow<List<UsersModel>>

    suspend fun insertAllUsers(users: List<UsersModel>)
}