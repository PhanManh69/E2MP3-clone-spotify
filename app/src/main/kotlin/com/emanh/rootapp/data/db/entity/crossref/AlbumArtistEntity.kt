package com.emanh.rootapp.data.db.entity.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cross_ref_album_artist", primaryKeys = ["albumId", "userId"])
data class AlbumArtistEntity(
    @ColumnInfo(name = "albumId") val albumId: Int, @ColumnInfo(name = "userId") val userId: Int
)