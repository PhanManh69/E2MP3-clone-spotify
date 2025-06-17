package com.emanh.rootapp.presentation.ui.upload

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body1Bold
import com.emanh.rootapp.presentation.theme.Body4Black
import com.emanh.rootapp.presentation.theme.Body4Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceAlphaSticky
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.SurfaceTertiary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.theme.TextTertiary
import com.emanh.rootapp.presentation.theme.Title5Bold
import com.emanh.rootapp.presentation.ui.upload.composable.UploadDialogGenres
import com.emanh.rootapp.presentation.ui.upload.composable.UploadTextField
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR

@Composable
fun UploadScreen(currentUser: UserInfo) {
    val viewModel = hiltViewModel<UploadViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.onImageSelected(it) }
    }

    val audioPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.onAudioSelected(it) }
    }

    val hasData =
        uiState.inputTitle.isNotBlank() || uiState.inputSubtitle.isNotBlank() || uiState.imageLink.isNotBlank() || uiState.songLink.isNotBlank() || uiState.genresSong.isNotEmpty()

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = hasData) {
        showExitDialog = true
    }

    UpdloadScaffold(inputTitle = uiState.inputTitle,
                    inputSubtitle = uiState.inputSubtitle,
                    imageLink = uiState.imageLink,
                    songLink = uiState.songLink,
                    genresSong = uiState.genresSong,
                    successMessage = uiState.errorMessage,
                    isUploading = uiState.isUploading,
                    goBack = {
                        if (hasData) {
                            showExitDialog = true
                        } else {
                            viewModel.goBack()
                            viewModel.clearError()
                        }
                    },
                    onUploadClick = {
                        viewModel.onUploadClick(listOf(currentUser.id))
                    },
                    onSelectAvatarClick = {
                        imagePickerLauncher.launch("image/*")
                        viewModel.clearError()
                    },
                    onSelectSongClick = {
                        audioPickerLauncher.launch("audio/*")
                        viewModel.clearError()
                    },
                    onSelectGenresClick = {
                        viewModel.onSelectGenresClick()
                        viewModel.clearError()
                    },
                    onInputTitleChange = {
                        viewModel.onInputTitleChange(it)
                        viewModel.clearError()
                    },
                    onInputSubtitleChange = {
                        viewModel.onInputSubtitleChange(it)
                        viewModel.clearError()
                    })

    if (uiState.showGenresDialog) {
        UploadDialogGenres(selectedGenres = uiState.selectedGenres,
                           genresList = uiState.genresList,
                           onConfirmClick = viewModel::onConfirmClick,
                           onDismissRequest = viewModel::onConfirmClick,
                           onCheckedChange = { genreResId, isNowChecked ->
                               viewModel.onCheckedChange(genreResId, isNowChecked)
                           })
    }

    if (showExitDialog) {
        AlertDialog(
                onDismissRequest = { showExitDialog = false },
                containerColor = SurfaceTertiary,
                shape = RoundedCornerShape(16.dp),
                title = {
                    Text(text = stringResource(R.string.confirm_exit), color = TextPrimary, style = Title5Bold)
                },
                text = {
                    Text(text = stringResource(R.string.are_you_sure_you_want_to_exit), color = TextTertiary, style = Body4Regular)
                },
                confirmButton = {
                    TextButton(onClick = {
                        showExitDialog = false
                        viewModel.goBack()
                    }) {
                        Text(stringResource(R.string.exit), color = TextPrimary, style = Body4Black)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) {
                        Text(stringResource(R.string.cancel), color = TextPrimary, style = Body4Black)
                    }
                },
        )
    }
}

@Composable
private fun UpdloadScaffold(
    modifier: Modifier = Modifier,
    inputTitle: String,
    inputSubtitle: String,
    imageLink: String,
    songLink: String,
    genresSong: String,
    successMessage: String?,
    isUploading: Boolean,
    goBack: () -> Unit,
    onUploadClick: () -> Unit,
    onSelectAvatarClick: () -> Unit,
    onSelectSongClick: () -> Unit,
    onSelectGenresClick: () -> Unit,
    onInputTitleChange: (String) -> Unit,
    onInputSubtitleChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.Asset("uploading.lottie"))

    Box(modifier = modifier) {
        Column(modifier = Modifier
            .statusBarsPadding()
            .background(SurfacePrimary)
            .debounceClickable(indication = null, onClick = {
                focusManager.clearFocus()
            })) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = goBack) {
                    Icon(painter = painterResource(R.drawable.ic_24_musical_chevron_lt), contentDescription = null, tint = IconPrimary)
                }

                Text(text = stringResource(R.string.add_song), color = TextPrimary, style = Body1Bold)

                IconButton(onClick = onUploadClick) {
                    Icon(painter = painterResource(R.drawable.ic_32_upload),
                         contentDescription = null,
                         tint = IconPrimary,
                         modifier = Modifier.size(24.dp))
                }
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(horizontal = 16.dp),
                       contentPadding = PaddingValues(top = 16.dp, bottom = PADDING_BOTTOM_BAR.dp),
                       verticalArrangement = Arrangement.spacedBy(24.dp)) {
                item {
                    Text(text = stringResource(R.string.title_song), color = TextPrimary, style = Body1Bold, modifier = Modifier.fillMaxWidth())

                    Spacer(Modifier.height(8.dp))

                    UploadTextField(inputText = inputTitle, placeholderId = R.string.enter_title_song, onInputTextChange = onInputTitleChange)
                }

                item {
                    Text(text = stringResource(R.string.subtitle_song), color = TextPrimary, style = Body1Bold, modifier = Modifier.fillMaxWidth())

                    Spacer(Modifier.height(8.dp))

                    UploadTextField(inputText = inputSubtitle, placeholderId = R.string.enter_title_song, onInputTextChange = onInputSubtitleChange)
                }

                item {
                    Text(text = stringResource(R.string.avatar_song), color = TextPrimary, style = Body1Bold, modifier = Modifier.fillMaxWidth())

                    Spacer(Modifier.height(8.dp))

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = SurfaceTertiary, shape = RoundedCornerShape(8.dp))
                        .debounceClickable(onClick = onSelectAvatarClick), contentAlignment = Alignment.Center) {
                        Text(text = if (imageLink.isEmpty()) stringResource(R.string.select_avatar_song) else imageLink,
                             color = if (imageLink.isEmpty()) TextSecondary else TextPrimary,
                             style = Body4Regular,
                             maxLines = 1,
                             overflow = TextOverflow.Ellipsis,
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(horizontal = 16.dp))
                    }
                }

                item {
                    Text(text = stringResource(R.string.song_link), color = TextPrimary, style = Body1Bold, modifier = Modifier.fillMaxWidth())

                    Spacer(Modifier.height(8.dp))

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = SurfaceTertiary, shape = RoundedCornerShape(8.dp))
                        .debounceClickable(onClick = onSelectSongClick), contentAlignment = Alignment.Center) {
                        Text(text = if (songLink.isEmpty()) stringResource(R.string.select_song_link) else songLink,
                             color = if (songLink.isEmpty()) TextSecondary else TextPrimary,
                             style = Body4Regular,
                             maxLines = 1,
                             overflow = TextOverflow.Ellipsis,
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(horizontal = 16.dp))
                    }
                }

                item {
                    Text(text = stringResource(R.string.genre_song), color = TextPrimary, style = Body1Bold, modifier = Modifier.fillMaxWidth())

                    Spacer(Modifier.height(8.dp))

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = SurfaceTertiary, shape = RoundedCornerShape(8.dp))
                        .debounceClickable(onClick = onSelectGenresClick), contentAlignment = Alignment.Center) {
                        Text(text = if (genresSong.isEmpty()) stringResource(R.string.select_genre_song) else genresSong,
                             color = if (genresSong.isEmpty()) TextSecondary else TextPrimary,
                             style = Body4Regular,
                             maxLines = 1,
                             overflow = TextOverflow.Ellipsis,
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(horizontal = 16.dp))
                    }
                }

                successMessage?.let {
                    item {
                        Text(text = it, color = Color.Red, style = Body4Regular)
                    }
                }
            }
        }

        if (isUploading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(SurfaceAlphaSticky)
                .debounceClickable(indication = null, onClick = {}),
                contentAlignment = Alignment.Center) {
                LottieAnimation(composition = lottieComposition,
                                modifier = Modifier.size(96.dp),
                                contentScale = ContentScale.Crop,
                                iterations = LottieConstants.IterateForever)
            }
        }
    }
}

@Preview
@Composable
private fun UploadScreenPreview() {
    E2MP3Theme {
        UpdloadScaffold(inputTitle = "",
                        inputSubtitle = "",
                        imageLink = "music.png",
                        songLink = "music.mp3",
                        genresSong = "",
                        successMessage = null,
                        isUploading = true,
                        goBack = {},
                        onUploadClick = {},
                        onSelectAvatarClick = {},
                        onSelectSongClick = {},
                        onSelectGenresClick = {},
                        onInputTitleChange = {},
                        onInputSubtitleChange = {})
    }
}