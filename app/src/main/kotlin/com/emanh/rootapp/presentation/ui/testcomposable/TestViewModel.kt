package com.emanh.rootapp.presentation.ui.testcomposable

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.AlbumsUseCase
import com.emanh.rootapp.domain.usecase.GenresUseCase
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.domain.usecase.crossref.SongGenreUseCase
import com.emanh.rootapp.presentation.ui.home.HomeAlbumsData
import com.emanh.rootapp.presentation.ui.home.HomeGenreData
import com.emanh.rootapp.presentation.ui.home.HomeSongsData
import com.emanh.rootapp.presentation.ui.home.HomeUsersData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val TAG = "TestViewModel"

@HiltViewModel
class TestViewModel @Inject constructor(
    private val genresUseCase: GenresUseCase,
    private val songsUseCase: SongsUseCase,
    private val usersUseCase: UsersUseCase,
    private val albumsUseCase: AlbumsUseCase,
    private val songGenreUseCase: SongGenreUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TestUiState())
    val uiState: StateFlow<TestUiState> = _uiState.asStateFlow()

    init {
        getAllGenres()
        getGenreById()
        getAllSongs()
        getAllUsers()
        getAllAlbums()
        getAllSongsWithGenres()
    }

    private fun getAllSongsWithGenres() {
        songGenreUseCase.getAllSongsWithGenres().onEach { songGenreList ->
            Log.d(TAG, "Fetched $songGenreList songGenre")
        }.catch { error ->
            Log.e(TAG, "Error fetching songGenre: $error")
        }.launchIn(viewModelScope)
    }

    private fun getAllGenres() {
        genresUseCase.getAllGenres().onEach { genresList ->
            _uiState.update { currentState ->
                currentState.copy(genres = genresList.map { genre ->
                    HomeGenreData(id = genre.id, nameId = genre.nameId ?: 0)
                })
            }
            Log.d(TAG, "Fetched ${genresList.size} genres")
        }.catch { error ->
            Log.e(TAG, "Error fetching genres: $error")
        }.launchIn(viewModelScope)
    }

    private fun getGenreById() {
        val genreId = 1
        genresUseCase.getGenreById(genreId).onEach { genre ->
            Log.d(TAG, "Genre with ID $genreId: name=${genre.nameId}, date=${System.currentTimeMillis()}")
        }.catch { error ->
            Log.e(TAG, "Error fetching genre with ID $genreId")
        }.launchIn(viewModelScope)
    }

    private fun getAllSongs() {
        songsUseCase.getAllSongs().onEach { songsList ->
            _uiState.update { currentState ->
                currentState.copy(songs = songsList.map { song ->
                    HomeSongsData(id = song.id,
                                  avatarUrl = song.avatarUrl ?: "",
                                  songUrl = song.songUrl ?: "",
                                  title = song.title ?: "",
                                  subtitle = song.subtitle ?: "",
                                  timeline = song.timeline ?: "",
                                  releaseDate = song.releaseDate ?: "",
                                  genres = song.genres ?: emptyList(),
                                  likes = song.likes ?: emptyList(),
                                  artists = song.artists ?: emptyList())
                })
            }
            Log.d("HomeViewModel", "Fetched ${songsList.size} songs")
        }.catch { error ->
            Log.e(TAG, "Error fetching songs: $error")
        }.launchIn(viewModelScope)
    }

    private fun getAllUsers() {
        usersUseCase.getAllUsers().onEach { usersList ->
            _uiState.update { currentState ->
                currentState.copy(users = usersList.map { user ->
                    HomeUsersData(id = user.id,
                                  isArtist = user.isArtist,
                                  username = user.username ?: "",
                                  email = user.email ?: "",
                                  password = user.password ?: "",
                                  avatarUrl = user.avatarUrl ?: "",
                                  name = user.name ?: "",
                                  followers = user.followers ?: 0,
                                  following = user.following ?: emptyList())
                })
            }
            Log.d(TAG, "Fetched ${usersList.size} users")
        }.catch { error ->
            Log.e(TAG, "Error fetching users: $error")
        }.launchIn(viewModelScope)
    }

    private fun getAllAlbums() {
        albumsUseCase.getAllAlbums().onEach { albumsList ->
            _uiState.update { currentState ->
                currentState.copy(albums = albumsList.map { album ->
                    HomeAlbumsData(id = album.id,
                                   avatarUrl = album.avatarUrl ?: "",
                                   title = album.albumType ?: "",
                                   subtitle = album.subtitle ?: "",
                                   albumType = album.albumType ?: "",
                                   releaseDate = album.releaseDate ?: "",
                                   artist = album.artist ?: emptyList(),
                                   songs = album.songs ?: emptyList())
                })
            }
            Log.d(TAG, "Fetched ${albumsList.size} albums")
        }.catch { error ->
            Log.e(TAG, "Error fetching albums: $error")
        }.launchIn(viewModelScope)
    }
}