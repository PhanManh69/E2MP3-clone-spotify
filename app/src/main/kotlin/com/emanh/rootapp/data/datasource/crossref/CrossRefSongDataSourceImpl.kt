package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.dao.crossref.CrossRefSongDao
import com.emanh.rootapp.data.db.entity.crossref.SongArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.data.db.entity.crossref.SongLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefSongDataSourceImpl @Inject constructor(
    private val crossRefSongDao: CrossRefSongDao
) : CrossRefSongDataSource {
    override fun getAllCrossRefSongs(): Flow<List<CrossRefSongsModel>> {
        return crossRefSongDao.getAllCrossRefSongs()
    }

    override fun getSongDetailsById(songId: Int): Flow<CrossRefSongsModel> {
        return crossRefSongDao.getSongDetailsById(songId)
    }

    override fun getSongLike(songLikeEntity: SongLikeEntity): Flow<SongLikeEntity?> {
        return crossRefSongDao.getSongLike(songLikeEntity.songId, songLikeEntity.userId)
    }

    override suspend fun deleteSongLike(songLikeEntity: SongLikeEntity) {
        return crossRefSongDao.deleteSongLike(songLikeEntity.songId, songLikeEntity.userId)
    }

    override suspend fun insertSongLike(songLikeEntity: SongLikeEntity) {
        return crossRefSongDao.insertSongLike(songLikeEntity)
    }

    override suspend fun insertAllCrossRefSongGenre(songGenreEntity: List<SongGenreEntity>) {
        crossRefSongDao.insertAllCrossRefSongGenre(songGenreEntity)
    }

    override suspend fun insertAllCrossRefSongArtist(songArtistEntity: List<SongArtistEntity>) {
        crossRefSongDao.insertAllCrossRefSongArtist(songArtistEntity)
    }
}