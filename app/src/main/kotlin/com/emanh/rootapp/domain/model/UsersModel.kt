package com.emanh.rootapp.domain.model

data class UsersModel(
    val id: Int,
    val isArtist: Boolean,
    val username: String?,
    val email: String?,
    val password: String?,
    val avatarUrl: String?,
    val name: String?,
    val followers: Int?,
    val following: List<Int>?,
)