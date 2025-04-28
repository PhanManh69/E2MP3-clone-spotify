package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.SongsDataSource
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.repository.SongsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SongsRepositoryImpl @Inject constructor(
    private val songsDataSource: SongsDataSource
) : SongsRepository {
    override fun getAllSongs(): Flow<List<SongsModel>> {
        return songsDataSource.getAllSongs().map { entities ->
            entities.map { entity ->
                SongsModel(id = entity.songId,
                           avatarUrl = entity.avatarUrl,
                           songUrl = entity.songUrl,
                           title = entity.title,
                           subtitle = entity.subtitle,
                           timeline = entity.timeline,
                           releaseDate = entity.releaseDate,
                           genres = entity.genresIdList,
                           likes = entity.likesIdList,
                           artists = entity.artistsIdList)
            }
        }
    }

    override fun getRecommendedSongs(userId: Int): Flow<List<SongsModel>> {
        return songsDataSource.getRecommendedSongs(userId).map { entities ->
            entities.map { entity ->
                SongsModel(id = entity.songId,
                           avatarUrl = entity.avatarUrl,
                           songUrl = entity.songUrl,
                           title = entity.title,
                           subtitle = entity.subtitle,
                           timeline = entity.timeline,
                           releaseDate = entity.releaseDate,
                           genres = entity.genresIdList,
                           likes = entity.likesIdList,
                           artists = entity.artistsIdList)
            }
        }
    }

    override fun getRecentlyListenedSongs(userId: Int): Flow<List<SongsModel>> {
        return songsDataSource.getRecentlyListenedSongs(userId).map { entities ->
            entities.map { entity ->
                SongsModel(id = entity.songId,
                           avatarUrl = entity.avatarUrl,
                           songUrl = entity.songUrl,
                           title = entity.title,
                           subtitle = entity.subtitle,
                           timeline = entity.timeline,
                           releaseDate = entity.releaseDate,
                           genres = entity.genresIdList,
                           likes = entity.likesIdList,
                           artists = entity.artistsIdList)
            }
        }
    }

    override fun getTrendingSongs(): Flow<List<SongsModel>> {
        return songsDataSource.getTrendingSongs().map { entities ->
            entities.map { entity ->
                SongsModel(id = entity.songId,
                           avatarUrl = entity.avatarUrl,
                           songUrl = entity.songUrl,
                           title = entity.title,
                           subtitle = entity.subtitle,
                           timeline = entity.timeline,
                           releaseDate = entity.releaseDate,
                           genres = entity.genresIdList,
                           likes = entity.likesIdList,
                           artists = entity.artistsIdList)
            }
        }
    }

    override suspend fun insertAllSongs(songs: List<SongsModel>) {
        songsDataSource.insertAllSongs(songs.map { mapToEntity(it) })
    }

    private fun mapToEntity(model: SongsModel): SongsEntity {
        return SongsEntity(songId = model.id,
                           avatarUrl = model.avatarUrl,
                           songUrl = model.songUrl,
                           title = model.title,
                           subtitle = model.subtitle,
                           timeline = model.timeline,
                           releaseDate = model.releaseDate,
                           genresIdList = model.genres,
                           likesIdList = model.likes,
                           artistsIdList = model.artists)
    }
}