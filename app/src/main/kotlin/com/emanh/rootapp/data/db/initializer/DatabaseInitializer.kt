package com.emanh.rootapp.data.db.initializer

import android.util.Log
import com.emanh.rootapp.data.db.dao.AlbumsDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefSongDao
import com.emanh.rootapp.data.db.dao.GenresDao
import com.emanh.rootapp.data.db.dao.PlaylistsDao
import com.emanh.rootapp.data.db.dao.SearchDao
import com.emanh.rootapp.data.db.dao.SongsDao
import com.emanh.rootapp.data.db.dao.UsersDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefAlbumDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefPlaylistDao
import com.emanh.rootapp.data.db.fakedata.createSearchData
import com.emanh.rootapp.data.db.fakedata.crossref.fakeCrossRefAlbumArtistData
import com.emanh.rootapp.data.db.fakedata.crossref.fakeCrossRefAlbumSongData
import com.emanh.rootapp.data.db.fakedata.crossref.fakeCrossRefPlaylistSongData
import com.emanh.rootapp.data.db.fakedata.crossref.fakeCrossRefSongArtistData
import com.emanh.rootapp.data.db.fakedata.fakeAlbumsData
import com.emanh.rootapp.data.db.fakedata.crossref.fakeCrossRefSongGenreData
import com.emanh.rootapp.data.db.fakedata.fakeGenresData
import com.emanh.rootapp.data.db.fakedata.fakePlaylistsData
import com.emanh.rootapp.data.db.fakedata.fakeSongsData
import com.emanh.rootapp.data.db.fakedata.fakeUsersData
import com.emanh.rootapp.utils.withNormalizedAlbums
import com.emanh.rootapp.utils.withNormalizedPlaylists
import com.emanh.rootapp.utils.withNormalizedSongs
import com.emanh.rootapp.utils.withNormalizedUsers
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
    private val genresDao: GenresDao,
    private val songsDao: SongsDao,
    private val usersDao: UsersDao,
    private val albumsDao: AlbumsDao,
    private val playlistsDao: PlaylistsDao,
    private val searchDao: SearchDao,
    private val crossRefSongDao: CrossRefSongDao,
    private val crossRefPlaylistDao: CrossRefPlaylistDao,
    private val crossRefAlbumDao: CrossRefAlbumDao
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
                val playlitsList = withTimeout(5000) {
                    playlistsDao.getAllPlaylists().first()
                }
                val searchList = withTimeout(5000) {
                    searchDao.getAllSearch().first()
                }
                val crossRefSongList = withTimeout(5000) {
                    crossRefSongDao.getAllCrossRefSongs().first()
                }
                val crossRefPlaylistList = withTimeout(5000) {
                    crossRefPlaylistDao.getAllCrossRefPlaylists().first()
                }
                val crossRefAlbumList = withTimeout(5000) {
                    crossRefAlbumDao.getAllCrossRefAlbums().first()
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
                        val rawSongs = fakeSongsData()
                        val normalizedSongs = rawSongs.withNormalizedSongs()
                        songsDao.insertAllSongs(normalizedSongs)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert songs data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (usersList.isEmpty()) {
                    Log.d(TAG, "Inserting users data: ${fakeUsersData().size} items")
                    try {
                        val rawUsers = fakeUsersData()
                        val normalizedUsers = rawUsers.withNormalizedUsers()
                        usersDao.insertAllUsers(normalizedUsers)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert users data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (albumsList.isEmpty()) {
                    Log.d(TAG, "Inserting albums data: ${fakeAlbumsData().size} items")
                    try {
                        val rawAlbums = fakeAlbumsData()
                        val normalizedAlbums = rawAlbums.withNormalizedAlbums()
                        albumsDao.insertAllAlbums(normalizedAlbums)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert albums data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (playlitsList.isEmpty()) {
                    Log.d(TAG, "Inserting playlists data: ${fakePlaylistsData().size} items")
                    try {
                        val rawPlaylists = fakePlaylistsData()
                        val normalizedPlaylists = rawPlaylists.withNormalizedPlaylists()
                        playlistsDao.insertAllPlaylists(normalizedPlaylists)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert playlists data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (searchList.isEmpty()) {
                    Log.d(TAG, "Creating search data from database")
                    try {
                        val searchData = createSearchData(songsDao.getAllSongs(),
                                                          usersDao.getAllUsers(),
                                                          albumsDao.getAllAlbums(),
                                                          playlistsDao.getAllPlaylists()).first()

                        Log.d(TAG, "Inserting search data: ${searchData.size} items")
                        searchDao.insertAllSearch(searchData)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert search data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (crossRefSongList.isEmpty()) {
                    Log.d(TAG, "Inserting crossRefSongGenre data: ${fakeCrossRefSongGenreData().size} items")
                    Log.d(TAG, "Inserting crossRefSongArtist data: ${fakeCrossRefSongArtistData().size} items")
                    try {
                        crossRefSongDao.insertAllCrossRefSongGenre(fakeCrossRefSongGenreData())
                        crossRefSongDao.insertAllCrossRefSongArtist(fakeCrossRefSongArtistData())
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert crossRefSong data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (crossRefPlaylistList.isEmpty()) {
                    Log.d(TAG, "Inserting crossRefPlaylistSongDaoList data: ${fakeCrossRefPlaylistSongData().size} items")
                    try {
                        crossRefPlaylistDao.insertAllCrossRefPlaylistSong(fakeCrossRefPlaylistSongData())
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert crossRefPlaylist data: $e")
                        _isDatabaseInitialized.value = false
                        return@launch
                    }
                }

                if (crossRefAlbumList.isEmpty()) {
                    Log.d(TAG, "Inserting crossRefAlbumSongDaoList data: ${fakeCrossRefAlbumSongData().size} items")
                    Log.d(TAG, "Inserting crossRefAlbumArtistDaoList data: ${fakeCrossRefAlbumArtistData().size} items")
                    try {
                        crossRefAlbumDao.insertAllCrossRefAlbumSong(fakeCrossRefAlbumSongData())
                        crossRefAlbumDao.insertAllCrossRefAlbumArtist(fakeCrossRefAlbumArtistData())
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to insert crossRefAlbum data: $e")
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