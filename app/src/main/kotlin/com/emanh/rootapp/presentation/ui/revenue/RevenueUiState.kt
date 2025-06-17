package com.emanh.rootapp.presentation.ui.revenue

import com.emanh.rootapp.domain.model.SongsModel

data class RevenueUiState(
    val isLoading: Boolean = false,
    val yourSong: List<SongsModel> = emptyList()
)