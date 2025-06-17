package com.emanh.rootapp.domain.model

data class SearchModel(
    val id: Long = 0,
    val idTable: Long = 0,
    val isTable: String? = null,
    val normalizedSearchValue: String? = null,
)