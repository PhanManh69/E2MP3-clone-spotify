package com.emanh.rootapp.domain.repository.crossref

import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefSongRepository {
    fun getAllCrossRefSongs(): Flow<List<CrossRefSongsModel>>

    fun getSongDetailsById(songId: Int): Flow<CrossRefSongsModel>
}