package com.emanh.rootapp.presentation.ui.searchinput

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SearchHistoryModel
import com.emanh.rootapp.domain.model.SearchModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.domain.usecase.AlbumsUseCase
import com.emanh.rootapp.domain.usecase.PlaylistsUseCase
import com.emanh.rootapp.domain.usecase.SearchHistoryUseCase
import com.emanh.rootapp.domain.usecase.SearchUseCase
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.UsersUseCase
import com.emanh.rootapp.presentation.navigation.AlbumScreenNavigation
import com.emanh.rootapp.presentation.navigation.ArtistScreenNavigation
import com.emanh.rootapp.presentation.navigation.PlaylistScreenNavigation
import com.emanh.rootapp.presentation.navigation.SingleScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.utils.MyConstant.ALBUMS_SEARCH
import com.emanh.rootapp.utils.MyConstant.ARTISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.PLAYLISTS_SEARCH
import com.emanh.rootapp.utils.MyConstant.SONGS_SEARCH
import com.emanh.rootapp.utils.removeAccents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchInputViewModel @Inject constructor(
    private val appRouter: AppRouter,
    private val searchUseCase: SearchUseCase,
    private val songsUseCase: SongsUseCase,
    private val usersUseCase: UsersUseCase,
    private val albumsUseCase: AlbumsUseCase,
    private val playlistsUseCase: PlaylistsUseCase,
    private val searchHistoryUseCase: SearchHistoryUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "SearchInputViewModel"
    }

    private val _uiState = MutableStateFlow(SearchInputUiState())
    val uiState: StateFlow<SearchInputUiState> = _uiState.asStateFlow()

    init {
        getSearchHistory()
    }

    fun goToBack() {
        appRouter.getNavController()?.goBack()
    }

    fun onIconClick(id: Int, type: String) {
        when (type) {
            SONGS_SEARCH -> {
                appRouter.getNavController()?.navigateTo(SingleScreenNavigation.getRoute(id))
            }

            ARTISTS_SEARCH -> {
                appRouter.getNavController()?.navigateTo(ArtistScreenNavigation.getRoute(id))
            }

            ALBUMS_SEARCH -> {
                appRouter.getNavController()?.navigateTo(AlbumScreenNavigation.getRoute(id))
            }

            PLAYLISTS_SEARCH -> {
                appRouter.getNavController()?.navigateTo(PlaylistScreenNavigation.getRoute(id))
            }
        }
    }

    fun onChipsSearchInputClick(chipId: Int) {
        _uiState.update { it.copy(chipState = chipId) }

        val currentMessage = _uiState.value.currentMessage
        if (currentMessage.isNotBlank()) {
            performSearch(currentMessage)
        }
    }

    fun updateMessage(message: String) {
        _uiState.update { it.copy(currentMessage = message) }

        if (message.isBlank()) {
            _uiState.update { it.copy(searchList = emptyList(), isLoading = false) }
            return
        }

        performSearch(message)
    }

    fun insertSearchHistory(idTable: Int, type: String) {
        viewModelScope.launch {
            val userId = 2
            val searchHistory = SearchHistoryModel(userId = userId, tableId = idTable, type = type)
            searchHistoryUseCase.insertSearchHistory(searchHistory)
        }
    }

    fun onRemovedSearchHistory(tableId: Int, type: String) {
        val userId = 2
        viewModelScope.launch {
            searchHistoryUseCase.deleteDuplicate(userId = userId, tableId = tableId, type = type)
        }
    }

    private fun performSearch(message: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val searchList = when (_uiState.value.chipState) {
                    -1 -> {
                        searchUseCase.getAllSearch(message.removeAccents()).catch { error ->
                            Log.e(TAG, "Error fetching SearchList: $error")
                            _uiState.update { it.copy(isLoading = false, searchList = emptyList()) }
                        }.collect { searchResult ->
                            val allResults = getAllSearchResults(searchResult)
                            _uiState.update {
                                it.copy(searchList = allResults, isLoading = false)
                            }
                        }
                        return@launch
                    }

                    0 -> getSearchArtist(message)
                    1 -> getSearchAlbum(message)
                    2 -> getSearchPlaylist(message)
                    3 -> getSearchSongs(message)
                    else -> emptyList()
                }

                _uiState.update {
                    it.copy(searchList = searchList, isLoading = false)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in performSearch: ${e.message}")
                _uiState.update { it.copy(isLoading = false, searchList = emptyList()) }
            }
        }
    }

    private fun getSearchHistory() {
        val userId = 2

        viewModelScope.launch {
            searchHistoryUseCase.getSearchHistory(userId).catch { error ->
                Log.e(TAG, "Error fetching SearchHistoryList: $error")
                emit(emptyList())
            }.collect { searchHistoryList ->
                val additionalItems = mutableListOf<Any>()

                coroutineScope {
                    val deferredItems = searchHistoryList.map { item ->
                        async {
                            try {
                                when (item.type) {
                                    SONGS_SEARCH -> {
                                        songsUseCase.getSongsById(item.tableId).first()
                                    }

                                    ARTISTS_SEARCH -> {
                                        usersUseCase.getArtistById(item.tableId).first()
                                    }

                                    ALBUMS_SEARCH -> {
                                        albumsUseCase.getAlbumsById(item.tableId).first()
                                    }

                                    PLAYLISTS_SEARCH -> {
                                        playlistsUseCase.getPlaylistsById(item.tableId).first()
                                    }

                                    else -> null
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "Error fetching item with id ${item.tableId}: $e")
                                null
                            }
                        }
                    }

                    deferredItems.awaitAll().filterNotNull().forEach { item ->
                        additionalItems.add(item)
                    }
                }

                _uiState.update {
                    it.copy(searchHistoryList = additionalItems)
                }
            }
        }
    }

    private suspend fun getAllSearchResults(searchResult: List<SearchModel>): List<Any> {
        return try {
            val searchList = mutableListOf<Any>()
            val songIds = searchResult.filter { it.isTable == SONGS_SEARCH }.map { it.idTable }
            val artistIds = searchResult.filter { it.isTable == ARTISTS_SEARCH }.map { it.idTable }
            val albumIds = searchResult.filter { it.isTable == ALBUMS_SEARCH }.map { it.idTable }
            val playlistIds = searchResult.filter { it.isTable == PLAYLISTS_SEARCH }.map { it.idTable }

            coroutineScope {
                val songsDeferred = async {
                    if (songIds.isNotEmpty()) {
                        songsUseCase.getSongsBySearch(songIds).firstOrNull() ?: emptyList()
                    } else emptyList()
                }
                val artistsDeferred = async {
                    if (artistIds.isNotEmpty()) {
                        usersUseCase.getArtistsBySearch(artistIds).firstOrNull() ?: emptyList()
                    } else emptyList()
                }
                val albumsDeferred = async {
                    if (albumIds.isNotEmpty()) {
                        albumsUseCase.getAlbumsBySearch(albumIds).firstOrNull() ?: emptyList()
                    } else emptyList()
                }
                val playlistsDeferred = async {
                    if (playlistIds.isNotEmpty()) {
                        playlistsUseCase.getPlaylistsBySearch(playlistIds).firstOrNull() ?: emptyList()
                    } else emptyList()
                }

                searchList.addAll(songsDeferred.await())
                searchList.addAll(artistsDeferred.await())
                searchList.addAll(albumsDeferred.await())
                searchList.addAll(playlistsDeferred.await())
                searchList
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error combining search lists: ${e.message}")
            emptyList()
        }
    }

    private suspend fun getSearchSongs(message: String): List<SongsModel> {
        return try {
            songsUseCase.getSearchSong(message.removeAccents()).first()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching SearchSongsList: $e")
            emptyList()
        }
    }

    private suspend fun getSearchArtist(message: String): List<UsersModel> {
        return try {
            usersUseCase.getSearchArtists(message.removeAccents()).first()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching SearchArtistsList: $e")
            emptyList()
        }
    }

    private suspend fun getSearchAlbum(message: String): List<AlbumsModel> {
        return try {
            albumsUseCase.getSearchAlbums(message.removeAccents()).first()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching SearchAlbumsList: $e")
            emptyList()
        }
    }

    private suspend fun getSearchPlaylist(message: String): List<PlaylistsModel> {
        return try {
            playlistsUseCase.getSearchPlaylists(message.removeAccents()).first()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching SearchPlaylistsList: $e")
            emptyList()
        }
    }
}