package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistsDao {
    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<PlaylistsEntity>>
}