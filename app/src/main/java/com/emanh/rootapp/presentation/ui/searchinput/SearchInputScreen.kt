package com.emanh.rootapp.presentation.ui.searchinput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.STFHeader
import com.emanh.rootapp.presentation.composable.STFHeaderType
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.Title3Bold
import com.emanh.rootapp.utils.MyConstant.chipSearchInputList
import kotlinx.coroutines.delay

@Composable
fun SearchInputScreen() {
    val viewModel = hiltViewModel<SearchInputViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    SearchInputScaffold(currentMessage = uiState.currentMessage, onMessageChange = viewModel::updateMessage, onBackClick = {
        viewModel.goToBack()
    })
}

@Composable
private fun SearchInputScaffold(
    modifier: Modifier = Modifier, currentMessage: String, onMessageChange: (String) -> Unit, onBackClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

    STFHeader(inputText = currentMessage,
              searchChipsList = chipSearchInputList,
              type = STFHeaderType.HeaderSearchInput,
              onInputTextChange = onMessageChange,
              onBackClick = onBackClick,
              focusRequester = focusRequester,
              modifier = modifier.debounceClickable(indication = null) { focusManager.clearFocus() },
              content = {
                  Column(modifier = it
                      .fillMaxWidth()
                      .padding(horizontal = 16.dp)
                      .debounceClickable(indication = null) { focusManager.clearFocus() },
                         horizontalAlignment = Alignment.CenterHorizontally) {
                      Spacer(modifier = Modifier.height(164.dp))

                      Text(text = stringResource(R.string.search_what_you_love), color = TextPrimary, style = Title3Bold)

                      Spacer(modifier = Modifier.height(8.dp))

                      Text(text = stringResource(R.string.search_for_all), color = TextPrimary, style = Body6Regular)
                  }
              })
}