package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.entity.crossref.AlbumArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumSongEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefAlbumDataSource {
    fun getAllCrossRefAlbums(): Flow<List<CrossRefAlbumsModel>>

    fun getAlbumDetailsById(albumId: Int): Flow<CrossRefAlbumsModel>

    suspend fun insertAllCrossRefAlbumSong(albumSongEntity: List<AlbumSongEntity>)

    suspend fun insertAllCrossRefAlbumArtist(albumArtistEntity: List<AlbumArtistEntity>)
}