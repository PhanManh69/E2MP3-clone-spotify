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
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getQuickAlbum(): Flow<List<AlbumsModel>> {
        return albumsDataSource.getQuickAlbum().map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getSimilarAlbums(): Flow<List<AlbumsModel>> {
        return albumsDataSource.getSimilarAlbums().map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getSearchAlbums(value: String): Flow<List<AlbumsModel>> {
        return albumsDataSource.getSearchAlbums(value).map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getAlbumsBySearch(listId: List<Int>): Flow<List<AlbumsModel>> {
        return albumsDataSource.getAlbumsBySearch(listId).map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getAlbumsById(albumId: Int): Flow<AlbumsModel> {
        return albumsDataSource.getAlbumsById(albumId).map { entity ->
            mapToModel(entity)
        }
    }

    override fun getAlbumLikeByUser(userId: Int): Flow<List<AlbumsModel>> {
        return albumsDataSource.getAlbumLikeByUser(userId).map { entities ->
            entities.map { entity -> mapToModel(entity) }
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
                            albumType = model.albumType,
                            releaseDate = model.releaseDate,
                            artistsIdList = model.artist,
                            songsIdList = model.songs)
    }

    private fun mapToModel(entity: AlbumsEntity): AlbumsModel {
        return AlbumsModel(id = entity.albumId,
                           avatarUrl = entity.avatarUrl,
                           title = entity.title,
                           subtitle = entity.subtitle,
                           albumType = entity.albumType,
                           releaseDate = entity.releaseDate,
                           artist = entity.artistsIdList,
                           songs = entity.songsIdList)
    }
}