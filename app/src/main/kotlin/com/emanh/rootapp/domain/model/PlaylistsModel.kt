package com.emanh.rootapp.domain.model

data class PlaylistsModel(
    val id: Int = 0,
    val avatarUrl: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val normalizedSearchValue: String? = null,
    val isRadio: Boolean = false,
    val ownerId: Int? = null,
    val releaseDate: String = "",
    val songsIdList: List<Int> = emptyList(),
)