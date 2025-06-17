package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val searchHistoryId: Long = 0,
    @ColumnInfo(name = "user_id") val userId: Long = 0,
    @ColumnInfo(name = "table_id") val tableId: Long = 0,
    @ColumnInfo(name = "type") val type: String? = null
)