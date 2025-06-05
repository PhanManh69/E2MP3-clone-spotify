package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.data.db.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    suspend fun saveUserInfo(user: UsersEntity)

    fun getUserInfo(): Flow<UserInfo?>

    suspend fun clearUserInfo()

    fun isUserLoggedIn(): Flow<Boolean>
}