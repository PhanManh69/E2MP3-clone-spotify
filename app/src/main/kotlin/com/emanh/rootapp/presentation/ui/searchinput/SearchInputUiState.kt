package com.emanh.rootapp.presentation.ui.searchinput

data class SearchInputUiState(
    val isLoading: Boolean = false,
    val currentMessage: String = "",
    val chipState: Int = -1,
    val searchList: List<Any> = emptyList(),
    val searchHistoryList: List<Any> = emptyList(),
)