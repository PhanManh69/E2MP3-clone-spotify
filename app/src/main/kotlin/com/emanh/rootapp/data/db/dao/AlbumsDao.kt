package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.AlbumsEntity
import com.emanh.rootapp.utils.MyQuery.QUERY_QUICK_ALBUM
import com.emanh.rootapp.utils.MyQuery.QUERY_SIMILAR_ALBUMS
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<AlbumsEntity>>

    @Query(QUERY_QUICK_ALBUM)
    fun getQuickAlbum(): Flow<List<AlbumsEntity>>

    @Query(QUERY_SIMILAR_ALBUMS)
    fun getSimilarAlbums(): Flow<List<AlbumsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbums(albums: List<AlbumsEntity>)
}