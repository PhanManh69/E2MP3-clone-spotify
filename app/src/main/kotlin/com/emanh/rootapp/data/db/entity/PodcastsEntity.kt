package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "podcasts")
data class PodcastsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "subtitle") val subtitle: String? = null,
    @ColumnInfo(name = "owner_id") val ownerId: Int? = null,
    @ColumnInfo(name = "release_date") val releaseDate: String? = null,
    @ColumnInfo(name = "timeline") val timeline: Long? = null,
    @ColumnInfo(name = "likes") val likesIdList: List<Long>? = null,
)