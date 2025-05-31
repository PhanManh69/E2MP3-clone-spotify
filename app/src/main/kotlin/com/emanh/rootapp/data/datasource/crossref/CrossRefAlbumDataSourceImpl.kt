package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.dao.crossref.CrossRefAlbumDao
import com.emanh.rootapp.data.db.entity.crossref.AlbumArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumSongEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrossRefAlbumDataSourceImpl @Inject constructor(
    private val crossRefAlbumDao: CrossRefAlbumDao
) : CrossRefAlbumDataSource {
    override fun getAllCrossRefAlbums(): Flow<List<CrossRefAlbumsModel>> {
        return crossRefAlbumDao.getAllCrossRefAlbums()
    }

    override fun getAlbumDetailsById(albumId: Int): Flow<CrossRefAlbumsModel> {
        return crossRefAlbumDao.getAlbumDetailsById(albumId)
    }

    override fun getAlbumLike(albumLikeEntity: AlbumLikeEntity): Flow<AlbumLikeEntity?> {
        return crossRefAlbumDao.getAlbumLike(albumId = albumLikeEntity.albumId, userId = albumLikeEntity.userId)
    }

    override suspend fun deleteAlbumLike(albumLikeEntity: AlbumLikeEntity) {
        return crossRefAlbumDao.deleteAlbumLike(albumId = albumLikeEntity.albumId, userId = albumLikeEntity.userId)
    }

    override suspend fun insertAlbumLike(albumLikeEntity: AlbumLikeEntity) {
        return crossRefAlbumDao.insertAlbumLike(albumLikeEntity)
    }

    override suspend fun insertAllCrossRefAlbumSong(albumSongEntity: List<AlbumSongEntity>) {
        crossRefAlbumDao.insertAllCrossRefAlbumSong(albumSongEntity)
    }

    override suspend fun insertAllCrossRefAlbumArtist(albumArtistEntity: List<AlbumArtistEntity>) {
        crossRefAlbumDao.insertAllCrossRefAlbumArtist(albumArtistEntity)
    }
}