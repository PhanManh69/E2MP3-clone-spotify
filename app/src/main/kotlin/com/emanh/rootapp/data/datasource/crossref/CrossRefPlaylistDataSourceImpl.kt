package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.dao.crossref.CrossRefPlaylistDao
import com.emanh.rootapp.data.db.entity.crossref.PlaylistLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefPlaylistDataSourceImpl @Inject constructor(
    private val crossRefPlaylistDao: CrossRefPlaylistDao
) : CrossRefPlaylistDataSource {
    override fun getAllCrossRefPlaylists(): Flow<List<CrossRefPlaylistsModel>> {
        return crossRefPlaylistDao.getAllCrossRefPlaylists()
    }

    override fun getYourTopMixes(): Flow<List<CrossRefPlaylistsModel>> {
        return crossRefPlaylistDao.getYourTopMixes()
    }

    override fun getPlaylistCard(): Flow<List<CrossRefPlaylistsModel>> {
        return crossRefPlaylistDao.getPlaylistCard()
    }

    override fun getPlaylistDetailsById(playlistId: Long): Flow<CrossRefPlaylistsModel> {
        return crossRefPlaylistDao.getPlaylistDetailsById(playlistId)
    }

    override fun getPlaylistLike(playlistLikeEntity: PlaylistLikeEntity): Flow<PlaylistLikeEntity?> {
        return crossRefPlaylistDao.getPlaylistLike(playlistId = playlistLikeEntity.playlistId, userId = playlistLikeEntity.userId)
    }

    override suspend fun deletePlaylistLike(playlistLikeEntity: PlaylistLikeEntity) {
        return crossRefPlaylistDao.deletePlaylistLike(playlistId = playlistLikeEntity.playlistId, userId = playlistLikeEntity.userId)
    }

    override suspend fun deletePlaylistSong(playlistSongEntity: PlaylistSongEntity) {
        return crossRefPlaylistDao.deletePlaylistSong(playlistId = playlistSongEntity.playlistId, songId = playlistSongEntity.songId)
    }

    override suspend fun insertPlaylistLike(playlistLikeEntity: PlaylistLikeEntity) {
        return crossRefPlaylistDao.insertPlaylistLike(playlistLikeEntity)
    }

    override suspend fun insertSongToPlaylist(playlistSongEntity: PlaylistSongEntity) {
        return crossRefPlaylistDao.insertSongToPlaylist(playlistSongEntity)
    }

    override suspend fun insertAllCrossRefPlaylistSong(playlistSongEntity: List<PlaylistSongEntity>) {
        crossRefPlaylistDao.insertAllCrossRefPlaylistSong(playlistSongEntity)
    }
}