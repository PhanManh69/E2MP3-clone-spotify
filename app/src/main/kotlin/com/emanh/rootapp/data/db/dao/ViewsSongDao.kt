package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.ViewsSongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewsSongDao {
    @Query("SELECT * FROM views_song")
    fun getAllViewsSong(): Flow<List<ViewsSongEntity>>
}