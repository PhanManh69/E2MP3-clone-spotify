package com.emanh.rootapp.data.db.entity.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cross_ref_user_following", primaryKeys = ["userId", "artistId"])
data class UserFollowingEntity(
    @ColumnInfo(name = "userId") val userId: Long, @ColumnInfo(name = "artistId") val artistId: Long
)