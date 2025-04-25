package com.emanh.rootapp.domain.usecase

import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    fun getAllUsers(): Flow<List<UsersModel>> {
        return usersRepository.getAllUsers()
    }
}