package com.emanh.rootapp.domain.model

data class ViewsSongModel(
    val id: Int = 0,
    val userId: Int? = null,
    val songId: Int? = null,
    val numberListener: Int? = null,
    val dateTime: String? = null
)