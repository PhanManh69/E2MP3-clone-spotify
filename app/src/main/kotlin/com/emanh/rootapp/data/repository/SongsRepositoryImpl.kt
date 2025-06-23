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
                mapToModel(entity)
            }
        }
    }

    override fun getRecommendedSongs(userId: Long): Flow<List<SongsModel>> {
        return songsDataSource.getRecommendedSongs(userId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getRecentlyListenedSongs(userId: Long): Flow<List<SongsModel>> {
        return songsDataSource.getRecentlyListenedSongs(userId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getTrendingSongs(): Flow<List<SongsModel>> {
        return songsDataSource.getTrendingSongs().map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getSimilarSongs(): Flow<List<SongsModel>> {
        return songsDataSource.getSimilarSongs().map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getMoreByArtists(songId: Long): Flow<List<SongsModel>> {
        return songsDataSource.getMoreByArtists(songId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getSongsByArtist(userId: Long): Flow<List<SongsModel>> {
        return songsDataSource.getSongsByArtist(userId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getSongsById(songId: Long): Flow<SongsModel> {
        return songsDataSource.getSongsById(songId).map { entity ->
            mapToModel(entity)
        }
    }

    override fun getSearchSong(value: String): Flow<List<SongsModel>> {
        return songsDataSource.getSearchSong(value).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getSongsBySearch(listId: List<Long>): Flow<List<SongsModel>> {
        return songsDataSource.getSongsBySearch(listId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getLikedSongsByUser(userId: Long): Flow<List<SongsModel>> {
        return songsDataSource.getLikedSongsByUser(userId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getSongsRecommend(): Flow<SongsModel> {
        return songsDataSource.getSongsRecommend().map { entity ->
            mapToModel(entity)
        }
    }

    override fun getRandomSongExcluding(excludeIds: List<Long>): Flow<SongsModel> {
        return songsDataSource.getRandomSongExcluding(excludeIds).map { entity ->
            mapToModel(entity)
        }
    }

    override fun getProcessingSongs(userId: Long): Flow<List<SongsModel>> {
        return songsDataSource.getProcessingSongs(userId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override suspend fun insertSong(song: SongsModel): Long {
        return songsDataSource.insertSong(mapToEntity(song))
    }

    private fun mapToModel(entity: SongsEntity): SongsModel {
        return SongsModel(id = entity.songId,
                          avatarUrl = entity.avatarUrl,
                          songUrl = entity.songUrl,
                          title = entity.title,
                          subtitle = entity.subtitle,
                          normalizedSearchValue = entity.normalizedSearchValue,
                          timeline = entity.timeline,
                          releaseDate = entity.releaseDate,
                          genres = entity.genresIdList,
                          likes = entity.likesIdList,
                          artists = entity.artistsIdList,
                          statusUpload = entity.statusUpload)
    }

    private fun mapToEntity(model: SongsModel): SongsEntity {
        return SongsEntity(songId = model.id,
                           avatarUrl = model.avatarUrl,
                           songUrl = model.songUrl,
                           title = model.title,
                           subtitle = model.subtitle,
                           normalizedSearchValue = model.normalizedSearchValue,
                           timeline = model.timeline,
                           releaseDate = model.releaseDate,
                           genresIdList = model.genres,
                           likesIdList = model.likes,
                           artistsIdList = model.artists,
                           statusUpload = model.statusUpload)
    }
}