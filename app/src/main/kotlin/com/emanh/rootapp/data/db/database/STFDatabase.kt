package com.emanh.rootapp.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emanh.rootapp.data.db.converter.Converters
import com.emanh.rootapp.data.db.dao.AlbumsDao
import com.emanh.rootapp.data.db.dao.crossref.SongGenreDao
import com.emanh.rootapp.data.db.dao.GenresDao
import com.emanh.rootapp.data.db.dao.PlaylistsDao
import com.emanh.rootapp.data.db.dao.PodcastsDao
import com.emanh.rootapp.data.db.dao.SongsDao
import com.emanh.rootapp.data.db.dao.UsersDao
import com.emanh.rootapp.data.db.dao.ViewsSongDao
import com.emanh.rootapp.data.db.entity.AlbumsEntity
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.data.db.entity.GenresEntity
import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import com.emanh.rootapp.data.db.entity.PodcastsEntity
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.data.db.entity.ViewsSongEntity

@Database(entities = [AlbumsEntity::class, GenresEntity::class, PlaylistsEntity::class, PodcastsEntity::class, SongsEntity::class, UsersEntity::class, ViewsSongEntity::class, SongGenreEntity::class],
          version = 1,
          exportSchema = false)
@TypeConverters(Converters::class)
abstract class STFDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun genresDao(): GenresDao
    abstract fun playlistsDao(): PlaylistsDao
    abstract fun podcastsDao(): PodcastsDao
    abstract fun songsDao(): SongsDao
    abstract fun usersDao(): UsersDao
    abstract fun viewsSongDao(): ViewsSongDao
    abstract fun crossRefSongGenreDao(): SongGenreDao
}