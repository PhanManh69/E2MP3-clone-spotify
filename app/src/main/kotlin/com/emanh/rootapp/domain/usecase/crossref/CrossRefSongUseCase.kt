package com.emanh.rootapp.domain.usecase.crossref

import com.emanh.rootapp.data.db.entity.crossref.SongLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import com.emanh.rootapp.domain.repository.crossref.CrossRefSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefSongUseCase @Inject constructor(
    private val crossRefSongRepository: CrossRefSongRepository
) {
    fun getSongDetailsById(songId: Int): Flow<CrossRefSongsModel> {
        return crossRefSongRepository.getSongDetailsById(songId)
    }

    fun getSongLike(songLikeEntity: SongLikeEntity): Flow<SongLikeEntity?> {
        return crossRefSongRepository.getSongLike(songLikeEntity)
    }

    suspend fun deleteSongLike(songLikeEntity: SongLikeEntity) {
        return crossRefSongRepository.deleteSongLike(songLikeEntity)
    }

    suspend fun insertSongLike(songLikeEntity: SongLikeEntity) {
        return crossRefSongRepository.insertSongLike(songLikeEntity)
    }
}