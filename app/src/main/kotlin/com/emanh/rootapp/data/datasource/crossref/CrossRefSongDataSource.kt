package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.entity.crossref.SongArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefSongDataSource {
    fun getAllCrossRefSongs(): Flow<List<CrossRefSongsModel>>

    fun getSongDetailsById(songId: Int): Flow<CrossRefSongsModel>

    suspend fun insertAllCrossRefSongGenre(songGenreEntity: List<SongGenreEntity>)

    suspend fun insertAllCrossRefSongArtist(songArtistEntity: List<SongArtistEntity>)
}