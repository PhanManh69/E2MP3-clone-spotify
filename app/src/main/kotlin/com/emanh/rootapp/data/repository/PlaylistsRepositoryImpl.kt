package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.PlaylistsDataSource
import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistsRepositoryImpl @Inject constructor(
    private val playlistsDataSource: PlaylistsDataSource
) : PlaylistsRepository {
    override fun getAllPlaylists(): Flow<List<PlaylistsModel>> {
        return playlistsDataSource.getAllPlaylists().map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getQuickPlaylist(userId: Long): Flow<List<PlaylistsModel>> {
        return playlistsDataSource.getQuickPlaylist(userId).map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getRadioForYou(): Flow<List<PlaylistsModel>> {
        return playlistsDataSource.getRadioForYou().map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getSearchPlaylists(value: String): Flow<List<PlaylistsModel>> {
        return playlistsDataSource.getSearchPlaylists(value).map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getPlaylistsBySearch(listId: List<Long>): Flow<List<PlaylistsModel>> {
        return playlistsDataSource.getPlaylistsBySearch(listId).map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getPlaylistsById(playlistId: Long): Flow<PlaylistsModel> {
        return playlistsDataSource.getPlaylistsById(playlistId).map { entity ->
            mapToModel(entity)
        }
    }

    override fun getPlaylistsYourByUser(userId: Long): Flow<List<PlaylistsModel>> {
        return playlistsDataSource.getPlaylistsYourByUser(userId).map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getPlaylistsForYouByUser(userId: Long): Flow<List<PlaylistsModel>> {
        return playlistsDataSource.getPlaylistsForYouByUser(userId).map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override suspend fun updatePlaylist(playlists: PlaylistsModel) {
        return playlistsDataSource.updatePlaylist(mapToEntity(playlists))
    }

    override suspend fun insertPlaylistYour(playlists: PlaylistsModel): Long {
        return playlistsDataSource.insertPlaylistYour(mapToEntity(playlists))
    }

    private fun mapToModel(entity: PlaylistsEntity): PlaylistsModel {
        return PlaylistsModel(id = entity.playlistId,
                              avatarUrl = entity.avatarUrl,
                              title = entity.title,
                              subtitle = entity.subtitle,
                              normalizedSearchValue = entity.normalizedSearchValue,
                              ownerId = entity.ownerId,
                              releaseDate = entity.releaseDate,
                              songsIdList = entity.songsIdList)
    }

    private fun mapToEntity(model: PlaylistsModel): PlaylistsEntity {
        return PlaylistsEntity(playlistId = model.id,
                               avatarUrl = model.avatarUrl,
                               title = model.title,
                               subtitle = model.subtitle,
                               normalizedSearchValue = model.normalizedSearchValue,
                               ownerId = model.ownerId,
                               releaseDate = model.releaseDate,
                               songsIdList = model.songsIdList)
    }
}