package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.PodcastsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PodcastsDao {
    @Query("SELECT * FROM podcasts")
    fun getAllPodcasts(): Flow<List<PodcastsEntity>>
}