package com.emanh.rootapp.presentation.ui.album

import com.emanh.rootapp.domain.model.AlbumsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel

data class AlbumUiState(
    val isLoading: Boolean = false,
    val views: Int? = null,
    val album: AlbumsModel? = null,
    val artistList: List<UsersModel> = emptyList(),
    val songList: List<SongsModel> = emptyList()
)