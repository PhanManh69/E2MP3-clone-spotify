package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.entity.crossref.SongArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.data.db.entity.crossref.SongLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefSongDataSource {
    fun getAllCrossRefSongs(): Flow<List<CrossRefSongsModel>>

    fun getSongDetailsById(songId: Int): Flow<CrossRefSongsModel>

    fun getSongLike(songLikeEntity: SongLikeEntity): Flow<SongLikeEntity?>

    suspend fun deleteSongLike(songLikeEntity: SongLikeEntity)

    suspend fun insertSongLike(songLikeEntity: SongLikeEntity)

    suspend fun insertAllCrossRefSongGenre(songGenreEntity: List<SongGenreEntity>)

    suspend fun insertAllCrossRefSongArtist(songArtistEntity: List<SongArtistEntity>)
}