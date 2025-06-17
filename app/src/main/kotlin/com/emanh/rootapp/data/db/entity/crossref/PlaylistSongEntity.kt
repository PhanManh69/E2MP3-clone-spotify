package com.emanh.rootapp.data.db.entity.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cross_ref_playlist_song", primaryKeys = ["playlistId", "songId"])
data class PlaylistSongEntity(
    @ColumnInfo(name = "playlistId") val playlistId: Long, @ColumnInfo(name = "songId") val songId: Long
)