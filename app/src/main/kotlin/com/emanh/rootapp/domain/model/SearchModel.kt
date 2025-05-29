package com.emanh.rootapp.domain.model

data class SearchModel(
    val id: Int = 0,
    val idTable: Int = 0,
    val isTable: String? = null,
    val normalizedSearchValue: String? = null,
)