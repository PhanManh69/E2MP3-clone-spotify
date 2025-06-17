package com.emanh.rootapp.data.db.entity.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cross_ref_song_like", primaryKeys = ["songId", "userId"])
data class SongLikeEntity(
    @ColumnInfo(name = "songId") val songId: Long, @ColumnInfo(name = "userId") val userId: Long
)