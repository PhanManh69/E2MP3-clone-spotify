package com.emanh.rootapp.data.db.entity.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cross_ref_song_genre", primaryKeys = ["songId", "genreId"])
data class SongGenreEntity(
    @ColumnInfo(name = "songId") val songId: Long, @ColumnInfo(name = "genreId") val genreId: Long
)