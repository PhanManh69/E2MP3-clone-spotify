package com.emanh.rootapp.di

import android.content.Context
import androidx.room.Room
import com.emanh.rootapp.data.datasource.AlbumsDataSource
import com.emanh.rootapp.data.datasource.AlbumsDataSourceImpl
import com.emanh.rootapp.data.datasource.crossref.SongGenreDataSource
import com.emanh.rootapp.data.datasource.crossref.SongGenreDataSourceImpl
import com.emanh.rootapp.data.datasource.GenresDataSource
import com.emanh.rootapp.data.datasource.GenresDataSourceImpl
import com.emanh.rootapp.data.datasource.SongsDataSource
import com.emanh.rootapp.data.datasource.SongsDataSourceImpl
import com.emanh.rootapp.data.datasource.UsersDataSource
import com.emanh.rootapp.data.datasource.UsersDataSourceImpl
import com.emanh.rootapp.data.datasource.ViewsSongDataSource
import com.emanh.rootapp.data.datasource.ViewsSongDataSourceImpl
import com.emanh.rootapp.data.db.dao.AlbumsDao
import com.emanh.rootapp.data.db.dao.crossref.SongGenreDao
import com.emanh.rootapp.data.db.dao.GenresDao
import com.emanh.rootapp.data.db.dao.PlaylistsDao
import com.emanh.rootapp.data.db.dao.PodcastsDao
import com.emanh.rootapp.data.db.dao.SongsDao
import com.emanh.rootapp.data.db.dao.UsersDao
import com.emanh.rootapp.data.db.dao.ViewsSongDao
import com.emanh.rootapp.data.db.database.STFDatabase
import com.emanh.rootapp.data.db.initializer.DatabaseInitializer
import com.emanh.rootapp.data.repository.AlbumsRepositoryImpl
import com.emanh.rootapp.data.repository.crossref.SongGenreRepositoryImpl
import com.emanh.rootapp.data.repository.GenresRepositoryImpl
import com.emanh.rootapp.data.repository.SongsRepositoryImpl
import com.emanh.rootapp.data.repository.UsersRepositoryImpl
import com.emanh.rootapp.data.repository.ViewsSongRepositoryImpl
import com.emanh.rootapp.domain.repository.AlbumsRepository
import com.emanh.rootapp.domain.repository.crossref.SongGenreRepository
import com.emanh.rootapp.domain.repository.GenresRepository
import com.emanh.rootapp.domain.repository.SongsRepository
import com.emanh.rootapp.domain.repository.UsersRepository
import com.emanh.rootapp.domain.repository.ViewsSongRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideSTFDatabase(@ApplicationContext context: Context): STFDatabase {
        return Room.databaseBuilder(context, STFDatabase::class.java, "e2mp3_db").fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideAlbumsDao(database: STFDatabase): AlbumsDao {
        return database.albumsDao()
    }

    @Provides
    @Singleton
    fun provideGenresDao(database: STFDatabase): GenresDao {
        return database.genresDao()
    }

    @Provides
    @Singleton
    fun providePlaylistDao(database: STFDatabase): PlaylistsDao {
        return database.playlistsDao()
    }

    @Provides
    @Singleton
    fun providePodcastDao(database: STFDatabase): PodcastsDao {
        return database.podcastsDao()
    }

    @Provides
    @Singleton
    fun provideSongsDao(database: STFDatabase): SongsDao {
        return database.songsDao()
    }

    @Provides
    @Singleton
    fun provideUsersDao(database: STFDatabase): UsersDao {
        return database.usersDao()
    }

    @Provides
    @Singleton
    fun provideViewsDao(database: STFDatabase): ViewsSongDao {
        return database.viewsSongDao()
    }

    @Provides
    @Singleton
    fun provideCrossRefSongGenreDao(database: STFDatabase): SongGenreDao {
        return database.crossRefSongGenreDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseInitializer(
        genresDao: GenresDao,
        songsDao: SongsDao,
        usersDao: UsersDao,
        albumsDao: AlbumsDao,
        crossRefSongGenreDao: SongGenreDao
    ): DatabaseInitializer {
        return DatabaseInitializer(genresDao, songsDao, usersDao, albumsDao, crossRefSongGenreDao)
    }

    @Provides
    @Singleton
    fun provideGenresDataSource(genresDao: GenresDao): GenresDataSource {
        return GenresDataSourceImpl(genresDao)
    }

    @Provides
    @Singleton
    fun provideGenresRepository(genresDataSource: GenresDataSource): GenresRepository {
        return GenresRepositoryImpl(genresDataSource)
    }

    @Provides
    @Singleton
    fun provideSongsDataSource(songsDao: SongsDao): SongsDataSource {
        return SongsDataSourceImpl(songsDao)
    }

    @Provides
    @Singleton
    fun provideSongsRepository(songsDataSource: SongsDataSource): SongsRepository {
        return SongsRepositoryImpl(songsDataSource)
    }

    @Provides
    @Singleton
    fun provideUsersDataSource(usersDao: UsersDao): UsersDataSource {
        return UsersDataSourceImpl(usersDao)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(usersDataSource: UsersDataSource): UsersRepository {
        return UsersRepositoryImpl(usersDataSource)
    }

    @Provides
    @Singleton
    fun provideAlbumsDataSource(albumsDao: AlbumsDao): AlbumsDataSource {
        return AlbumsDataSourceImpl(albumsDao)
    }

    @Provides
    @Singleton
    fun provideAlbumsRepository(albumsDataSource: AlbumsDataSource): AlbumsRepository {
        return AlbumsRepositoryImpl(albumsDataSource)
    }

    @Provides
    @Singleton
    fun provideViewsSongDataSource(viewsSongDao: ViewsSongDao): ViewsSongDataSource {
        return ViewsSongDataSourceImpl(viewsSongDao)
    }

    @Provides
    @Singleton
    fun provideViewsSongRepository(viewsSongDataSource: ViewsSongDataSource): ViewsSongRepository {
        return ViewsSongRepositoryImpl(viewsSongDataSource)
    }

    @Provides
    @Singleton
    fun provideCrossRefSongGenreDataSource(crossRefSongGenreDao: SongGenreDao): SongGenreDataSource {
        return SongGenreDataSourceImpl(crossRefSongGenreDao)
    }

    @Provides
    @Singleton
    fun provideCrossRefSongGenreRepository(songGenreDataSource: SongGenreDataSource): SongGenreRepository {
        return SongGenreRepositoryImpl(songGenreDataSource)
    }
}