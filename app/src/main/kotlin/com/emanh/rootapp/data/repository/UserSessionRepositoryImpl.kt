package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.UserDataStore
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.repository.UserSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSessionRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore,
) : UserSessionRepository {
    override suspend fun saveUserSession(user: UsersModel) {
        userDataStore.saveUserInfo(mapToEntity(user))
    }

    override fun getCurrentUser(): Flow<UserInfo?> {
        return userDataStore.getUserInfo()
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return userDataStore.isUserLoggedIn()
    }

    override suspend fun logout() {
        userDataStore.clearUserInfo()
    }

    private fun mapToEntity(model: UsersModel): UsersEntity {
        return UsersEntity(userId = model.id,
                           isArtist = model.isArtist,
                           username = model.username,
                           email = model.email,
                           password = model.password,
                           avatarUrl = model.avatarUrl,
                           name = model.name,
                           normalizedSearchValue = model.normalizedSearchValue,
                           followers = model.followers,
                           followingIdList = model.following)
    }
}