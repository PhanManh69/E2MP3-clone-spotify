package com.emanh.rootapp.presentation.ui.upload

data class UploadUiState(
    val isLoading: Boolean = false,
    val showGenresDialog: Boolean = false,
    val isUploading: Boolean = false,
    val uploadSuccess: Boolean = false,
    val errorMessage: String? = null,
    val inputTitle: String = "",
    val inputSubtitle: String = "",
    val imageLink: String = "",
    val songLink: String = "",
    val genresSong: String = "",
    val songDuration: String = "",
    val genresList: List<Int> = emptyList(),
    val selectedGenres: List<Int> = emptyList(),
    val selectedGenresIndexes: List<Long> = emptyList(),
)