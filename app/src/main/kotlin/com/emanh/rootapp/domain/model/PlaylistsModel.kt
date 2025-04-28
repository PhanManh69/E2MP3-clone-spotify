package com.emanh.rootapp.domain.model

data class PlaylistsModel(
    val playlistId: Int = 0,
    val avatarUrl: String?,
    val title: String?,
    val subtitle: String?,
    val isRadio: Boolean = false,
    val ownerId: Int?,
    val releaseDate: String,
    val songsIdList: List<Int>
)