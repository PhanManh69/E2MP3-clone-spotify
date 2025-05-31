package com.emanh.rootapp.data.db.dao.crossref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.emanh.rootapp.data.db.entity.crossref.PlaylistLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefPlaylistsModel
import com.emanh.rootapp.utils.MyQuery.QUERY_DELETE_PLAYLIST_LIKE
import com.emanh.rootapp.utils.MyQuery.QUERY_DELETE_USER_FOLLOWING
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_PLAYLIST_LIKE
import com.emanh.rootapp.utils.MyQuery.QUERY_PLAYLIST_CARD
import com.emanh.rootapp.utils.MyQuery.QUERY_YOUR_TOP_MIXES
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_PLAYLIST_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_USER_FOLLWING
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

    @Transaction
    @Query(QUERY_GET_PLAYLIST_LIKE)
    fun getPlaylistLike(playlistId: Int, userId: Int): Flow<PlaylistLikeEntity?>

    @Query(QUERY_DELETE_PLAYLIST_LIKE)
    suspend fun deletePlaylistLike(playlistId: Int, userId: Int)

    @Insert()
    suspend fun insertPlaylistLike(playlistLikeEntity: PlaylistLikeEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefPlaylistSong(playlistSongEntity: List<PlaylistSongEntity>)
}