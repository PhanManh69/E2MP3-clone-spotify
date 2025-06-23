package com.emanh.rootapp.network.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.room.Room
import com.emanh.rootapp.data.db.database.STFDatabase
import kotlinx.coroutines.runBlocking

class MyContentProvider : ContentProvider() {

    private lateinit var database: STFDatabase

    companion object {
        const val AUTHORITY = "com.emanh.e2mp3.spotify"
        const val PATH_SONGS = "songs"
        const val PATH_SONGS_UPDATE = "songs_update"
        const val PATH_ALL_SONGS = "songs/all"
        const val PATH_PROCESSING_SONGS = "songs/processing"
        const val PATH_ARTIST = "artist"
        const val SONG_ID = 1
        const val UPDATE_SONG_ID = 1
        const val ALL_SONGS = 1
        const val PROCESSING_SONGS = 2
        const val ARTIST = 3

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "$PATH_SONGS/#", SONG_ID)
            addURI(AUTHORITY, "$PATH_SONGS_UPDATE/#", UPDATE_SONG_ID)
            addURI(AUTHORITY, PATH_ALL_SONGS, ALL_SONGS)
            addURI(AUTHORITY, PATH_PROCESSING_SONGS, PROCESSING_SONGS)
            addURI(AUTHORITY, PATH_ARTIST, ARTIST)
        }
    }

    override fun onCreate(): Boolean {
        database = Room.databaseBuilder(context!!, STFDatabase::class.java, "e2mp3_db").build()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<out String?>?, selection: String?, selectionArgs: Array<out String?>?, sortOrder: String?
    ): Cursor? {
        return try {
            when (uriMatcher.match(uri)) {
                ALL_SONGS -> database.songsDao().getAllSongsCursor()
                PROCESSING_SONGS -> database.songsDao().getProcessingSongsCursor()
                ARTIST -> database.usersDao().getAllArtistCursor()
                else -> null
            }
        } catch (e: Exception) {
            Log.e("MyContentProvider", "Error in query", e)
            null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String?>?): Int {
        return when (uriMatcher.match(uri)) {
            SONG_ID -> {
                val id = uri.lastPathSegment?.toLongOrNull() ?: return 0

                val deletedCount = database.songsDao().deleteSongById(id)

                if (deletedCount > 0) {
                    try {
                        database.searchDao().deleteSongFromSearch(id)
                    } catch (e: Exception) {
                        Log.e("MyContentProvider", "Error in delete search", e)
                    }

                    try {
                        database.searchHistoryDao().deleteSongFromSearchHistory(id)
                    } catch (e: Exception) {
                        Log.e("MyContentProvider", "Error in delete search history", e)
                    }

                    try {
                        database.viewsSongDao().deleteSongFromView(id)
                    } catch (e: Exception) {
                        Log.e("MyContentProvider", "Error in delete views song", e)
                    }

                    try {
                        database.crossRefAlbumDao().deleteSongFromAlbum(id)
                    } catch (e: Exception) {
                        Log.e("MyContentProvider", "Error in delete album", e)
                    }

                    try {
                        database.crossRefPlaylistDao().deleteSongFromPlaylist(id)
                    } catch (e: Exception) {
                        Log.e("MyContentProvider", "Error in delete playlist", e)
                    }
                    try {
                        database.crossRefSongDao().deleteSongFromArtist(id)
                    } catch (e: Exception) {
                        Log.e("MyContentProvider", "Error in delete artist", e)
                    }

                    try {
                        database.crossRefSongDao().deleteSongFromGenre(id)
                    } catch (e: Exception) {
                        Log.e("MyContentProvider", "Error in delete genre", e)
                    }

                    try {
                        database.crossRefSongDao().deleteSongFromLike(id)
                    } catch (e: Exception) {
                        Log.e("MyContentProvider", "Error in delete like", e)
                    }

                    context?.contentResolver?.notifyChange(uri, null)
                }

                deletedCount
            }

            else -> 0
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String?>?
    ): Int {
        return when (uriMatcher.match(uri)) {
            UPDATE_SONG_ID -> {
                val id = uri.lastPathSegment?.toLongOrNull() ?: return 0
                val newStatus = values?.getAsString("status_upload") ?: return 0

                runBlocking {
                    database.songsDao().updateStatusUpload(id, newStatus)
                }
            }

            else -> 0
        }
    }
}