package com.emanh.rootapp.domain.model

data class SongsModel(
    val id: Int,
    val avatarUrl: String?,
    val songUrl: String?,
    val title: String?,
    val subtitle: String?,
    val timeline: String?,
    val releaseDate: String?,
    val genres: List<Int>?,
    val likes: List<Int>?,
    val artists: List<Int>?
)