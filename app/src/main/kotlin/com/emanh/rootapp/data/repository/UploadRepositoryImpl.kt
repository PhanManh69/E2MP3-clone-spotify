package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.UploadDataSource
import com.emanh.rootapp.data.db.entity.UploadEntity
import com.emanh.rootapp.domain.model.UploadModel
import com.emanh.rootapp.domain.repository.UploadRepository
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    private val uploadDataSource: UploadDataSource
) : UploadRepository {
    override suspend fun insertUpdoad(upload: UploadModel): Long {
        return uploadDataSource.insertUpdoad(mapToEntity(upload))
    }

    private fun mapToEntity(model: UploadModel) = UploadEntity(
            uploadId = model.uploadId,
            userId = model.userId,
            songId = model.songId,
            statusUpload = model.statusUpload,
    )
}