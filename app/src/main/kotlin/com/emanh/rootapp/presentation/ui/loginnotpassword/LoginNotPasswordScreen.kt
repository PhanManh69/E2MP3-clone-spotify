package com.emanh.rootapp.presentation.ui.loginnotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body1Bold
import com.emanh.rootapp.presentation.theme.Body4Regular
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceFieldInvert
import com.emanh.rootapp.presentation.theme.SurfaceInvert
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.SurfaceSecondary
import com.emanh.rootapp.presentation.theme.SurfaceTertiary
import com.emanh.rootapp.presentation.theme.TextBackgroundDark
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun LoginNotPasswordScreen() {
    var inputText by remember { mutableStateOf("") }

    LoginNotPasswordScaffold(inputText = inputText, goBack = {}, onGetCodeClick = {}, onInputTextChange = { inputText = it })
}

@Composable
private fun LoginNotPasswordScaffold(
    modifier: Modifier = Modifier, inputText: String, goBack: () -> Unit, onGetCodeClick: () -> Unit, onInputTextChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var textValue by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

    Box(modifier = modifier
        .fillMaxSize()
        .background(SurfacePrimary)) {
        Column(modifier = Modifier
            .imePadding()
            .statusBarsPadding()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(painter = painterResource(R.drawable.ic_24_musical_chevron_lt),
                     contentDescription = null,
                     tint = IconPrimary,
                     modifier = Modifier
                         .clip(RoundedCornerShape(8.dp))
                         .debounceClickable(onClick = goBack))
            }

            Spacer(Modifier.height(24.dp))

            Text(text = stringResource(R.string.email_or_username), color = TextPrimary, style = Body1Bold, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(8.dp))

            TextField(value = inputText,
                      onValueChange = {
                          if (it.length <= 225) {
                              onInputTextChange(it)
                              textValue = it.isNotEmpty()
                          }
                      },
                      placeholder = {
                          Text(text = stringResource(id = R.string.enter_email_or_username), style = Body4Regular, color = TextSecondary)
                      },
                      textStyle = Body4Regular,
                      maxLines = 1,
                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                      colors = TextFieldDefaults.colors(
                              focusedContainerColor = SurfaceFieldInvert,
                              unfocusedContainerColor = SurfaceTertiary,
                              disabledContainerColor = Color.Transparent,
                              focusedIndicatorColor = Color.Transparent,
                              unfocusedIndicatorColor = Color.Transparent,
                              cursorColor = TextPrimary,
                              focusedTextColor = TextPrimary,
                              unfocusedTextColor = TextPrimary,
                      ),
                      modifier = Modifier
                          .fillMaxWidth()
                          .clip(shape = RoundedCornerShape(8.dp))
                          .focusRequester(focusRequester))

            Spacer(Modifier.height(4.dp))

            Text(text = stringResource(R.string.serd_passcode_to_mail), color = TextPrimary, style = Body6Regular, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(32.dp))

            Box(modifier = Modifier
                .clip(CircleShape)
                .background(color = if (textValue) SurfaceInvert else SurfaceSecondary, shape = CircleShape)
                .debounceClickable(onClick = {
                    if (!textValue) onGetCodeClick
                })) {
                Text(text = stringResource(R.string.get_code),
                     color = TextBackgroundDark,
                     style = Body1Bold,
                     modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp))
            }
        }
    }
}