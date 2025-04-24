package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenresEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, @ColumnInfo(name = "name_id") val nameId: Int? = null
)