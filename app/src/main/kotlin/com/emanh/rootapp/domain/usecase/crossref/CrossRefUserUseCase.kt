package com.emanh.rootapp.domain.usecase.crossref

import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity
import com.emanh.rootapp.domain.repository.crossref.CrossRefUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefUserUseCase @Inject constructor(
    private val crossRefUserRepository: CrossRefUserRepository
) {
    fun getUserFollwing(userFollowingEntity: UserFollowingEntity): Flow<UserFollowingEntity?> {
        return crossRefUserRepository.getUserFollwing(userFollowingEntity)
    }

    suspend fun deleteUserFollwing(userFollowingEntity: UserFollowingEntity) {
        return crossRefUserRepository.deleteUserFollwing(userFollowingEntity)
    }

    suspend fun insertUserFollwing(userFollowingEntity: UserFollowingEntity) {
        return crossRefUserRepository.insertUserFollwing(userFollowingEntity)
    }
}