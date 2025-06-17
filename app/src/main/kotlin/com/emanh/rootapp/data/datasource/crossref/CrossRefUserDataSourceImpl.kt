package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.dao.crossref.CrossRefUserDao
import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefUserDataSourceImpl @Inject constructor(
    private val crossRefUserDao: CrossRefUserDao
) : CrossRefUserDataSource {
    override fun getUserFollwing(userFollowingEntity: UserFollowingEntity): Flow<UserFollowingEntity?> {
        return crossRefUserDao.getUserFollwing(userFollowingEntity.userId, userFollowingEntity.artistId)
    }

    override suspend fun deleteUserFollwing(userFollowingEntity: UserFollowingEntity) {
        return crossRefUserDao.deleteUserFollwing(userFollowingEntity.userId, userFollowingEntity.artistId)
    }

    override suspend fun insertUserFollwing(userFollowingEntity: UserFollowingEntity) {
        return crossRefUserDao.insertUserFollwing(userFollowingEntity)
    }
}