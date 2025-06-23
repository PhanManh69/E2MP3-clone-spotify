package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.UploadModel

interface UploadRepository {
    suspend fun insertUpdoad(upload: UploadModel): Long
}