package com.emanh.rootapp.data.db.entity

data class UserInfo(
    val id: Long = 0L,
    val isArtist: Boolean = false,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val avatarUrl: String = "",
    val name: String = "",
    val isLoggedIn: Boolean = false,
)