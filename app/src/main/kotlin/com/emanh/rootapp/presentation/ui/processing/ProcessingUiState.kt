package com.emanh.rootapp.presentation.ui.processing

import com.emanh.rootapp.domain.model.SongsModel

data class ProcessingUiState(
    val songsList: List<SongsModel> = emptyList()
)