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

    override fun getQuickPlaylist(userId: Long): Flow<List<PlaylistsEntity>> {
        return playlistsDao.getQuickPlaylist(userId)
    }

    override fun getRadioForYou(): Flow<List<PlaylistsEntity>> {
        return playlistsDao.getRadioForYou()
    }

    override fun getSearchPlaylists(value: String): Flow<List<PlaylistsEntity>> {
        return playlistsDao.getSearchPlaylists(value)
    }

    override fun getPlaylistsBySearch(listId: List<Long>): Flow<List<PlaylistsEntity>> {
        return playlistsDao.getPlaylistsBySearch(listId)
    }

    override fun getPlaylistsById(playlistId: Long): Flow<PlaylistsEntity> {
        return playlistsDao.getPlaylistsById(playlistId)
    }

    override fun getPlaylistsYourByUser(userId: Long): Flow<List<PlaylistsEntity>> {
        return playlistsDao.getPlaylistsYourByUser(userId)
    }

    override fun getPlaylistsForYouByUser(userId: Long): Flow<List<PlaylistsEntity>> {
        return playlistsDao.getPlaylistsForYouByUser(userId)
    }

    override suspend fun updatePlaylist(playlists: PlaylistsEntity) {
        return playlistsDao.updatePlaylist(playlists)
    }

    override suspend fun insertPlaylistYour(playlists: PlaylistsEntity): Long {
        return playlistsDao.insertPlaylistYour(playlists)
    }

    override suspend fun insertAllPlaylists(playlists: List<PlaylistsEntity>) {
        playlistsDao.insertAllPlaylists(playlists)
    }
}