package com.emanh.rootapp.presentation.ui.artist

import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel


data class ArtistUiState(
    val isLoading: Boolean = false,
    val viewsMonth: Long? = null,
    val isFollowing: Boolean = true,
    val artist: UsersModel? = null,
    val genreNameList: List<Int> = emptyList(),
    val songsList: List<SongsModel> = emptyList()
)