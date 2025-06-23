package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.entity.UploadEntity

interface UploadDataSource {
    suspend fun insertUpdoad(upload: UploadEntity): Long
}