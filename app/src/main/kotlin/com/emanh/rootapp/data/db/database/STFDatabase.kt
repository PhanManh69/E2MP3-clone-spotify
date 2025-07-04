package com.emanh.rootapp.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emanh.rootapp.data.db.converter.Converters
import com.emanh.rootapp.data.db.dao.AlbumsDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefSongDao
import com.emanh.rootapp.data.db.dao.GenresDao
import com.emanh.rootapp.data.db.dao.PlaylistsDao
import com.emanh.rootapp.data.db.dao.PodcastsDao
import com.emanh.rootapp.data.db.dao.SearchDao
import com.emanh.rootapp.data.db.dao.SearchHistoryDao
import com.emanh.rootapp.data.db.dao.SongsDao
import com.emanh.rootapp.data.db.dao.UploadDao
import com.emanh.rootapp.data.db.dao.UsersDao
import com.emanh.rootapp.data.db.dao.ViewsSongDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefAlbumDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefPlaylistDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefUserDao
import com.emanh.rootapp.data.db.entity.AlbumsEntity
import com.emanh.rootapp.data.db.entity.crossref.SongGenreEntity
import com.emanh.rootapp.data.db.entity.GenresEntity
import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import com.emanh.rootapp.data.db.entity.PodcastsEntity
import com.emanh.rootapp.data.db.entity.SearchEntity
import com.emanh.rootapp.data.db.entity.SearchHistoryEntity
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.UploadEntity
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.data.db.entity.ViewsSongEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.AlbumSongEntity
import com.emanh.rootapp.data.db.entity.crossref.PlaylistLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.PlaylistSongEntity
import com.emanh.rootapp.data.db.entity.crossref.SongArtistEntity
import com.emanh.rootapp.data.db.entity.crossref.SongLikeEntity
import com.emanh.rootapp.data.db.entity.crossref.UserFollowingEntity

@Database(entities = [AlbumsEntity::class, GenresEntity::class, PlaylistsEntity::class, PodcastsEntity::class, SongsEntity::class, UsersEntity::class, ViewsSongEntity::class, SearchEntity::class, SongGenreEntity::class, SongArtistEntity::class, UploadEntity::class, SongLikeEntity::class, UserFollowingEntity::class, PlaylistSongEntity::class, AlbumSongEntity::class, AlbumArtistEntity::class, SearchHistoryEntity::class, PlaylistLikeEntity::class, AlbumLikeEntity::class],
          version = 1,
          exportSchema = false)
@TypeConverters(Converters::class)
abstract class STFDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun genresDao(): GenresDao
    abstract fun playlistsDao(): PlaylistsDao
    abstract fun podcastsDao(): PodcastsDao
    abstract fun songsDao(): SongsDao
    abstract fun uploadDao(): UploadDao
    abstract fun usersDao(): UsersDao
    abstract fun viewsSongDao(): ViewsSongDao
    abstract fun searchDao(): SearchDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun crossRefSongDao(): CrossRefSongDao
    abstract fun crossRefPlaylistDao(): CrossRefPlaylistDao
    abstract fun crossRefAlbumDao(): CrossRefAlbumDao
    abstract fun crossRefUserDao(): CrossRefUserDao
}