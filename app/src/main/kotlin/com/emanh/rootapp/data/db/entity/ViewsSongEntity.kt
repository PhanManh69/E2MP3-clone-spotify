package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "views_song")
data class ViewsSongEntity(
    @PrimaryKey(autoGenerate = true) val viewsSongId: Long = 0,
    @ColumnInfo(name = "user_id") val userId: Long? = null,
    @ColumnInfo(name = "song_id") val songId: Long? = null,
    @ColumnInfo(name = "number_listener") val numberListener: Long? = null,
    @ColumnInfo(name = "date_time") val dateTime: String? = null
)