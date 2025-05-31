package com.emanh.rootapp.domain.repository.crossref

import com.emanh.rootapp.data.db.entity.crossref.SongLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefSongRepository {
    fun getAllCrossRefSongs(): Flow<List<CrossRefSongsModel>>

    fun getSongDetailsById(songId: Int): Flow<CrossRefSongsModel>

    fun getSongLike(songLikeEntity: SongLikeEntity): Flow<SongLikeEntity?>

    suspend fun deleteSongLike(songLikeEntity: SongLikeEntity)

    suspend fun insertSongLike(songLikeEntity: SongLikeEntity)
}