package com.emanh.rootapp.data.db.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.StatusUpload
import com.emanh.rootapp.data.db.entity.UploadEntity
import com.emanh.rootapp.utils.MyQuery.QERRY_SEARCH_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_PROCESSING_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_RANDOM_SONG_EXCLUDING
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_SONGS_BY_SEARCH
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_SONGS_RECOMMEND
import com.emanh.rootapp.utils.MyQuery.QUERY_MORE_BY_ARTISTS
import com.emanh.rootapp.utils.MyQuery.QUERY_RECENTLY_LISTENED_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_RECOMMENDED_YOUR
import com.emanh.rootapp.utils.MyQuery.QUERY_SIMILAR_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_SONGS_BY_ARTIST
import com.emanh.rootapp.utils.MyQuery.QUERY_SONG_BY_ID
import com.emanh.rootapp.utils.MyQuery.QUERY_TRENDING
import com.emanh.rootapp.utils.MyQuery.QUETY_GET_LIKED_SONGS_BY_USER
import kotlinx.coroutines.flow.Flow

@Dao
interface SongsDao {
    @Query("SELECT * FROM songs")
    fun getAllSongs(): Flow<List<SongsEntity>>

    @Query("SELECT * FROM songs WHERE status_upload = :statusUpload")
    fun getAllSongsCursor(statusUpload: String = StatusUpload.SUCCESS.name): Cursor

    @Query("SELECT * FROM songs WHERE status_upload = :statusUpload")
    fun getProcessingSongsCursor(statusUpload: String = StatusUpload.PROCESSING.name): Cursor

    @Query(QUERY_RECOMMENDED_YOUR)
    fun getRecommendedSongs(userId: Long): Flow<List<SongsEntity>>

    @Query(QUERY_RECENTLY_LISTENED_SONGS)
    fun getRecentlyListenedSongs(userId: Long): Flow<List<SongsEntity>>

    @Query(QUERY_TRENDING)
    fun getTrendingSongs(): Flow<List<SongsEntity>>

    @Query(QUERY_SIMILAR_SONGS)
    fun getSimilarSongs(): Flow<List<SongsEntity>>

    @Query(QUERY_MORE_BY_ARTISTS)
    fun getMoreByArtists(songId: Long): Flow<List<SongsEntity>>

    @Query(QUERY_SONGS_BY_ARTIST)
    fun getSongsByArtist(userId: Long): Flow<List<SongsEntity>>

    @Query(QUERY_SONG_BY_ID)
    fun getSongsById(songId: Long): Flow<SongsEntity>

    @Query(QERRY_SEARCH_SONGS)
    fun getSearchSong(value: String): Flow<List<SongsEntity>>

    @Query(QUERY_GET_SONGS_BY_SEARCH)
    fun getSongsBySearch(listId: List<Long>): Flow<List<SongsEntity>>

    @Query(QUETY_GET_LIKED_SONGS_BY_USER)
    fun getLikedSongsByUser(userId: Long): Flow<List<SongsEntity>>

    @Query(QUERY_GET_SONGS_RECOMMEND)
    fun getSongsRecommend(): Flow<SongsEntity>

    @Query(QUERY_GET_RANDOM_SONG_EXCLUDING)
    fun getRandomSongExcluding(excludeIds: List<Long>): Flow<SongsEntity>

    @Query(QUERY_GET_PROCESSING_SONGS)
    fun getProcessingSongs(userId: Long): Flow<List<SongsEntity>>

    @Query("DELETE FROM songs WHERE songId = :songId")
    fun deleteSongById(songId: Long): Int

    @Query("UPDATE songs SET status_upload = :status WHERE songId = :id")
    suspend fun updateStatusUpload(id: Long, status: String): Int

    @Insert()
    suspend fun insertSong(song: SongsEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSongs(songs: List<SongsEntity>)
}