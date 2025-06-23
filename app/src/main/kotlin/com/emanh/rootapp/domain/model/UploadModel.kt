package com.emanh.rootapp.domain.model

import com.emanh.rootapp.data.db.entity.StatusUpload

data class UploadModel(
    val uploadId: Long = 0,
    val userId: Long = 0,
    val songId: Long = 0,
    val statusUpload: String = StatusUpload.PROCESSING.name,
)