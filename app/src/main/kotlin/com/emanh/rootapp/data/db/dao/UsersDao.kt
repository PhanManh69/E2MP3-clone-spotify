package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.utils.MyQuery.QUERY_ARTIST_BY_ID
import com.emanh.rootapp.utils.MyQuery.QUERY_OWNER_ALBUM
import com.emanh.rootapp.utils.MyQuery.QUERY_SIMILAR_ARTISTS
import com.emanh.rootapp.utils.MyQuery.QUERY_YOUR_FAVORITE_ARTISTS
import com.emanh.rootapp.utils.MyQuery.QUERY_OWNER_PLAYLIST
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UsersEntity>>

    @Query(QUERY_YOUR_FAVORITE_ARTISTS)
    fun getYourFavoriteArtists(userId: Int): Flow<UsersEntity>

    @Query(QUERY_SIMILAR_ARTISTS)
    fun getSimilarArtists(userId: Int): Flow<List<UsersEntity>>

    @Query(QUERY_OWNER_PLAYLIST)
    fun getOwnerPlaylist(userId: Int): Flow<UsersEntity>

    @Query(QUERY_OWNER_ALBUM)
    fun getOwnerAlbum(albumId: Int): Flow<List<UsersEntity>>

    @Query(QUERY_ARTIST_BY_ID)
    fun getArtistById(userId: Int): Flow<UsersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UsersEntity>)
}