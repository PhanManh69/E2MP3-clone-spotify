package com.emanh.rootapp.domain.usecase.crossref

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
}