package com.emanh.rootapp.domain.model

data class ViewsSongModel(
    val viewsSongId: Int,
    val userId: Int?,
    val songId: Int?,
    val numberListener: Int?,
    val dateTime: String?
)