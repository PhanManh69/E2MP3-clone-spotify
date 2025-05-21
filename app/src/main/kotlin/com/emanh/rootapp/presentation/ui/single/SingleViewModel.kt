package com.emanh.rootapp.presentation.ui.single

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefSongUseCase
import com.emanh.rootapp.presentation.navigation.ArtistScreenNavigation
import com.emanh.rootapp.presentation.navigation.SingleScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SingleViewModel"

@HiltViewModel
class SingleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRouter: AppRouter,
    private val songUseCase: SongsUseCase,
    private val crossRefSongUseCase: CrossRefSongUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SingleUiState())
    val uiState: StateFlow<SingleUiState> = _uiState.asStateFlow()

    private val songId: Int = savedStateHandle.get<Int>("singleId") ?: -1

    init {
        if (songId != -1) {
            Log.d(TAG, "Initializing with singleId: $songId")

            loadSingleDetails(songId)
            loadMoreSongsByArtists(songId)
        }
    }

    fun onBackClick() {
        appRouter.getNavController()?.goBack()
    }

    fun goToArtist(artistId: Int) {
        appRouter.getNavController()?.navigateTo(ArtistScreenNavigation.getRoute(artistId))
    }

    fun goToSingle(singleId: Int) {
        appRouter.getNavController()?.navigateTo(SingleScreenNavigation.getRoute(singleId))
    }

    fun totalTime(song: SongsModel): String {
        val time = song.timeline ?: "00:00:00"
        val parts = time.split(":")
        val formatted = "${parts[1]}min${parts[2]}s"

        return formatted
    }

    private fun loadSingleDetails(songId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val single = crossRefSongUseCase.getSongDetailsById(songId).first()
                val song = single.songs
                val artistsList = single.artistsList
                val artistsMap = artistsList.associateBy { it.userId }
                val sortedArtists = song.artistsIdList?.mapNotNull { artistId ->
                    artistsMap[artistId]?.let { artist ->
                        UsersModel(id = artist.userId,
                                   isArtist = artist.isArtist,
                                   username = artist.username,
                                   email = artist.email,
                                   password = artist.password,
                                   avatarUrl = artist.avatarUrl,
                                   name = artist.name,
                                   followers = artist.followers,
                                   following = artist.followingIdList)
                    }
                }

                _uiState.update { currentState ->
                    currentState.copy(single = SongsModel(id = song.songId,
                                                          avatarUrl = song.avatarUrl,
                                                          songUrl = song.songUrl,
                                                          title = song.title,
                                                          subtitle = song.subtitle,
                                                          timeline = song.timeline,
                                                          releaseDate = song.releaseDate,
                                                          genres = song.genresIdList,
                                                          likes = song.likesIdList,
                                                          artists = song.artistsIdList), artistList = sortedArtists.orEmpty(), isLoading = false)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading playlist: ${e.message}")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun loadMoreSongsByArtists(songId: Int) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            songUseCase.getMoreByArtists(songId).catch { error ->
                Log.e(TAG, "Error fetching MoreSongsByArtists: $error")
                _uiState.update { it.copy(isLoading = false) }
                emit(emptyList())
            }.collect { songsList ->
                _uiState.update { currentState ->
                    currentState.copy(moreByArtists = songsList, isLoading = false)
                }
            }
        }
    }
}