package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.UploadModel
import com.emanh.rootapp.domain.repository.UploadRepository
import javax.inject.Inject

class UploadUseCase @Inject constructor(
    private val uplaodRepository: UploadRepository
) {
    suspend fun insertUpdoad(upload: UploadModel): Long {
        return uplaodRepository.insertUpdoad(upload)
    }
}