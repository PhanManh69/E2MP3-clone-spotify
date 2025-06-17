package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.UsersModel
import kotlinx.coroutines.flow.Flow

interface UserSessionRepository {
    suspend fun saveUserSession(user: UsersModel)

    fun getCurrentUser(): Flow<UserInfo?>

    fun isLoggedIn(): Flow<Boolean>

    suspend fun logout()
}