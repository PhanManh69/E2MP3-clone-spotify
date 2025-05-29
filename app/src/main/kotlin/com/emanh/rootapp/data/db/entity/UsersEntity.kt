package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    @ColumnInfo(name = "is_artist") val isArtist: Boolean = false,
    @ColumnInfo(name = "username") val username: String? = null,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "password") val password: String? = null,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "normalized_search_value") val normalizedSearchValue: String? = null,
    @ColumnInfo(name = "followers") val followers: Int? = null,
    @ColumnInfo(name = "following") val followingIdList: List<Int>? = null,
)