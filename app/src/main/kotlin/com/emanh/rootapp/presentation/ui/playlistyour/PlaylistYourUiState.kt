package com.emanh.rootapp.presentation.ui.playlistyour

import com.emanh.rootapp.domain.model.PlaylistsModel
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel

data class PlaylistYourUiState(
    val isLoading: Boolean = false,
    val isShowButtonSheet: Boolean = false,
    val owner: UsersModel? = null,
    val song: SongsModel? = null,
    val songsRecommend1: SongsModel? = null,
    val songsRecommend2: SongsModel? = null,
    val songsRecommend3: SongsModel? = null,
    val songsRecommend4: SongsModel? = null,
    val songsRecommend5: SongsModel? = null,
    val playlist: PlaylistsModel? = null,
    val songsList: List<SongsModel> = emptyList()
)