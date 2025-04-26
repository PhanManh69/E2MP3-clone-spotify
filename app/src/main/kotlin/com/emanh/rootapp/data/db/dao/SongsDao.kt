package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.utils.MyQuery.QUERY_RECENTLY_LISTENED_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_SONGS_BY_GENRES
import kotlinx.coroutines.flow.Flow

@Dao
interface SongsDao {
    @Query("SELECT * FROM songs")
    fun getAllSongs(): Flow<List<SongsEntity>>

    @Query(QUERY_SONGS_BY_GENRES)
    fun getRecommendedSongs(userId: Int): Flow<List<SongsEntity>>

    @Query(QUERY_RECENTLY_LISTENED_SONGS)
    fun getRecentlyListenedSongs(userId: Int): Flow<List<SongsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSongs(songs: List<SongsEntity>)
}