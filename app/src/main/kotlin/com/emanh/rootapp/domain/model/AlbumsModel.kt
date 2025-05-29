package com.emanh.rootapp.domain.model

data class AlbumsModel(
    val id: Int = 0,
    val avatarUrl: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val normalizedSearchValue: String? = null,
    val albumType: String? = null,
    val releaseDate: String? = null,
    val artist: List<Int>? = null,
    val songs: List<Int> = emptyList(),
)