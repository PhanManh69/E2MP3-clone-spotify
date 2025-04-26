package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emanh.rootapp.data.db.entity.ViewsSongEntity
import com.emanh.rootapp.utils.MyQuery.QUERY_VIEW_RECORD
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewsSongDao {
    @Query("SELECT * FROM views_song")
    fun getAllViewsSong(): Flow<List<ViewsSongEntity>>
    
    @Query(QUERY_VIEW_RECORD)
    suspend fun findViewRecord(userId: Int, songId: Int): ViewsSongEntity?
    
    @Update
    suspend fun updateViewsSong(viewsSong: ViewsSongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViewsSong(viewsSong: ViewsSongEntity)
}