package com.emanh.rootapp.presentation.ui.createplaylist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.STFChips
import com.emanh.rootapp.presentation.composable.STFChipsSize
import com.emanh.rootapp.presentation.composable.STFChipsType
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body3Medium
import com.emanh.rootapp.presentation.theme.CreatePlaylist
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.theme.Title4Bold
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import kotlinx.coroutines.delay

@Composable
fun CreatePlaylistScreen() {
    val viewModel = hiltViewModel<CreatePlaylistViewModel>()
    var inputText by remember { mutableStateOf("") }

    CreatePlaylistScaffold(inputText = inputText, onCancelClick = viewModel::onCancelClick, onCreateClick = {
        viewModel.onCreateClick(inputText)
    }, onInputTextChange = {
        inputText = it
    })
}

@Composable
private fun CreatePlaylistScaffold(
    modifier: Modifier = Modifier, inputText: String, onCancelClick: () -> Unit, onCreateClick: () -> Unit, onInputTextChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

    Box(modifier = modifier
        .background(brush = CreatePlaylist)
        .debounceClickable(indication = null, onClick = {
            focusManager.clearFocus()
        })) {
        Column(modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(bottom = PADDING_BOTTOM_BAR.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)) {
            Text(text = stringResource(R.string.name_for_playlist), color = TextPrimary, style = Body3Medium)

            TextField(value = inputText,
                      onValueChange = {
                          if (it.length <= 225) {
                              onInputTextChange(it)
                          }
                      },
                      placeholder = {
                          Text(text = stringResource(id = R.string.name_playlist),
                               style = Title4Bold,
                               color = TextSecondary,
                               textAlign = TextAlign.Center,
                               modifier = Modifier.fillMaxWidth())
                      },
                      textStyle = Title4Bold.copy(textAlign = TextAlign.Center),
                      maxLines = 4,
                      singleLine = false,
                      colors = TextFieldDefaults.colors(
                              focusedContainerColor = Color.Transparent,
                              unfocusedContainerColor = Color.Transparent,
                              disabledContainerColor = Color.Transparent,
                              focusedIndicatorColor = Color.Transparent,
                              unfocusedIndicatorColor = Color.Transparent,
                              cursorColor = TextPrimary,
                              focusedTextColor = TextPrimary,
                              unfocusedTextColor = TextPrimary,
                      ),
                      modifier = Modifier
                          .fillMaxWidth()
                          .focusRequester(focusRequester))

            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                STFChips(text = stringResource(R.string.cancel), size = STFChipsSize.Normal, type = STFChipsType.Stroke, onClick = onCancelClick)

                STFChips(text = stringResource(R.string.create), size = STFChipsSize.Normal, type = STFChipsType.Active, onClick = onCreateClick)
            }
        }
    }
}

@Preview
@Composable
private fun CreatePlaylistScreenPreview() {
    E2MP3Theme {
        var inputText by remember { mutableStateOf("") }
        CreatePlaylistScaffold(inputText = inputText, onInputTextChange = { inputText = it }, onCancelClick = {}, onCreateClick = {})
    }
}