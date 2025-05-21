package com.emanh.rootapp.data.repository.crossref

import com.emanh.rootapp.data.datasource.crossref.CrossRefSongDataSource
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import com.emanh.rootapp.domain.repository.crossref.CrossRefSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefSongRepositoryImpl @Inject constructor(
    private val crossRefSongDataSource: CrossRefSongDataSource
) : CrossRefSongRepository {
    override fun getAllCrossRefSongs(): Flow<List<CrossRefSongsModel>> {
        return crossRefSongDataSource.getAllCrossRefSongs()
    }

    override fun getSongDetailsById(songId: Int): Flow<CrossRefSongsModel> {
        return crossRefSongDataSource.getSongDetailsById(songId)
    }
}