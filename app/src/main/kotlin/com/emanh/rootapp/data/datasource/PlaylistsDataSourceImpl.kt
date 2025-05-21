package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.dao.PlaylistsDao
import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistsDataSourceImpl @Inject constructor(
        private val playlistsDao: PlaylistsDao
) : PlaylistsDataSource {
    override fun getAllPlaylists(): Flow<List<PlaylistsEntity>> {
        return playlistsDao.getAllPlaylists()
    }

    override fun getQuickPlaylist(userId: Int): Flow<List<PlaylistsEntity>> {
        return playlistsDao.getQuickPlaylist(userId)
    }

    override fun getRadioForYou(): Flow<List<PlaylistsEntity>> {
        return playlistsDao.getRadioForYou()
    }

    override suspend fun insertAllPlaylists(playlists: List<PlaylistsEntity>) {
        playlistsDao.insertAllPlaylists(playlists)
    }
}