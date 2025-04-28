package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistsUseCase @Inject constructor(
    private val playlistsRepository: PlaylistsRepository
) {
    fun getAllPlaylists(): Flow<List<PlaylistsModel>> {
        return playlistsRepository.getAllPlaylists()
    }
}