package com.emanh.rootapp.data.datasource.crossref

import com.emanh.rootapp.data.db.entity.crossref.AlbumArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumSongEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import kotlinx.coroutines.flow.Flow

interface CrossRefAlbumDataSource {
    fun getAllCrossRefAlbums(): Flow<List<CrossRefAlbumsModel>>

    fun getAlbumDetailsById(albumId: Long): Flow<CrossRefAlbumsModel>

    fun getAlbumLike(albumLikeEntity: AlbumLikeEntity): Flow<AlbumLikeEntity?>

    suspend fun deleteAlbumLike(albumLikeEntity: AlbumLikeEntity)

    suspend fun insertAlbumLike(albumLikeEntity: AlbumLikeEntity)

    suspend fun insertAllCrossRefAlbumSong(albumSongEntity: List<AlbumSongEntity>)

    suspend fun insertAllCrossRefAlbumArtist(albumArtistEntity: List<AlbumArtistEntity>)
}