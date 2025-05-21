package com.emanh.rootapp.data.db.dao.crossref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.emanh.rootapp.data.db.entity.crossref.AlbumArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumSongEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_ALBUM_SONGS
import kotlinx.coroutines.flow.Flow

@Dao
interface CrossRefAlbumDao {
    @Transaction
    @Query("SELECT * FROM albums")
    fun getAllCrossRefAlbums(): Flow<List<CrossRefAlbumsModel>>

    @Transaction
    @Query(QUERY_GET_ALBUM_SONGS)
    fun getAlbumDetailsById(albumId: Int): Flow<CrossRefAlbumsModel>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefAlbumSong(albumSongEntity: List<AlbumSongEntity>)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefAlbumArtist(albumArtistEntity: List<AlbumArtistEntity>)
}