package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.repository.SongsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongsUseCase @Inject constructor(
    private val songsRepository: SongsRepository
) {
    fun getRecommendedSongs(userId: Int): Flow<List<SongsModel>> {
        return songsRepository.getRecommendedSongs(userId)
    }

    fun getRecentlyListenedSongs(userId: Int): Flow<List<SongsModel>> {
        return songsRepository.getRecentlyListenedSongs(userId)
    }

    fun getTrendingSongs(): Flow<List<SongsModel>> {
        return songsRepository.getTrendingSongs()
    }

    fun getSimilarSongs(): Flow<List<SongsModel>> {
        return songsRepository.getSimilarSongs()
    }

    fun getMoreByArtists(songId: Int): Flow<List<SongsModel>> {
        return songsRepository.getMoreByArtists(songId)
    }

    fun getSongsByArtist(userId: Int): Flow<List<SongsModel>> {
        return songsRepository.getSongsByArtist(userId)
    }

    fun getSearchSong(value: String): Flow<List<SongsModel>> {
        return songsRepository.getSearchSong(value)
    }

    fun getSongsBySearch(listId: List<Int>): Flow<List<SongsModel>> {
        return songsRepository.getSongsBySearch(listId)
    }

    fun getSongsById(songId: Int): Flow<SongsModel> {
        return songsRepository.getSongsById(songId)
    }

    fun getLikedSongsByUser(userId: Int): Flow<List<SongsModel>> {
        return songsRepository.getLikedSongsByUser(userId)
    }
}