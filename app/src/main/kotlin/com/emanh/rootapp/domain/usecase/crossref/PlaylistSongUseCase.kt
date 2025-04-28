package com.emanh.rootapp.domain.usecase.crossref

import com.emanh.rootapp.domain.model.crossref.PlaylistWithSongModel
import com.emanh.rootapp.domain.repository.crossref.PlaylistSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistSongUseCase @Inject constructor(
    private val playlistSongRepository: PlaylistSongRepository
) {
    fun getAllPlaylistWithSong(): Flow<List<PlaylistWithSongModel>> {
        return playlistSongRepository.getAllPlaylistWithSong()
    }

    fun getQuickPlaylist(userId: Int): Flow<List<PlaylistWithSongModel>> {
        return playlistSongRepository.getQuickPlaylist(userId)
    }
}