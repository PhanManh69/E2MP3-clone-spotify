package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.PlaylistsModel
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getAllPlaylists(): Flow<List<PlaylistsModel>>

    fun getQuickPlaylist(userId: Long): Flow<List<PlaylistsModel>>

    fun getRadioForYou(): Flow<List<PlaylistsModel>>

    fun getSearchPlaylists(value: String): Flow<List<PlaylistsModel>>

    fun getPlaylistsBySearch(listId: List<Long>): Flow<List<PlaylistsModel>>

    fun getPlaylistsById(playlistId: Long): Flow<PlaylistsModel>

    fun getPlaylistsYourByUser(userId: Long): Flow<List<PlaylistsModel>>

    fun getPlaylistsForYouByUser(userId: Long): Flow<List<PlaylistsModel>>

    suspend fun updatePlaylist(playlists: PlaylistsModel)

    suspend fun insertPlaylistYour(playlists: PlaylistsModel): Long
}