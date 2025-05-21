package com.emanh.rootapp.data.db.entity.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cross_ref_album_song", primaryKeys = ["albumId", "songId"])
data class AlbumSongEntity(
    @ColumnInfo(name = "albumId") val albumId: Int, @ColumnInfo(name = "songId") val songId: Int
)