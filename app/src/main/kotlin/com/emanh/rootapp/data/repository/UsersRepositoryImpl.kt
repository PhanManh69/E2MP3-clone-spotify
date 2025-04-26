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
                UsersModel(id = entity.id,
                           isArtist = entity.isArtist,
                           username = entity.username,
                           email = entity.email,
                           password = entity.password,
                           avatarUrl = entity.avatarUrl,
                           name = entity.name,
                           followers = entity.followers,
                           following = entity.followingIdList)
            }
        }
    }

    override suspend fun insertAllUsers(users: List<UsersModel>) {
        usersDataSource.insertAllUsers(users.map { mapToEntity(it) })
    }

    private fun mapToEntity(model: UsersModel): UsersEntity {
        return UsersEntity(id = model.id,
                           isArtist = model.isArtist,
                           username = model.username,
                           email = model.email,
                           password = model.password,
                           avatarUrl = model.avatarUrl,
                           name = model.name,
                           followers = model.followers,
                           followingIdList = model.following)
    }
}