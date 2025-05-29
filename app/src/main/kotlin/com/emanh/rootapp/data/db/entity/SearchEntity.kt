package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true) val idSearch: Int = 0,
    @ColumnInfo(name = "idTable") val idTable: Int = 0,
    @ColumnInfo(name = "isTable") val isTable: String? = null,
    @ColumnInfo(name = "normalized_search_value") val normalizedSearchValue: String? = null,
)