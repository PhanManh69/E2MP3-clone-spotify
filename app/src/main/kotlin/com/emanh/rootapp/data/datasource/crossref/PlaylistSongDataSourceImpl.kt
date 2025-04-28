package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.dao.crossref.PlaylistSongDao
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.domain.model.crossref.PlaylistWithSongModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistSongDataSourceImpl @Inject constructor(
    private val crossRefSongPlaylistSongDao: PlaylistSongDao
) : PlaylistSongDataSource {
    override fun getAllPlaylistWithSong(): Flow<List<PlaylistWithSongModel>> {
        return crossRefSongPlaylistSongDao.getAllPlaylistWithSong()
    }

    override fun getQuickPlaylist(userId: Int): Flow<List<PlaylistWithSongModel>> {
        return crossRefSongPlaylistSongDao.getQuickPlaylist(userId)
    }

    override suspend fun insertAllCrossRefPlaylistSong(playlistSongEntity: List<PlaylistSongEntity>) {
        crossRefSongPlaylistSongDao.insertAllCrossRefPlaylistSong(playlistSongEntity)
    }
}