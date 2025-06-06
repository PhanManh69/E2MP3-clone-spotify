package com.emanh.rootapp.presentation.ui.upload

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanh.rootapp.domain.usecase.GenresUseCase
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appRouter: AppRouter,
    private val genresUseCase: GenresUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UploadUiState())
    val uiState: StateFlow<UploadUiState> = _uiState

    init {
        getGenresList()
    }

    fun goBack() {
        appRouter.getMainNavController()?.goBack()
    }

    fun onSelectGenresClick() {
        _uiState.update { it.copy(showGenresDialog = true) }
    }

    fun onConfirmClick() {
        _uiState.update { it.copy(showGenresDialog = false) }

        val selectedGenres = uiState.value.selectedGenres.joinToString(", ") { genreResId ->
            context.getString(genreResId)
        }

        _uiState.update { it.copy(genresSong = selectedGenres) }
    }

    fun onCheckedChange(genreResId: Int, isNowChecked: Boolean) {
        if (isNowChecked) {
            if (genreResId !in uiState.value.selectedGenres) _uiState.update { it.copy(selectedGenres = it.selectedGenres + genreResId) }
        } else {
            _uiState.update { it.copy(selectedGenres = it.selectedGenres - genreResId) }
        }
    }

    fun onInputTitleChange(inputTitle: String) {
        _uiState.update { it.copy(inputTitle = inputTitle) }
    }

    fun onInputSubtitleChange(inputSubtitle: String) {
        _uiState.update { it.copy(inputSubtitle = inputSubtitle) }
    }

    fun onImageSelected(uri: Uri) {
        _uiState.update { it.copy(imageLink = uri.toString()) }
    }

    fun onAudioSelected(uri: Uri) {
        _uiState.update { it.copy(songLink = uri.toString()) }
    }

    private fun getGenresList() {
        viewModelScope.launch {
            genresUseCase.getAllGenres().collect { genres ->
                val genresList = genres.map { it.nameId }
                _uiState.update { it.copy(genresList = genresList) }
            }
        }
    }
}