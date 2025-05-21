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

    override suspend fun insertAlbums(albums: List<AlbumsEntity>) {
        albumsDao.insertAllAlbums(albums)
    }
}