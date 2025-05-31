package com.emanh.rootapp.domain.repository

import com.emanh.rootapp.domain.model.PlaylistsModel
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getAllPlaylists(): Flow<List<PlaylistsModel>>

    fun getQuickPlaylist(userId: Int): Flow<List<PlaylistsModel>>

    fun getRadioForYou(): Flow<List<PlaylistsModel>>

    fun getSearchPlaylists(value: String): Flow<List<PlaylistsModel>>

    fun getPlaylistsBySearch(listId: List<Int>): Flow<List<PlaylistsModel>>

    fun getPlaylistsById(playlistId: Int): Flow<PlaylistsModel>

    fun getPlaylistsYourByUser(userId: Int): Flow<List<PlaylistsModel>>

    fun getPlaylistsForYouByUser(userId: Int): Flow<List<PlaylistsModel>>
}