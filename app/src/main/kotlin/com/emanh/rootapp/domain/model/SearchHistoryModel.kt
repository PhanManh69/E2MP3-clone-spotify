package com.emanh.rootapp.domain.model

data class SearchHistoryModel(
    val id: Long = 0, val userId: Long = 0, val tableId: Long = 0, val type: String? = null
)