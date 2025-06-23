package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emanh.rootapp.data.db.entity.ViewsSongEntity
import com.emanh.rootapp.utils.MyQuery.QUERY_TOTAL_LISTENER_ALBUM
import com.emanh.rootapp.utils.MyQuery.QUERY_LISTENER_MONTH
import com.emanh.rootapp.utils.MyQuery.QUERY_VIEW_RECORD
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewsSongDao {
    @Query("SELECT * FROM views_song")
    fun getAllViewsSong(): Flow<List<ViewsSongEntity>>

    @Query(QUERY_TOTAL_LISTENER_ALBUM)
    fun getTotalListenerAlbum(albumId: Long): Flow<Long>

    @Query(QUERY_LISTENER_MONTH)
    fun getListenerMonth(userId: Long): Flow<Long>

    @Query(QUERY_VIEW_RECORD)
    suspend fun findViewRecord(userId: Long, songId: Long): ViewsSongEntity?

    @Query("DELETE FROM views_song WHERE song_id = :songId")
    fun deleteSongFromView(songId: Long): Int
    
    @Update
    suspend fun updateViewsSong(viewsSong: ViewsSongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViewsSong(viewsSong: ViewsSongEntity)
}