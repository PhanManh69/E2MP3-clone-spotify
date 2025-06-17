package com.emanh.rootapp.presentation.ui.single

import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel

data class SingleUiState(
    val isLoading: Boolean = false,
    val isAddSong: Boolean = false,
    val views: Int? = null,
    val single: SongsModel? = null,
    val artistList: List<UsersModel> = emptyList(),
    val moreByArtists: List<SongsModel> = emptyList()
)