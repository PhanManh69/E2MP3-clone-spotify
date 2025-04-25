package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String? = null,
    @ColumnInfo(name = "song_url") val songUrl: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "subtitle") val subtitle: String? = null,
    @ColumnInfo(name = "timeline") val timeline: String? = null,
    @ColumnInfo(name = "release_date") val releaseDate: String? = null,
    @ColumnInfo(name = "genres") val genres: List<Int>? = null,
    @ColumnInfo(name = "likes") val likes: List<Int>? = null,
    @ColumnInfo(name = "artists") val artists: List<Int>? = null
)