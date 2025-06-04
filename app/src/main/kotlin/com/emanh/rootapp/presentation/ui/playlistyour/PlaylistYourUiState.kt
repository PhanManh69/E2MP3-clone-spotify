package com.emanh.rootapp.presentation.ui.playlistyour

import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel

data class PlaylistYourUiState(
    val isLoading: Boolean = false,
    val owner: UsersModel? = null,
    val playlist: PlaylistsModel? = null,
    val songsList: List<SongsModel> = emptyList(),
    val songsRecommendList: List<SongsModel> = emptyList()
)