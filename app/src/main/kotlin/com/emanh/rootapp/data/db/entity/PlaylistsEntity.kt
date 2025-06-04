package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistsEntity(
    @PrimaryKey(autoGenerate = true) val playlistId: Long = 0,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "normalized_search_value") val normalizedSearchValue: String? = null,
    @ColumnInfo(name = "subtitle") val subtitle: String? = null,
    @ColumnInfo(name = "isRadio") val isRadio: Boolean = false,
    @ColumnInfo(name = "owner_id") val ownerId: Long? = null,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "songs") val songsIdList: List<Long>
)