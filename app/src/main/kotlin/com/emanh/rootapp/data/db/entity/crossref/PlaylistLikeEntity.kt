package com.emanh.rootapp.data.db.entity.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cross_ref_playlist_like", primaryKeys = ["playlistId", "userId"])
data class PlaylistLikeEntity(
    @ColumnInfo(name = "playlistId") val playlistId: Int, @ColumnInfo(name = "userId") val userId: Int
)