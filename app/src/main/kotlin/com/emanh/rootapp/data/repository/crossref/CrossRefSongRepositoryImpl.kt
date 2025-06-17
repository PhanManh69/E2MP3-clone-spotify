package com.emanh.rootapp.data.repository.crossref

import com.emanh.rootapp.data.datasource.crossref.CrossRefSongDataSource
import com.emanh.rootapp.data.db.entity.crossref.SongArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.SongLikeEntity
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

    override fun getSongDetailsById(songId: Long): Flow<CrossRefSongsModel> {
        return crossRefSongDataSource.getSongDetailsById(songId)
    }

    override fun getRandomSongDetails(): Flow<List<CrossRefSongsModel>> {
        return crossRefSongDataSource.getRandomSongDetails()
    }

    override fun getSongLike(songLikeEntity: SongLikeEntity): Flow<SongLikeEntity?> {
        return crossRefSongDataSource.getSongLike(songLikeEntity)
    }

    override suspend fun deleteSongLike(songLikeEntity: SongLikeEntity) {
        return crossRefSongDataSource.deleteSongLike(songLikeEntity)
    }

    override suspend fun insertSongLike(songLikeEntity: SongLikeEntity) {
        return crossRefSongDataSource.insertSongLike(songLikeEntity)
    }

    override suspend fun insertSongArtist(songArtistEntity: SongArtistEntity) {
        return crossRefSongDataSource.insertSongArtist(songArtistEntity)
    }
}