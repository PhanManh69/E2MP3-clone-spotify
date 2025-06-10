package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.AlbumsEntity
import com.emanh.rootapp.utils.MyQuery.QERRY_SEARCH_ALBUMS
import com.emanh.rootapp.utils.MyQuery.QUERY_ALBUMS_BY_ARTIST
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_ALBUMS_BY_SEARCH
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_ALBUMS_BY_ID
import com.emanh.rootapp.utils.MyQuery.QUERY_QUICK_ALBUM
import com.emanh.rootapp.utils.MyQuery.QUERY_SIMILAR_ALBUMS
import com.emanh.rootapp.utils.MyQuery.QUETY_GET_ALBUM_LIKE_BY_USER
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<AlbumsEntity>>

    @Query(QUERY_QUICK_ALBUM)
    fun getQuickAlbum(): Flow<List<AlbumsEntity>>

    @Query(QUERY_SIMILAR_ALBUMS)
    fun getSimilarAlbums(): Flow<List<AlbumsEntity>>

    @Query(QERRY_SEARCH_ALBUMS)
    fun getSearchAlbums(value: String): Flow<List<AlbumsEntity>>

    @Query(QUERY_GET_ALBUMS_BY_SEARCH)
    fun getAlbumsBySearch(listId: List<Long>): Flow<List<AlbumsEntity>>

    @Query(QUERY_GET_ALBUMS_BY_ID)
    fun getAlbumsById(albumId: Long): Flow<AlbumsEntity>

    @Query(QUETY_GET_ALBUM_LIKE_BY_USER)
    fun getAlbumLikeByUser(userId: Long): Flow<List<AlbumsEntity>>

    @Query(QUERY_ALBUMS_BY_ARTIST)
    fun getAlbumByArtist(userId: Long): Flow<List<AlbumsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbums(albums: List<AlbumsEntity>)
}