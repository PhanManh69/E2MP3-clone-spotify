package com.emanh.rootapp.data.db.initializer

import android.util.Log
import com.emanh.rootapp.data.db.dao.AlbumsDao
import com.emanh.rootapp.data.db.dao.GenresDao
import com.emanh.rootapp.data.db.dao.SongsDao
import com.emanh.rootapp.data.db.dao.UsersDao
import com.emanh.rootapp.data.db.fakedata.fakeAlbumsData
import com.emanh.rootapp.data.db.fakedata.fakeGenresData
import com.emanh.rootapp.data.db.fakedata.fakeSongsData
import com.emanh.rootapp.data.db.fakedata.fakeUsersData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "DatabaseInitializer"

@Singleton
class DatabaseInitializer @Inject constructor(
    private val genresDao: GenresDao, private val songsDao: SongsDao, private val usersDao: UsersDao, private val albumsDao: AlbumsDao
) {
    private val _isDatabaseInitialized = MutableStateFlow(false)
    val isDatabaseInitialized: StateFlow<Boolean> = _isDatabaseInitialized.asStateFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        initializeDatabase()
    }

    private fun initializeDatabase() {
        coroutineScope.launch {
            try {
                if (!checkDatabaseConnection()) {
                    _isDatabaseInitialized.value = false
                    return@launch
                }

                val genresList = withTimeout(5000) {
                    genresDao.getAllGenres().first()
                }
                val songsList = withTimeout(5000) {
                    songsDao.getAllSongs().first()
                }
                val usersList = withTimeout(5000) {
                    usersDao.getAllUsers().first()
                }
                val albumsList = withTimeout(5000) {
                    albumsDao.getAllAlbums().first()
                }

                if (genresList.isEmpty()) {
                    Log.d(TAG, "Inserting genres data: ${fakeGenresData().size} items")
                    try {
                        genresDao.insertAllGenres(fakeGenresData())
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert genres data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (songsList.isEmpty()) {
                    Log.d(TAG, "Inserting songs data: ${fakeSongsData().size} items")
                    try {
                        songsDao.insertAllSongs(fakeSongsData())
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert songs data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (usersList.isEmpty()) {
                    Log.d(TAG, "Inserting users data: ${fakeUsersData().size} items")
                    try {
                        usersDao.insertAllUsers(fakeUsersData())
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert users data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (albumsList.isEmpty()) {
                    Log.d(TAG, "Inserting albums data: ${fakeAlbumsData().size} items")
                    try {
                        albumsDao.insertAllAlbums(fakeAlbumsData())
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert albums data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                _isDatabaseInitialized.value = true
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize database: ${e.message}")
                _isDatabaseInitialized.value = false
            }
        }
    }

    private suspend fun checkDatabaseConnection(): Boolean {
        return try {
            withTimeout(2000) {
                withContext(Dispatchers.IO) {
                    try {
                        genresDao.getAllGenres().first()
                        true
                    } catch (e: Exception) {
                        Log.e(TAG, "Database connection check failed: $e")
                        false
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Database connection timeout: $e")
            false
        }
    }

    fun reinitializeDatabase() {
        _isDatabaseInitialized.value = false
        initializeDatabase()
    }
} 