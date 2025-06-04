package com.emanh.rootapp.data.db.dao.crossref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.emanh.rootapp.data.db.entity.crossref.AlbumArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumSongEntity
import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import com.emanh.rootapp.utils.MyQuery.QUERY_DELETE_ALBUM_LIKE
import com.emanh.rootapp.utils.MyQuery.QUERY_DELETE_USER_FOLLOWING
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_ALBUM_LIKE
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_ALBUM_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_USER_FOLLWING
import kotlinx.coroutines.flow.Flow

@Dao
interface CrossRefAlbumDao {
    @Transaction
    @Query("SELECT * FROM albums")
    fun getAllCrossRefAlbums(): Flow<List<CrossRefAlbumsModel>>

    @Transaction
    @Query(QUERY_GET_ALBUM_SONGS)
    fun getAlbumDetailsById(albumId: Long): Flow<CrossRefAlbumsModel>

    @Transaction
    @Query(QUERY_GET_ALBUM_LIKE)
    fun getAlbumLike(albumId: Long, userId: Long): Flow<AlbumLikeEntity?>

    @Query(QUERY_DELETE_ALBUM_LIKE)
    suspend fun deleteAlbumLike(albumId: Long, userId: Long)

    @Insert()
    suspend fun insertAlbumLike(albumLikeEntity: AlbumLikeEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefAlbumSong(albumSongEntity: List<AlbumSongEntity>)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefAlbumArtist(albumArtistEntity: List<AlbumArtistEntity>)
}