package com.emanh.rootapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class StatusUpload {
    PROCESSING, SUCCESS, FAILED
}

@Entity(tableName = "upload")
class UploadEntity(
    @PrimaryKey(autoGenerate = true) val uploadId: Long = 0,
    @ColumnInfo(name = "user_id") val userId: Long = 0,
    @ColumnInfo(name = "song_id") val songId: Long = 0,
    @ColumnInfo(name = "status_upload") val statusUpload: String = StatusUpload.PROCESSING.name,
)