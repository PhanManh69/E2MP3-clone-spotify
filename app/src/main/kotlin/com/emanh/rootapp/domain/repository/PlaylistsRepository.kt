package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.PlaylistsModel
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getAllPlaylists(): Flow<List<PlaylistsModel>>
}