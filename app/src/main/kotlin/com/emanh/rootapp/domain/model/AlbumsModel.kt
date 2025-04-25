package com.emanh.rootapp.domain.model

data class AlbumsModel(
    val id: Int,
    val avatarUrl: String?,
    val title: String?,
    val subtitle: String?,
    val albumType: String?,
    val releaseDate: String?,
    val artist: List<Int>?,
    val songs: List<Int>?
)