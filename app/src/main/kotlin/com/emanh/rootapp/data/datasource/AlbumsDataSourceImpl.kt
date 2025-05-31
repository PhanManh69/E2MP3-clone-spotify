package com.emanh.rootapp.data.datasource

import com.emanh.rootapp.data.db.dao.AlbumsDao
import com.emanh.rootapp.data.db.entity.AlbumsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlbumsDataSourceImpl @Inject constructor(
    private val albumsDao: AlbumsDao
) : AlbumsDataSource {
    override fun getAllAlbums(): Flow<List<AlbumsEntity>> {
        return albumsDao.getAllAlbums()
    }

    override fun getQuickAlbum(): Flow<List<AlbumsEntity>> {
        return albumsDao.getQuickAlbum()
    }

    override fun getSimilarAlbums(): Flow<List<AlbumsEntity>> {
        return albumsDao.getSimilarAlbums()
    }

    override fun getSearchAlbums(value: String): Flow<List<AlbumsEntity>> {
        return albumsDao.getSearchAlbums(value)
    }

    override fun getAlbumsBySearch(listId: List<Int>): Flow<List<AlbumsEntity>> {
        return albumsDao.getAlbumsBySearch(listId)
    }

    override fun getAlbumsById(albumId: Int): Flow<AlbumsEntity> {
        return albumsDao.getAlbumsById(albumId)
    }

    override fun getAlbumLikeByUser(userId: Int): Flow<List<AlbumsEntity>> {
        return albumsDao.getAlbumLikeByUser(userId)
    }

    override suspend fun insertAlbums(albums: List<AlbumsEntity>) {
        albumsDao.insertAllAlbums(albums)
    }
}