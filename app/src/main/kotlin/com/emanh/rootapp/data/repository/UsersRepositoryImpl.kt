package com.emanh.rootapp.data.repository

import com.emanh.rootapp.data.datasource.UsersDataSource
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersDataSource: UsersDataSource
) : UsersRepository {
    override fun getAllUsers(): Flow<List<UsersModel>> {
        return usersDataSource.getAllUsers().map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getYourFavoriteArtists(userId: Long): Flow<UsersModel> {
        return usersDataSource.getYourFavoriteArtists(userId).map { entity ->
            mapToModel(entity)
        }
    }

    override fun getSimilarArtists(userId: Long): Flow<List<UsersModel>> {
        return usersDataSource.getSimilarArtists(userId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getOwnerPlaylist(userId: Long): Flow<UsersModel> {
        return usersDataSource.getOwnerPlaylist(userId).map { entity -> mapToModel(entity) }
    }

    override fun getOwnerAlbum(albumId: Long): Flow<List<UsersModel>> {
        return usersDataSource.getOwnerAlbum(albumId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getArtistById(userId: Long): Flow<UsersModel> {
        return usersDataSource.getArtistById(userId).map { entity -> mapToModel(entity) }
    }

    override fun getSearchArtists(value: String): Flow<List<UsersModel>> {
        return usersDataSource.getSearchArtists(value).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getArtistsBySearch(listId: List<Long>): Flow<List<UsersModel>> {
        return usersDataSource.getArtistsBySearch(listId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getFoveriteArtistsByUser(userId: Long): Flow<List<UsersModel>> {
        return usersDataSource.getFoveriteArtistsByUser(userId).map { entities ->
            entities.map { entity ->
                mapToModel(entity)
            }
        }
    }

    override fun getOwnerPlaylistYour(playlistId: Long): Flow<UsersModel> {
        return usersDataSource.getOwnerPlaylistYour(playlistId).map { entity ->
            mapToModel(entity)
        }
    }

    override fun getUsername(username: String): Flow<UsersModel?> {
        return usersDataSource.getUsername(username).map { entity ->
            entity?.let { mapToModel(it) }
        }
    }

    override fun getEmail(email: String): Flow<UsersModel?> {
        return usersDataSource.getEmail(email).map { entity ->
            entity?.let { mapToModel(it) }
        }
    }

    override suspend fun getGetUserLogin(account: String, password: String): UsersModel? {
        return usersDataSource.getGetUserLogin(account, password)?.let { entity ->
            mapToModel(entity)
        }
    }

    override suspend fun insertUser(user: UsersModel): Long {
        return usersDataSource.insertUser(mapToEntity(user))
    }

    override suspend fun insertAllUsers(users: List<UsersModel>) {
        usersDataSource.insertAllUsers(users.map { mapToEntity(it) })
    }

    private fun mapToModel(entity: UsersEntity): UsersModel {
        return UsersModel(id = entity.userId,
                          isArtist = entity.isArtist,
                          username = entity.username,
                          email = entity.email,
                          password = entity.password,
                          avatarUrl = entity.avatarUrl,
                          name = entity.name,
                          normalizedSearchValue = entity.normalizedSearchValue,
                          followers = entity.followers,
                          following = entity.followingIdList)
    }

    private fun mapToEntity(model: UsersModel): UsersEntity {
        return UsersEntity(userId = model.id,
                           isArtist = model.isArtist,
                           username = model.username,
                           email = model.email,
                           password = model.password,
                           avatarUrl = model.avatarUrl,
                           name = model.name,
                           normalizedSearchValue = model.normalizedSearchValue,
                           followers = model.followers,
                           followingIdList = model.following)
    }
}