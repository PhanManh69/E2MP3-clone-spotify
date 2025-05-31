package com.emanh.rootapp.data.repository.crossref

import com.emanh.rootapp.data.datasource.crossref.CrossRefUserDataSource
import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity
import com.emanh.rootapp.domain.repository.crossref.CrossRefUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefUserRepositoryImpl @Inject constructor(
    private val crossRefUserDataSource: CrossRefUserDataSource
) : CrossRefUserRepository {
    override fun getUserFollwing(userFollowingEntity: UserFollowingEntity): Flow<UserFollowingEntity?> {
        return crossRefUserDataSource.getUserFollwing(userFollowingEntity)
    }

    override suspend fun deleteUserFollwing(userFollowingEntity: UserFollowingEntity) {
        return crossRefUserDataSource.deleteUserFollwing(userFollowingEntity)
    }

    override suspend fun insertUserFollwing(userFollowingEntity: UserFollowingEntity) {
        return crossRefUserDataSource.insertUserFollwing(userFollowingEntity)
    }
}