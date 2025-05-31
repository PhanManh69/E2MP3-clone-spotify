package com.emanh.rootapp.domain.repository.crossref

import com.emanh.rootapp.data.db.entity.crossref.PlaylistLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefPlaylistRepository {
    fun getAllCrossRefPlaylists(): Flow<List<CrossRefPlaylistsModel>>

    fun getYourTopMixes(): Flow<List<CrossRefPlaylistsModel>>

    fun getPlaylistCard(): Flow<List<CrossRefPlaylistsModel>>

    fun getPlaylistDetailsById(playlistId: Int): Flow<CrossRefPlaylistsModel>

    fun getPlaylistLike(playlistLikeEntity: PlaylistLikeEntity): Flow<PlaylistLikeEntity?>

    suspend fun deletePlaylistLike(playlistLikeEntity: PlaylistLikeEntity)

    suspend fun insertPlaylistLike(playlistLikeEntity: PlaylistLikeEntity)
}