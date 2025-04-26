package com.emanh.rootapp.data.db.dao.crossref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.domain.model.crossref.SongsWithGenresModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SongGenreDao {
    @Transaction
    @Query("SELECT * FROM songs")
    fun getAllSongsWithGenres(): Flow<List<SongsWithGenresModel>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefSongGenre(songGenreEntity: List<SongGenreEntity>)
}