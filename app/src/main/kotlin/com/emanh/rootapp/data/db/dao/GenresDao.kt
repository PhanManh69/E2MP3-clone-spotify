package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.GenresEntity
import com.emanh.rootapp.utils.MyQuery.QUERY_GENRE_BY_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {
    @Query("SELECT * FROM genres")
    fun getAllGenres(): Flow<List<GenresEntity>>

    @Query(QUERY_GENRE_BY_ID)
    fun getGenreById(genreId: Int): Flow<GenresEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGenres(genres: List<GenresEntity>)
}