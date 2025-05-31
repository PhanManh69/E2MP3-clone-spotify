package com.emanh.rootapp.data.db.entity.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cross_ref_album_like", primaryKeys = ["albumId", "userId"])
data class AlbumLikeEntity(
    @ColumnInfo(name = "albumId") val albumId: Int, @ColumnInfo(name = "userId") val userId: Int
)