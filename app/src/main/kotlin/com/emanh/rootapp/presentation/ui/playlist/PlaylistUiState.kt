package com.emanh.rootapp.presentation.ui.playlist

import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel

data class PlaylistUiState(
    val isLoading: Boolean = false,
    val isAddPlaylist: Boolean = false,
    val playlist: PlaylistsModel? = null,
    val owner: UsersModel? = null,
    val songList: List<SongsModel> = emptyList(),
)