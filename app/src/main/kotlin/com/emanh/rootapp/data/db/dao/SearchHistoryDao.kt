package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.SearchHistoryEntity
import com.emanh.rootapp.utils.MyQuery.QUERY_DELETE_DUPLICATE
import com.emanh.rootapp.utils.MyQuery.QUERY_GET_SEARCH_HISTORY
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history")
    fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Query(QUERY_GET_SEARCH_HISTORY)
    fun getSearchHistory(userId: Long): Flow<List<SearchHistoryEntity>>

    @Query(QUERY_DELETE_DUPLICATE)
    suspend fun deleteDuplicate(userId: Long, tableId: Long, type: String?)

    @Query("DELETE FROM search_history WHERE table_id = :songId AND type = 'songs_search'")
    fun deleteSongFromSearchHistory(songId: Long): Int

    @Insert()
    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity)
}