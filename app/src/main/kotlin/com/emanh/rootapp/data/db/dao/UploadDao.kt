package com.emanh.rootapp.data.db.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emanh.rootapp.data.db.entity.StatusUpload
import com.emanh.rootapp.data.db.entity.UploadEntity

@Dao
interface UploadDao {
    @Query("SELECT * FROM upload WHERE status_upload = :statusUpload")
    fun getProcessingSongsCursor(statusUpload: String = StatusUpload.PROCESSING.name): Cursor

    @Update
    suspend fun updateUpload(upload: UploadEntity): Int

    @Insert()
    suspend fun insertUpdoad(upload: UploadEntity): Long
}