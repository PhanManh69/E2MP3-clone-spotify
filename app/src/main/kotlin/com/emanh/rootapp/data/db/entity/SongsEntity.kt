package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongsEntity(
    @PrimaryKey(autoGenerate = true) val songId: Long = 0,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String? = null,
    @ColumnInfo(name = "song_url") val songUrl: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "subtitle") val subtitle: String? = null,
    @ColumnInfo(name = "normalized_search_value") val normalizedSearchValue: String? = null,
    @ColumnInfo(name = "timeline") val timeline: String? = null,
    @ColumnInfo(name = "release_date") val releaseDate: String? = null,
    @ColumnInfo(name = "genres") val genresIdList: List<Long>? = null,
    @ColumnInfo(name = "likes") val likesIdList: List<Long>? = null,
    @ColumnInfo(name = "artists") val artistsIdList: List<Long>? = null
)