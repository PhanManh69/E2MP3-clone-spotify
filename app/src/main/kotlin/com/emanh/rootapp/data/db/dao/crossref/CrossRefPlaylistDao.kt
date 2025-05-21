package com.emanh.rootapp.data.db.dao.crossref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import com.emanh.rootapp.utils.MyQuery.QUERY_PLAYLIST_CARD
import com.emanh.rootapp.utils.MyQuery.QUERY_YOUR_TOP_MIXES
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_PLAYLIST_SONGS
import kotlinx.coroutines.flow.Flow

@Dao
interface CrossRefPlaylistDao {
    @Transaction
    @Query("SELECT * FROM playlists")
    fun getAllCrossRefPlaylists(): Flow<List<CrossRefPlaylistsModel>>

    @Transaction
    @Query(QUERY_YOUR_TOP_MIXES)
    fun getYourTopMixes(): Flow<List<CrossRefPlaylistsModel>>

    @Transaction
    @Query(QUERY_PLAYLIST_CARD)
    fun getPlaylistCard(): Flow<List<CrossRefPlaylistsModel>>

    @Transaction
    @Query(QUERY_GET_PLAYLIST_SONGS)
    fun getPlaylistDetailsById(playlistId: Int): Flow<CrossRefPlaylistsModel>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefPlaylistSong(playlistSongEntity: List<PlaylistSongEntity>)
}