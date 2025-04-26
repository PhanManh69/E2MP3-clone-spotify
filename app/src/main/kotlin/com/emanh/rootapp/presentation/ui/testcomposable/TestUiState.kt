package com.emanh.rootapp.presentation.ui.testcomposable

import com.emanh.rootapp.presentation.ui.home.HomeAlbumsData
import com.emanh.rootapp.presentation.ui.home.HomeGenreData
import com.emanh.rootapp.presentation.ui.home.HomeSongsData
import com.emanh.rootapp.presentation.ui.home.HomeUsersData

data class TestUiState(
    val isLiked: Boolean = false,
    val genres: List<HomeGenreData> = emptyList(),
    val songs: List<HomeSongsData> = emptyList(),
    val users: List<HomeUsersData> = emptyList(),
    val albums: List<HomeAlbumsData> = emptyList()
)