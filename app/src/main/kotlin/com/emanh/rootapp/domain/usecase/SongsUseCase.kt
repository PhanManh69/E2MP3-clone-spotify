package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.repository.SongsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongsUseCase @Inject constructor(
    private val songsRepository: SongsRepository
) {
    fun getAllSongs(): Flow<List<SongsModel>> {
        return songsRepository.getAllSongs()
    }

    fun getRecommendedSongs(): Flow<List<SongsModel>> {
        return songsRepository.getRecommendedSongs()
    }

    fun getRecentlyListenedSongs(userId: Int): Flow<List<SongsModel>> {
        return songsRepository.getRecentlyListenedSongs(userId)
    }
}