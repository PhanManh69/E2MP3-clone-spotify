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

    override fun getQuickPlaylist(userId: Int): Flow<List<PlaylistsModel>> {
        return playlistsDataSource.getQuickPlaylist(userId).map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    override fun getRadioForYou(): Flow<List<PlaylistsModel>> {
        return playlistsDataSource.getRadioForYou().map { entities ->
            entities.map { entity -> mapToModel(entity) }
        }
    }

    private fun mapToModel(entity: PlaylistsEntity): PlaylistsModel {
        return PlaylistsModel(id = entity.playlistId,
                              avatarUrl = entity.avatarUrl,
                              title = entity.title,
                              subtitle = entity.subtitle,
                              ownerId = entity.ownerId,
                              releaseDate = entity.releaseDate,
                              songsIdList = entity.songsIdList)
    }
}