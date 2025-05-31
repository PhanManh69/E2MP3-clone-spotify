package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistsUseCase @Inject constructor(
    private val playlistsRepository: PlaylistsRepository
) {
    fun getQuickPlaylist(userId: Int): Flow<List<PlaylistsModel>> {
        return playlistsRepository.getQuickPlaylist(userId)
    }

    fun getRadioForYou(): Flow<List<PlaylistsModel>> {
        return playlistsRepository.getRadioForYou()
    }

    fun getSearchPlaylists(value: String): Flow<List<PlaylistsModel>> {
        return playlistsRepository.getSearchPlaylists(value)
    }

    fun getPlaylistsBySearch(listId: List<Int>): Flow<List<PlaylistsModel>> {
        return playlistsRepository.getPlaylistsBySearch(listId)
    }

    fun getPlaylistsById(playlistId: Int): Flow<PlaylistsModel> {
        return playlistsRepository.getPlaylistsById(playlistId)
    }

    fun getPlaylistsYourByUser(userId: Int): Flow<List<PlaylistsModel>> {
        return playlistsRepository.getPlaylistsYourByUser(userId)
    }

    fun getPlaylistsForYouByUser(userId: Int): Flow<List<PlaylistsModel>> {
        return playlistsRepository.getPlaylistsForYouByUser(userId)
    }
}