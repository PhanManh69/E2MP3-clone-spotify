package com.emanh.rootapp.domain.model

data class PlaylistsModel(
    val id: Long = 0,
    val avatarUrl: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val normalizedSearchValue: String? = null,
    val isRadio: Boolean = false,
    val ownerId: Long? = null,
    val releaseDate: String = "",
    val songsIdList: List<Long> = emptyList(),
)