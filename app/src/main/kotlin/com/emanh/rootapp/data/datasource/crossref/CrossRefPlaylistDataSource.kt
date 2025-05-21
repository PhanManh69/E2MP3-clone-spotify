package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefPlaylistDataSource {
    fun getAllCrossRefPlaylists(): Flow<List<CrossRefPlaylistsModel>>

    fun getYourTopMixes(): Flow<List<CrossRefPlaylistsModel>>

    fun getPlaylistCard(): Flow<List<CrossRefPlaylistsModel>>

    fun getPlaylistDetailsById(playlistId: Int): Flow<CrossRefPlaylistsModel>

    suspend fun insertAllCrossRefPlaylistSong(playlistSongEntity: List<PlaylistSongEntity>)
}