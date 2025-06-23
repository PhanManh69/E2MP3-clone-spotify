package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.dao.UploadDao
import com.emanh.rootapp.data.db.entity.UploadEntity
import javax.inject.Inject

class UploadDataSourceImpl @Inject constructor(
    private val uploadDao: UploadDao
) : UploadDataSource {
    override suspend fun insertUpdoad(upload: UploadEntity): Long {
        return uploadDao.insertUpdoad(upload)
    }
}