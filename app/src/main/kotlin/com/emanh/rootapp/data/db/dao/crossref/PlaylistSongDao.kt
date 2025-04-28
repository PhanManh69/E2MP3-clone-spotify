package com.emanh.rootapp.data.db.dao.crossref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.domain.model.crossref.PlaylistWithSongModel
import com.emanh.rootapp.utils.MyQuery.QUERY_QUICK_PLAYLIST
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistSongDao {
    @Transaction
    @Query("SELECT * FROM playlists")
    fun getAllPlaylistWithSong(): Flow<List<PlaylistWithSongModel>>

    @Transaction
    @Query(QUERY_QUICK_PLAYLIST)
    fun getQuickPlaylist(userId: Int): Flow<List<PlaylistWithSongModel>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefPlaylistSong(playlistSongEntity: List<PlaylistSongEntity>)
}