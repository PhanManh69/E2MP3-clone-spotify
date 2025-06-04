package com.emanh.rootapp.domain.model

data class SongsModel(
    val id: Long = 0,
    val avatarUrl: String? = null,
    val songUrl: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val normalizedSearchValue: String? = null,
    val timeline: String? = null,
    val releaseDate: String? = null,
    val genres: List<Long>? = null,
    val likes: List<Long>? = null,
    val artists: List<Long>? = null
)