package com.emanh.rootapp.data.repository.crossref

import com.emanh.rootapp.data.datasource.crossref.CrossRefPlaylistDataSource
import com.emanh.rootapp.data.db.entity.crossref.PlaylistLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import com.emanh.rootapp.domain.repository.crossref.CrossRefPlaylistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefPlaylistRepositoryImpl @Inject constructor(
    private val crossRefPlaylistDataSource: CrossRefPlaylistDataSource
) : CrossRefPlaylistRepository {
    override fun getAllCrossRefPlaylists(): Flow<List<CrossRefPlaylistsModel>> {
        return crossRefPlaylistDataSource.getAllCrossRefPlaylists()
    }

    override fun getYourTopMixes(): Flow<List<CrossRefPlaylistsModel>> {
        return crossRefPlaylistDataSource.getYourTopMixes()
    }

    override fun getPlaylistCard(): Flow<List<CrossRefPlaylistsModel>> {
        return crossRefPlaylistDataSource.getPlaylistCard()
    }

    override fun getPlaylistDetailsById(playlistId: Long): Flow<CrossRefPlaylistsModel> {
        return crossRefPlaylistDataSource.getPlaylistDetailsById(playlistId)
    }

    override fun getPlaylistLike(playlistLikeEntity: PlaylistLikeEntity): Flow<PlaylistLikeEntity?> {
        return crossRefPlaylistDataSource.getPlaylistLike(playlistLikeEntity)
    }

    override suspend fun deletePlaylistLike(playlistLikeEntity: PlaylistLikeEntity) {
        return crossRefPlaylistDataSource.deletePlaylistLike(playlistLikeEntity)
    }

    override suspend fun deletePlaylistSong(playlistSongEntity: PlaylistSongEntity) {
        return crossRefPlaylistDataSource.deletePlaylistSong(playlistSongEntity)
    }

    override suspend fun insertPlaylistLike(playlistLikeEntity: PlaylistLikeEntity) {
        return crossRefPlaylistDataSource.insertPlaylistLike(playlistLikeEntity)
    }

    override suspend fun insertSongToPlaylist(playlistSongEntity: PlaylistSongEntity) {
        return crossRefPlaylistDataSource.insertSongToPlaylist(playlistSongEntity)
    }
}