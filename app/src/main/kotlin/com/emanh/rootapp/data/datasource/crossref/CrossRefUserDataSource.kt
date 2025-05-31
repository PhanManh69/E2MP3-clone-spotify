package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity
import kotlinx.coroutines.flow.Flow

interface CrossRefUserDataSource {
    fun getUserFollwing(userFollowingEntity: UserFollowingEntity): Flow<UserFollowingEntity?>

    suspend fun deleteUserFollwing(userFollowingEntity: UserFollowingEntity)

    suspend fun insertUserFollwing(userFollowingEntity: UserFollowingEntity)
}