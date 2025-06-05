package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.repository.UserSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSessionUseCase @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) {
    suspend fun saveUserSession(user: UsersModel) {
        userSessionRepository.saveUserSession(user)
    }

    fun getCurrentUser(): Flow< UserInfo?> {
        return userSessionRepository.getCurrentUser()
    }

    fun isLoggedIn(): Flow<Boolean> {
        return userSessionRepository.isLoggedIn()
    }

    suspend fun logout() {
        userSessionRepository.logout()
    }
}