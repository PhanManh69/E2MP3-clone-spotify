package com.emanh.rootapp.presentation.ui.player

data class PlayerUiState(
    val lyrics: String? = null,
    val isLoading: Boolean = false,
    val isAddSong: Boolean = false,
    val followingArtists: Set<Long> = setOf(),
    val showPlayerSheet: Boolean = false,
    val showLyricsSheet: Boolean = false,
    val viewMonthArtists: Map<Long, Long> = emptyMap(),
)