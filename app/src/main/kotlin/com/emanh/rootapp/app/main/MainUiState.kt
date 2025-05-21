package com.emanh.rootapp.app.main

import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import kotlinx.coroutines.Job

data class MainUiState(
    val headerTitle: String = "",
    val headerSubtitle: String = "",
    val timeline: Long = 0L,
    val currentProgress: Float = 0f,
    val isPlayed: Boolean = false,
    val isLoading: Boolean = false,
    val progressJob: Job? = null,
    val single: SongsModel? = null,
    val artistsList: List<UsersModel> = emptyList(),
)