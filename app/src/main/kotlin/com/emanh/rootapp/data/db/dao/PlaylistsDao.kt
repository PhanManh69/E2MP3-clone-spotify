package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import com.emanh.rootapp.utils.MyQuery.QERRY_SEARCH_PLAYLISTS
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_PLAYLISTS_BY_SEARCH
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_PLAYLIST_BY_ID
import com.emanh.rootapp.utils.MyQuery.QUERY_QUICK_PLAYLIST
import com.emanh.rootapp.utils.MyQuery.QUERY_RADIO_FOR_YOU
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistsDao {
    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<PlaylistsEntity>>

    @Query(QUERY_QUICK_PLAYLIST)
    fun getQuickPlaylist(userId: Int): Flow<List<PlaylistsEntity>>

    @Query(QUERY_RADIO_FOR_YOU)
    fun getRadioForYou(): Flow<List<PlaylistsEntity>>

    @Query(QERRY_SEARCH_PLAYLISTS)
    fun getSearchPlaylists(value: String): Flow<List<PlaylistsEntity>>

    @Query(QUERY_GET_PLAYLISTS_BY_SEARCH)
    fun getPlaylistsBySearch(listId: List<Int>): Flow<List<PlaylistsEntity>>

    @Query(QUERY_GET_PLAYLIST_BY_ID)
    fun getPlaylistsById(playlistId: Int): Flow<PlaylistsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaylists(playlists: List<PlaylistsEntity>)
}