package com.emanh.rootapp.data.repository.crossref

import com.emanh.rootapp.data.datasource.crossref.CrossRefPlaylistDataSource
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

    override fun getPlaylistDetailsById(playlistId: Int): Flow<CrossRefPlaylistsModel> {
        return crossRefPlaylistDataSource.getPlaylistDetailsById(playlistId)
    }
}