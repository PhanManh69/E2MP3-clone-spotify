package com.emanh.rootapp.domain.repository.crossref

import com.emanh.rootapp.domain.model.crossref.PlaylistWithSongModel
import kotlinx.coroutines.flow.Flow

interface PlaylistSongRepository {
    fun getAllPlaylistWithSong(): Flow<List<PlaylistWithSongModel>>

    fun getQuickPlaylist(userId: Int): Flow<List<PlaylistWithSongModel>>
}