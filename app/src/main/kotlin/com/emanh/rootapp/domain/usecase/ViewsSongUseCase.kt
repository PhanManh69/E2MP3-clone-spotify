package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.ViewsSongModel
import com.emanh.rootapp.domain.repository.ViewsSongRepository
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ViewsSongUseCase @Inject constructor(
    private val viewsSongRepository: ViewsSongRepository
) {
    fun getTotalListenerAlbum(albumId: Long): Flow<Long> {
        return viewsSongRepository.getTotalListenerAlbum(albumId)
    }

    fun getListenerMonth(userId: Long): Flow<Long> {
        return viewsSongRepository.getListenerMonth(userId)
    }

    suspend fun trackSongView(userId: Long, songId: Long): ViewsSongModel {
        val currentDateTime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
        val existingRecord = viewsSongRepository.findViewRecord(userId, songId)

        return if (existingRecord != null) {
            val updatedRecord = existingRecord.copy(
                numberListener = (existingRecord.numberListener ?: 0) + 1,
                dateTime = currentDateTime
            )
            viewsSongRepository.updateViewsSong(updatedRecord)
            updatedRecord
        } else {
            val newRecord = ViewsSongModel(
                    id = 0,
                    userId = userId,
                    songId = songId,
                    numberListener = 1,
                    dateTime = currentDateTime
            )
            viewsSongRepository.insertViewsSong(newRecord)
            newRecord
        }
    }
}