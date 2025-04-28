package com.emanh.rootapp.data.repository.crossref

import com.emanh.rootapp.data.datasource.crossref.PlaylistSongDataSource
import com.emanh.rootapp.domain.model.crossref.PlaylistWithSongModel
import com.emanh.rootapp.domain.repository.crossref.PlaylistSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistSongRepositoryImpl @Inject constructor(
    private val playlistSongDataSource: PlaylistSongDataSource
) : PlaylistSongRepository {
    override fun getAllPlaylistWithSong(): Flow<List<PlaylistWithSongModel>> {
        return playlistSongDataSource.getAllPlaylistWithSong()
    }

    override fun getQuickPlaylist(userId: Int): Flow<List<PlaylistWithSongModel>> {
        return playlistSongDataSource.getQuickPlaylist(userId)
    }
}