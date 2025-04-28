package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.domain.model.crossref.PlaylistWithSongModel
import kotlinx.coroutines.flow.Flow

interface PlaylistSongDataSource {
    fun getAllPlaylistWithSong(): Flow<List<PlaylistWithSongModel>>

    fun getQuickPlaylist(userId: Int): Flow<List<PlaylistWithSongModel>>

    suspend fun insertAllCrossRefPlaylistSong(playlistSongEntity: List<PlaylistSongEntity>)
}