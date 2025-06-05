package com.emanh.rootapp.di

import android.content.Context
import androidx.room.Room
import com.emanh.rootapp.data.datasource.AlbumsDataSource
import com.emanh.rootapp.data.datasource.AlbumsDataSourceImpl
import com.emanh.rootapp.data.datasource.crossref.CrossRefSongDataSource
import com.emanh.rootapp.data.datasource.crossref.CrossRefSongDataSourceImpl
import com.emanh.rootapp.data.datasource.GenresDataSource
import com.emanh.rootapp.data.datasource.GenresDataSourceImpl
import com.emanh.rootapp.data.datasource.MusixmatchDataSource
import com.emanh.rootapp.data.datasource.MusixmatchDataSourceImpl
import com.emanh.rootapp.data.datasource.PlaylistsDataSource
import com.emanh.rootapp.data.datasource.PlaylistsDataSourceImpl
import com.emanh.rootapp.data.datasource.SearchDataSource
import com.emanh.rootapp.data.datasource.SearchDataSourceImpl
import com.emanh.rootapp.data.datasource.SearchHistoryDataSource
import com.emanh.rootapp.data.datasource.SearchHistoryDataSourceImpl
import com.emanh.rootapp.data.datasource.SongsDataSource
import com.emanh.rootapp.data.datasource.SongsDataSourceImpl
import com.emanh.rootapp.data.datasource.UserDataStore
import com.emanh.rootapp.data.datasource.UserDataStoreImpl
import com.emanh.rootapp.data.datasource.UsersDataSource
import com.emanh.rootapp.data.datasource.UsersDataSourceImpl
import com.emanh.rootapp.data.datasource.ViewsSongDataSource
import com.emanh.rootapp.data.datasource.ViewsSongDataSourceImpl
import com.emanh.rootapp.data.datasource.crossref.CrossRefAlbumDataSource
import com.emanh.rootapp.data.datasource.crossref.CrossRefAlbumDataSourceImpl
import com.emanh.rootapp.data.datasource.crossref.CrossRefPlaylistDataSource
import com.emanh.rootapp.data.datasource.crossref.CrossRefPlaylistDataSourceImpl
import com.emanh.rootapp.data.datasource.crossref.CrossRefUserDataSource
import com.emanh.rootapp.data.datasource.crossref.CrossRefUserDataSourceImpl
import com.emanh.rootapp.data.db.dao.AlbumsDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefSongDao
import com.emanh.rootapp.data.db.dao.GenresDao
import com.emanh.rootapp.data.db.dao.PlaylistsDao
import com.emanh.rootapp.data.db.dao.PodcastsDao
import com.emanh.rootapp.data.db.dao.SearchDao
import com.emanh.rootapp.data.db.dao.SearchHistoryDao
import com.emanh.rootapp.data.db.dao.SongsDao
import com.emanh.rootapp.data.db.dao.UsersDao
import com.emanh.rootapp.data.db.dao.ViewsSongDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefAlbumDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefPlaylistDao
import com.emanh.rootapp.data.db.dao.crossref.CrossRefUserDao
import com.emanh.rootapp.data.db.database.STFDatabase
import com.emanh.rootapp.data.db.initializer.DatabaseInitializer
import com.emanh.rootapp.data.db.service.MusixmatchService
import com.emanh.rootapp.data.repository.AlbumsRepositoryImpl
import com.emanh.rootapp.data.repository.crossref.CrossRefSongRepositoryImpl
import com.emanh.rootapp.data.repository.GenresRepositoryImpl
import com.emanh.rootapp.data.repository.MusixmatchRepositoryImpl
import com.emanh.rootapp.data.repository.PlaylistsRepositoryImpl
import com.emanh.rootapp.data.repository.SearchHistoryRepositoryImpl
import com.emanh.rootapp.data.repository.SearchRepositoryImpl
import com.emanh.rootapp.data.repository.SongsRepositoryImpl
import com.emanh.rootapp.data.repository.UserSessionRepositoryImpl
import com.emanh.rootapp.data.repository.UsersRepositoryImpl
import com.emanh.rootapp.data.repository.ViewsSongRepositoryImpl
import com.emanh.rootapp.data.repository.crossref.CrossRefAlbumRepositoryImpl
import com.emanh.rootapp.data.repository.crossref.CrossRefPlaylistRepositoryImpl
import com.emanh.rootapp.data.repository.crossref.CrossRefUserRepositoryImpl
import com.emanh.rootapp.domain.repository.AlbumsRepository
import com.emanh.rootapp.domain.repository.crossref.CrossRefSongRepository
import com.emanh.rootapp.domain.repository.GenresRepository
import com.emanh.rootapp.domain.repository.MusixmatchRepository
import com.emanh.rootapp.domain.repository.PlaylistsRepository
import com.emanh.rootapp.domain.repository.SearchHistoryRepository
import com.emanh.rootapp.domain.repository.SearchRepository
import com.emanh.rootapp.domain.repository.SongsRepository
import com.emanh.rootapp.domain.repository.UserSessionRepository
import com.emanh.rootapp.domain.repository.UsersRepository
import com.emanh.rootapp.domain.repository.ViewsSongRepository
import com.emanh.rootapp.domain.repository.crossref.CrossRefAlbumRepository
import com.emanh.rootapp.domain.repository.crossref.CrossRefPlaylistRepository
import com.emanh.rootapp.domain.repository.crossref.CrossRefUserRepository
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
    fun provideSearch(database: STFDatabase): SearchDao {
        return database.searchDao()
    }

    @Provides
    @Singleton
    fun provideSearchHistory(database: STFDatabase): SearchHistoryDao {
        return database.searchHistoryDao()
    }

    @Provides
    @Singleton
    fun provideCrossRefSongDao(database: STFDatabase): CrossRefSongDao {
        return database.crossRefSongDao()
    }

    @Provides
    @Singleton
    fun provideCrossRefPlaylistDao(database: STFDatabase): CrossRefPlaylistDao {
        return database.crossRefPlaylistDao()
    }

    @Provides
    @Singleton
    fun provideCrossRefAlbumDao(database: STFDatabase): CrossRefAlbumDao {
        return database.crossRefAlbumDao()
    }

    @Provides
    @Singleton
    fun provideCrossRefUserDao(database: STFDatabase): CrossRefUserDao {
        return database.crossRefUserDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseInitializer(
        genresDao: GenresDao,
        songsDao: SongsDao,
        usersDao: UsersDao,
        albumsDao: AlbumsDao,
        palylistsDao: PlaylistsDao,
        searchDao: SearchDao,
        crossRefSongDao: CrossRefSongDao,
        crossRefPlaylistDao: CrossRefPlaylistDao,
        crossRefAlbumDao: CrossRefAlbumDao
    ): DatabaseInitializer {
        return DatabaseInitializer(genresDao,
                                   songsDao,
                                   usersDao,
                                   albumsDao,
                                   palylistsDao,
                                   searchDao,
                                   crossRefSongDao,
                                   crossRefPlaylistDao,
                                   crossRefAlbumDao)
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
    fun providePlaylistsDataSource(playlistsDao: PlaylistsDao): PlaylistsDataSource {
        return PlaylistsDataSourceImpl(playlistsDao)
    }

    @Provides
    @Singleton
    fun providePlaylistsRepository(playlistsDataSource: PlaylistsDataSource): PlaylistsRepository {
        return PlaylistsRepositoryImpl(playlistsDataSource)
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
    fun provideSearchDataSource(searchDao: SearchDao): SearchDataSource {
        return SearchDataSourceImpl(searchDao)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(searchDataSource: SearchDataSource): SearchRepository {
        return SearchRepositoryImpl(searchDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchHistoryDataSource(searchHistoryDao: SearchHistoryDao): SearchHistoryDataSource {
        return SearchHistoryDataSourceImpl(searchHistoryDao)
    }

    @Provides
    @Singleton
    fun provideSearchHistoryRepository(searchHistoryDataSource: SearchHistoryDataSource): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(searchHistoryDataSource)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(@ApplicationContext context: Context): UserDataStore {
        return UserDataStoreImpl(context)
    }

    @Provides
    @Singleton
    fun provideUserSessionRepository(userDataStore: UserDataStore): UserSessionRepository {
        return UserSessionRepositoryImpl(userDataStore)
    }

    @Provides
    @Singleton
    fun provideCrossRefSongGenreDataSource(crossRefCrossRefSongDao: CrossRefSongDao): CrossRefSongDataSource {
        return CrossRefSongDataSourceImpl(crossRefCrossRefSongDao)
    }

    @Provides
    @Singleton
    fun provideCrossRefSongGenreRepository(crossRefSongDataSource: CrossRefSongDataSource): CrossRefSongRepository {
        return CrossRefSongRepositoryImpl(crossRefSongDataSource)
    }

    @Provides
    @Singleton
    fun provideCrossRefPlaylistSongDataSource(crossRefCrossRefPlaylistDao: CrossRefPlaylistDao): CrossRefPlaylistDataSource {
        return CrossRefPlaylistDataSourceImpl(crossRefCrossRefPlaylistDao)
    }

    @Provides
    @Singleton
    fun provideCrossRefPlaylistSongRepository(crossRefPlaylistDataSource: CrossRefPlaylistDataSource): CrossRefPlaylistRepository {
        return CrossRefPlaylistRepositoryImpl(crossRefPlaylistDataSource)
    }

    @Provides
    @Singleton
    fun provideCrossRefAlbumSongDataSource(crossRefCrossRefAlbumDao: CrossRefAlbumDao): CrossRefAlbumDataSource {
        return CrossRefAlbumDataSourceImpl(crossRefCrossRefAlbumDao)
    }

    @Provides
    @Singleton
    fun provideCrossRefAlbumSongRepository(crossRefAlbumDataSource: CrossRefAlbumDataSource): CrossRefAlbumRepository {
        return CrossRefAlbumRepositoryImpl(crossRefAlbumDataSource)
    }

    @Provides
    @Singleton
    fun provideCrossRefUserDataSource(crossRefCrossRefUserDao: CrossRefUserDao): CrossRefUserDataSource {
        return CrossRefUserDataSourceImpl(crossRefCrossRefUserDao)
    }

    @Provides
    @Singleton
    fun provideCrossRefUserRepository(crossRefUserDataSource: CrossRefUserDataSource): CrossRefUserRepository {
        return CrossRefUserRepositoryImpl(crossRefUserDataSource)
    }

    @Provides
    @Singleton
    fun provideMusixmatchDataSource(musixmatchService: MusixmatchService): MusixmatchDataSource {
        return MusixmatchDataSourceImpl(musixmatchService)
    }

    @Provides
    @Singleton
    fun provideMusixmatchRepository(musixmatchDataSource: MusixmatchDataSource): MusixmatchRepository {
        return MusixmatchRepositoryImpl(musixmatchDataSource)
    }
}