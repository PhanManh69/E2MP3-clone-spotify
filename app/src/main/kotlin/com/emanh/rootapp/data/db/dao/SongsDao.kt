package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.SongsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongsDao {
    @Query("SELECT * FROM songs")
    fun getAllSongs(): Flow<List<SongsEntity>>

    @Query("SELECT * " +
           "FROM songs " +
           "WHERE genres LIKE '[' || :genreId || ',%'" +
           "OR genres LIKE '%,' || :genreId || ',%'" +
           "OR genres LIKE '%,' || :genreId || ']'" +
           "OR genres = '[' || :genreId || ']' " +
           "ORDER BY RANDOM() " +
           "LIMIT 10")
    fun getSongByGenreId(genreId: String): Flow<List<SongsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSongs(songs: List<SongsEntity>)
}