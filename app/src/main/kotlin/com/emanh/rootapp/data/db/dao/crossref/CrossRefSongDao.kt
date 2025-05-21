package com.emanh.rootapp.data.db.dao.crossref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.emanh.rootapp.data.db.entity.crossref.SongArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.domain.model.crossref.CrossRefAlbumsModel
import com.emanh.rootapp.domain.model.crossref.CrossRefSongsModel
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_ALBUM_SONGS
import com.emanh.rootapp.utils.MyQuery.QUERY_SONGS_BY_ARTIST
import com.emanh.rootapp.utils.MyQuery.QUERY_SONG_BY_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface CrossRefSongDao {
    @Transaction
    @Query("SELECT * FROM songs")
    fun getAllCrossRefSongs(): Flow<List<CrossRefSongsModel>>

    @Transaction
    @Query(QUERY_SONG_BY_ID)
    fun getSongDetailsById(songId: Int): Flow<CrossRefSongsModel>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefSongGenre(songGenreEntity: List<SongGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCrossRefSongArtist(songGenreEntity: List<SongArtistEntity>)
}