package com.emanh.rootapp.presentation.ui.upload

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.goBack
import com.emanh.rootapp.presentation.navigation.router.AppRouter
import com.emanh.rootapp.utils.MyConstant.CLOUNDINARY_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import androidx.core.net.toUri
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.StatusUpload
import com.emanh.rootapp.data.db.entity.crossref.SongArtistEntity
import com.emanh.rootapp.domain.model.SearchModel
import com.emanh.rootapp.domain.model.UploadModel
import com.emanh.rootapp.domain.usecase.SearchUseCase
import com.emanh.rootapp.domain.usecase.SongsUseCase
import com.emanh.rootapp.domain.usecase.UploadUseCase
import com.emanh.rootapp.domain.usecase.crossref.CrossRefSongUseCase
import com.emanh.rootapp.presentation.navigation.ProcessingScreenNavigation
import com.emanh.rootapp.presentation.navigation.extensions.NavActions.navigateTo
import com.emanh.rootapp.utils.MyConstant.SONGS_SEARCH
import com.emanh.rootapp.utils.MyConstant.genresList
import com.emanh.rootapp.utils.removeAccents
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.Long
import kotlin.String

@HiltViewModel
class UploadViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appRouter: AppRouter,
    private val songsUseCase: SongsUseCase,
    private val uploadUseCase: UploadUseCase,
    private val searchUseCase: SearchUseCase,
    private val crossRefSongUseCase: CrossRefSongUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UploadUiState())
    val uiState: StateFlow<UploadUiState> = _uiState

    private val cloudinary = Cloudinary(CLOUNDINARY_URL)

    init {
        getGenresList()
    }

    fun goBack() {
        appRouter.getMainNavController()?.goBack()
    }

    fun onSelectGenresClick() {
        _uiState.update { it.copy(showGenresDialog = true) }
    }

    fun onNotiClick() {
        appRouter.getMainNavController()?.navigateTo(ProcessingScreenNavigation.getRoute())
    }

    fun onConfirmClick() {
        _uiState.update { it.copy(showGenresDialog = false) }

        val selectedGenres = uiState.value.selectedGenres.joinToString(", ") { genreResId ->
            context.getString(genreResId)
        }

        _uiState.update { it.copy(genresSong = selectedGenres) }
    }

    fun onCheckedChange(genreResId: Int, isNowChecked: Boolean) {
        val index: Long = uiState.value.genresList.indexOf(genreResId).toLong()

        if (index != -1L) {
            if (isNowChecked) {
                if (genreResId !in uiState.value.selectedGenres) {
                    _uiState.update {
                        it.copy(selectedGenres = it.selectedGenres + genreResId, selectedGenresIndexes = it.selectedGenresIndexes + index)
                    }

                    Log.d(TAG, "Selected Genres ID: ${uiState.value.selectedGenresIndexes}")
                }
            } else {
                _uiState.update {
                    it.copy(selectedGenres = it.selectedGenres - genreResId, selectedGenresIndexes = it.selectedGenresIndexes - index)
                }

                Log.d(TAG, "Selected Genres ID: ${uiState.value.selectedGenresIndexes}")
            }
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

    @SuppressLint("DefaultLocale")
    fun onAudioSelected(uri: Uri) {
        viewModelScope.launch {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, uri)

            val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val durationMs = durationStr?.toLongOrNull() ?: 0L
            val hours = durationMs / 1000 / 3600
            val minutes = (durationMs / 1000 % 3600) / 60
            val seconds = (durationMs / 1000) % 60
            val formattedDuration = String.format("%02d:%02d:%02d", hours, minutes, seconds)


            retriever.release()

            Log.d(TAG, "Song Duration: $formattedDuration")
            _uiState.value = _uiState.value.copy(songLink = uri.toString(), songDuration = formattedDuration)
        }
    }

    fun onUploadClick(artistsId: List<Long>) {
        if (!isValidInput()) {
            _uiState.update { it.copy(errorMessage = context.getString(R.string.please_fill_in_all_required_fields)) }
            return
        }

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isUploading = true, errorMessage = null) }

                val imageUrl = uploadImageToCloudinary(uiState.value.imageLink)
                val audioUrl = uploadAudioToCloudinary(uiState.value.songLink)
                val songData = SongsModel(avatarUrl = imageUrl,
                                          songUrl = audioUrl,
                                          title = uiState.value.inputTitle,
                                          subtitle = uiState.value.inputSubtitle,
                                          normalizedSearchValue = "${uiState.value.inputTitle.removeAccents()} ${uiState.value.inputSubtitle.removeAccents()}",
                                          timeline = uiState.value.songDuration,
                                          releaseDate = getCurrentDate(),
                                          genres = uiState.value.selectedGenresIndexes,
                                          artists = artistsId,
                                          statusUpload = StatusUpload.PROCESSING.name)
                val insertSong = songsUseCase.insertSong(songData)
                val uploadData = UploadModel(userId = artistsId.first(), songId = insertSong, statusUpload = StatusUpload.PROCESSING.name)

                uploadUseCase.insertUpdoad(uploadData)
                val searchData = SearchModel(idTable = insertSong,
                                             isTable = SONGS_SEARCH,
                                             normalizedSearchValue = "${uiState.value.inputTitle.removeAccents()} ${uiState.value.inputSubtitle.removeAccents()}")

                searchUseCase.insertSearch(searchData)
                crossRefSongUseCase.insertSongArtist(SongArtistEntity(insertSong, artistsId.first()))

                _uiState.update {
                    it.copy(isUploading = false, uploadSuccess = true)
                }

                goBack()
            } catch (e: Exception) {
                Log.e(TAG, "Error uploading song: ${e.message}")

                _uiState.update {
                    it.copy(isUploading = false, errorMessage = context.getString(R.string.upload_failed))
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    private fun isValidInput(): Boolean {
        return uiState.value.inputTitle.isNotBlank() && uiState.value.inputSubtitle.isNotBlank() && uiState.value.imageLink.isNotBlank() && uiState.value.songLink.isNotBlank() && uiState.value.genresSong.isNotEmpty()
    }

    private fun getGenresList() {
        _uiState.update {
            it.copy(genresList = genresList)
        }
    }

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return formatter.format(Date())
    }

    private suspend fun uploadImageToCloudinary(imageUri: String): String = withContext(Dispatchers.IO) {
        val uri = imageUri.toUri()
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_image", ".jpg", context.cacheDir)

        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val uploadResult = cloudinary.uploader().upload(tempFile, mapOf("resource_type" to "image", "folder" to "e2mp3-spotify/img"))

        tempFile.delete()
        uploadResult["secure_url"] as String
    }

    private suspend fun uploadAudioToCloudinary(audioUri: String): String = withContext(Dispatchers.IO) {
        val uri = audioUri.toUri()
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_audio", ".mp3", context.cacheDir)

        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val uploadResult = cloudinary.uploader().upload(tempFile, mapOf("resource_type" to "video", "folder" to "e2mp3-spotify/mp3"))

        tempFile.delete()
        uploadResult["secure_url"] as String
    }

    companion object {
        private const val TAG = "UploadViewModel"
    }
}