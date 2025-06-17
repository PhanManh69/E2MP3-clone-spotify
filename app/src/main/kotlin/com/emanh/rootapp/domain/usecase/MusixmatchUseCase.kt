package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.repository.MusixmatchRepository
import javax.inject.Inject

class MusixmatchUseCase @Inject constructor(
    private val musixmatchRepository: MusixmatchRepository
) {
    suspend fun getLyrics(trackName: String, artistName: String): String? {
        return musixmatchRepository.getLyrics(trackName, artistName)
    }
}