package com.emanh.rootapp.data.db.dao.crossref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.emanh.rootapp.data.db.entity.crossref.SongArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.data.db.entity.crossref.SongLikeEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import com.emanh.rootapp.utils.MyQuery.QUERY_DELETE_SONG_LIKE
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_SONG_LIKE
import com.emanh.rootapp.utils.MyQuery.QUERY_RANDOM_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_SONG_BY_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface CrossRefSongDao {
    @Transaction
    @Query("SELECT * FROM songs")
    fun getAllCrossRefSongs(): Flow<List<CrossRefSongsModel>>

    @Transaction
    @Query(QUERY_SONG_BY_ID)
    fun getSongDetailsById(songId: Long): Flow<CrossRefSongsModel>

    @Transaction
    @Query(QUERY_RANDOM_SONGS)
    fun getRandomSongDetails(): Flow<List<CrossRefSongsModel>>

    @Transaction
    @Query(QUERY_GET_SONG_LIKE)
    fun getSongLike(songId: Long, userId: Long): Flow<SongLikeEntity?>

    @Query(QUERY_DELETE_SONG_LIKE)
    suspend fun deleteSongLike(songId: Long, userId: Long)

    @Query("DELETE FROM cross_ref_song_artist WHERE songId = :songId")
    fun deleteSongFromArtist(songId: Long): Int

    @Query("DELETE FROM cross_ref_song_genre WHERE songId = :songId")
    fun deleteSongFromGenre(songId: Long): Int

    @Query("DELETE FROM cross_ref_song_like WHERE songId = :songId")
    fun deleteSongFromLike(songId: Long): Int

    @Insert()
    suspend fun insertSongLike(songLikeEntity: SongLikeEntity)

    @Insert()
    suspend fun insertSongArtist(songArtistEntity: SongArtistEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefSongGenre(songGenreEntity: List<SongGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefSongArtist(songGenreEntity: List<SongArtistEntity>)
}