package com.emanh.rootapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanh.rootapp.data.db.entity.SearchEntity
import com.emanh.rootapp.utils.MyQuery.QERRY_SEARCH
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM search")
    fun getAllSearch(): Flow<List<SearchEntity>>

    @Query(QERRY_SEARCH)
    fun getAllSearch(value: String): Flow<List<SearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSearch(searchList: List<SearchEntity>)
}