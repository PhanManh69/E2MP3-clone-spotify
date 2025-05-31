package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.utils.MyQuery.QERRY_SEARCH_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_SONGS_BY_SEARCH
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

    @Query(QUERY_RECOMMENDED_YOUR)
    fun getRecommendedSongs(userId: Int): Flow<List<SongsEntity>>

    @Query(QUERY_RECENTLY_LISTENED_SONGS)
    fun getRecentlyListenedSongs(userId: Int): Flow<List<SongsEntity>>

    @Query(QUERY_TRENDING)
    fun getTrendingSongs(): Flow<List<SongsEntity>>

    @Query(QUERY_SIMILAR_SONGS)
    fun getSimilarSongs(): Flow<List<SongsEntity>>

    @Query(QUERY_MORE_BY_ARTISTS)
    fun getMoreByArtists(songId: Int): Flow<List<SongsEntity>>

    @Query(QUERY_SONGS_BY_ARTIST)
    fun getSongsByArtist(userId: Int): Flow<List<SongsEntity>>

    @Query(QUERY_SONG_BY_ID)
    fun getSongsById(songId: Int): Flow<SongsEntity>

    @Query(QERRY_SEARCH_SONGS)
    fun getSearchSong(value: String): Flow<List<SongsEntity>>

    @Query(QUERY_GET_SONGS_BY_SEARCH)
    fun getSongsBySearch(listId: List<Int>): Flow<List<SongsEntity>>

    @Query(QUETY_GET_LIKED_SONGS_BY_USER)
    fun getLikedSongsByUser(userId: Int): Flow<List<SongsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSongs(songs: List<SongsEntity>)
}