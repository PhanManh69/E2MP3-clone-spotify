package com.emanh.rootapp.domain.usecase.crossref

import com.emanh.rootapp.data.db.entity.crossref.PlaylistLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import com.emanh.rootapp.domain.repository.crossref.CrossRefPlaylistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefPlaylistUseCase @Inject constructor(
    private val crossRefPlaylistRepository: CrossRefPlaylistRepository
) {
    fun getYourTopMixes(): Flow<List<CrossRefPlaylistsModel>> {
        return crossRefPlaylistRepository.getYourTopMixes()
    }

    fun getPlaylistCard(): Flow<List<CrossRefPlaylistsModel>> {
        return crossRefPlaylistRepository.getPlaylistCard()
    }

    fun getPlaylistDetailsById(playlistId: Int): Flow<CrossRefPlaylistsModel> {
        return crossRefPlaylistRepository.getPlaylistDetailsById(playlistId)
    }

    fun getPlaylistLike(playlistLikeEntity: PlaylistLikeEntity): Flow<PlaylistLikeEntity?> {
        return crossRefPlaylistRepository.getPlaylistLike(playlistLikeEntity)
    }

    suspend fun deletePlaylistLike(playlistLikeEntity: PlaylistLikeEntity) {
        return crossRefPlaylistRepository.deletePlaylistLike(playlistLikeEntity)
    }

    suspend fun insertPlaylistLike(playlistLikeEntity: PlaylistLikeEntity) {
        return crossRefPlaylistRepository.insertPlaylistLike(playlistLikeEntity)
    }
}