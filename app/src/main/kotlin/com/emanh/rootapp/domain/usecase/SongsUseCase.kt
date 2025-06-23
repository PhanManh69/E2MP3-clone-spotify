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

    fun getRecommendedSongs(userId: Long): Flow<List<SongsModel>> {
        return songsRepository.getRecommendedSongs(userId)
    }

    fun getRecentlyListenedSongs(userId: Long): Flow<List<SongsModel>> {
        return songsRepository.getRecentlyListenedSongs(userId)
    }

    fun getTrendingSongs(): Flow<List<SongsModel>> {
        return songsRepository.getTrendingSongs()
    }

    fun getSimilarSongs(): Flow<List<SongsModel>> {
        return songsRepository.getSimilarSongs()
    }

    fun getMoreByArtists(songId: Long): Flow<List<SongsModel>> {
        return songsRepository.getMoreByArtists(songId)
    }

    fun getSongsByArtist(userId: Long): Flow<List<SongsModel>> {
        return songsRepository.getSongsByArtist(userId)
    }

    fun getSearchSong(value: String): Flow<List<SongsModel>> {
        return songsRepository.getSearchSong(value)
    }

    fun getSongsBySearch(listId: List<Long>): Flow<List<SongsModel>> {
        return songsRepository.getSongsBySearch(listId)
    }

    fun getSongsById(songId: Long): Flow<SongsModel> {
        return songsRepository.getSongsById(songId)
    }

    fun getLikedSongsByUser(userId: Long): Flow<List<SongsModel>> {
        return songsRepository.getLikedSongsByUser(userId)
    }

    fun getSongsRecommend(): Flow<SongsModel> {
        return songsRepository.getSongsRecommend()
    }

    fun getProcessingSongs(userId: Long): Flow<List<SongsModel>> {
        return songsRepository.getProcessingSongs(userId)
    }

    suspend fun insertSong(song: SongsModel): Long {
        return songsRepository.insertSong(song)
    }
}