package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ViewsSongEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user") val user: Int? = null,
    @ColumnInfo(name = "song") val song: Int? = null,
    @ColumnInfo(name = "number_listener") val numberListener: Int? = null,
    @ColumnInfo(name = "date_time") val dateTime: String
)