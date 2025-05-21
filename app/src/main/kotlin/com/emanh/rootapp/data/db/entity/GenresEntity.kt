package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenresEntity(
    @PrimaryKey val genreId: Int = 0, @ColumnInfo(name = "name_id") val nameId: Int? = null
)