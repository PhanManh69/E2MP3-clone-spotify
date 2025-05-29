package com.emanh.rootapp.domain.model

data class UsersModel(
    val id: Int = 0,
    val isArtist: Boolean = false,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val avatarUrl: String? = null,
    val name: String? = null,
    val normalizedSearchValue: String? = null,
    val followers: Int? = null,
    val following: List<Int>? = null,
)