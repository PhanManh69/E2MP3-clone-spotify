package com.emanh.rootapp.domain.model

data class SongsModel(
    val id: Int = 0,
    val avatarUrl: String? = null,
    val songUrl: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val timeline: String? = null,
    val releaseDate: String? = null,
    val genres: List<Int>? = null,
    val likes: List<Int>? = null,
    val artists: List<Int>? = null
)