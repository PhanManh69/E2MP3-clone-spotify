package com.emanh.rootapp.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.ViewsSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val songsUseCase: SongsUseCase, private val viewsSongUseCase: ViewsSongUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getRecommendedSongs()
        getRecentlyListenedSongs()
    }

    private fun getRecommendedSongs() {
        val userId = 1
        songsUseCase.getRecommendedSongs(userId).onEach { songsList ->
            _uiState.update { currentState ->
                currentState.copy(recommendedSongs = songsList.map { song ->
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
            Log.d("HomeViewModel", "Fetched Recommended ${songsList.size} songs")
        }.catch { error ->
            Log.e(TAG, "Error fetching songs: $error")
        }.launchIn(viewModelScope)
    }

    private fun getRecentlyListenedSongs() {
        val userId = 1
        songsUseCase.getRecentlyListenedSongs(userId).onEach { songsList ->
            _uiState.update { currentState ->
                currentState.copy(recentlyListenedSongs = songsList.map { song ->
                    with(song) {
                        HomeSongsData(id = id,
                                      avatarUrl = avatarUrl.orEmpty(),
                                      songUrl = songUrl.orEmpty(),
                                      title = title.orEmpty(),
                                      subtitle = subtitle.orEmpty(),
                                      timeline = timeline.orEmpty(),
                                      releaseDate = releaseDate.orEmpty(),
                                      genres = genres.orEmpty(),
                                      likes = likes.orEmpty(),
                                      artists = artists.orEmpty())
                    }
                })
            }
            Log.d("HomeViewModel", "Fetched Recently Listened ${songsList.size} songs")
        }.catch { error ->
            Log.e(TAG, "Error fetching songs: $error")
        }.launchIn(viewModelScope)
    }

    fun onRecommendedlClick(songId: Int) {
        viewModelScope.launch {
            try {
                val userId = 1
                val result = viewsSongUseCase.trackSongView(userId, songId)
                val viewCount = result.numberListener ?: 0
                Log.d(TAG, "Song $songId viewed by user $userId - view count: $viewCount, timestamp: ${result.dateTime}")
            } catch (e: Exception) {
                Log.e(TAG, "Error recording song view: ${e.message}")
            }
        }
    }
}