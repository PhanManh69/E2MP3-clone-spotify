package com.emanh.rootapp.data.db.dao.crossref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity
import com.emanh.rootapp.utils.MyQuery.QUERY_DELETE_USER_FOLLOWING
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_USER_FOLLWING
import kotlinx.coroutines.flow.Flow

@Dao
interface CrossRefUserDao {
    @Transaction
    @Query(QUERY_GET_USER_FOLLWING)
    fun getUserFollwing(userId: Long, artistId: Long): Flow<UserFollowingEntity?>

    @Query(QUERY_DELETE_USER_FOLLOWING)
    suspend fun deleteUserFollwing(userId: Long, artistId: Long)

    @Insert()
    suspend fun insertUserFollwing(userFollowingEntity: UserFollowingEntity)
}