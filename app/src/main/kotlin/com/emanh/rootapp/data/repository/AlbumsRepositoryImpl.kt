package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.AlbumsDataSource
import com.emanh.rootapp.data.db.entity.AlbumsEntity
import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlbumsRepositoryImpl @Inject constructor(
    private val albumsDataSource: AlbumsDataSource
) : AlbumsRepository {
    override fun getAllAlbums(): Flow<List<AlbumsModel>> {
        return albumsDataSource.getAllAlbums().map { entities ->
            entities.map { entity ->
                AlbumsModel(id = entity.albumId,
                            avatarUrl = entity.avatarUrl,
                            title = entity.title,
                            subtitle = entity.subtitle,
                            albumType = entity.avatarUrl,
                            releaseDate = entity.releaseDate,
                            artist = entity.artistsIdList,
                            songs = entity.songsIdList)
            }
        }
    }

    override suspend fun insertAllAlbums(albums: List<AlbumsModel>) {
        albumsDataSource.insertAlbums(albums.map { mapToEntity(it) })
    }

    private fun mapToEntity(model: AlbumsModel): AlbumsEntity {
        return AlbumsEntity(albumId = model.id,
                            avatarUrl = model.avatarUrl,
                            title = model.title,
                            subtitle = model.subtitle,
                            albumType = model.avatarUrl,
                            releaseDate = model.releaseDate,
                            artistsIdList = model.artist,
                            songsIdList = model.songs)
    }
}