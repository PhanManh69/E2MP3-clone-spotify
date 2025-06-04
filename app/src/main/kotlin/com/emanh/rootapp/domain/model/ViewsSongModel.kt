package com.emanh.rootapp.domain.model

data class ViewsSongModel(
    val id: Long = 0,
    val userId: Long? = null,
    val songId: Long? = null,
    val numberListener: Long? = null,
    val dateTime: String? = null
)