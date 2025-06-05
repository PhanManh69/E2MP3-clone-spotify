package com.emanh.rootapp.presentation.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.STFChips
import com.emanh.rootapp.presentation.composable.STFChipsSize
import com.emanh.rootapp.presentation.composable.STFChipsType
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body1Bold
import com.emanh.rootapp.presentation.theme.Body4Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceFieldInvert
import com.emanh.rootapp.presentation.theme.SurfaceInvert
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceSecondary
import com.emanh.rootapp.presentation.theme.SurfaceSecondaryInvert
import com.emanh.rootapp.presentation.theme.SurfaceTertiary
import com.emanh.rootapp.presentation.theme.TextBackgroundDark
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onLoginSuccess()
        }
    }

    LoginScaffold(inputAccount = uiState.account,
                  inputPassword = uiState.password,
                  errorMessage = uiState.errorMessage,
                  isLoading = uiState.isLoading,
                  goBack = viewModel::goBack,
                  onLoginClick = viewModel::onLoginClick,
                  onNotPasswordClick = viewModel::onNotPasswordClick,
                  onInputAccountChange = {
                      viewModel.onInputAccountChange(it)
                  },
                  onInputPasswordChange = {
                      viewModel.onInputPasswordChange(it)
                  })
}

@Composable
private fun LoginScaffold(
    modifier: Modifier = Modifier,
    inputAccount: String,
    inputPassword: String,
    errorMessage: String? = null,
    isLoading: Boolean,
    goBack: () -> Unit,
    onLoginClick: () -> Unit,
    onNotPasswordClick: () -> Unit,
    onInputAccountChange: (String) -> Unit,
    onInputPasswordChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    var accountValue by remember { mutableStateOf(false) }
    var passwordValue by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .fillMaxSize()
        .background(SurfacePrimary)
        .debounceClickable(indication = null, onClick = {
            focusManager.clearFocus()
        })) {
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

            TextField(value = inputAccount,
                      onValueChange = {
                          if (it.length <= 225) {
                              onInputAccountChange(it)
                              accountValue = it.isNotEmpty()
                          }
                      },
                      placeholder = {
                          Text(text = stringResource(id = R.string.enter_email_or_username), style = Body4Regular, color = TextSecondary)
                      },
                      textStyle = Body4Regular,
                      maxLines = 1,
                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                          .clip(shape = RoundedCornerShape(8.dp)))

            Spacer(Modifier.height(32.dp))

            Text(text = stringResource(R.string.password), color = TextPrimary, style = Body1Bold, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(8.dp))

            TextField(value = inputPassword,
                      onValueChange = {
                          if (it.length <= 225) {
                              onInputPasswordChange(it)
                              passwordValue = it.isNotEmpty()
                          }
                      },
                      placeholder = {
                          Text(text = stringResource(id = R.string.enter_password), style = Body4Regular, color = TextSecondary)
                      },
                      textStyle = Body4Regular,
                      maxLines = 1,
                      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                      visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                      trailingIcon = {
                          val icon = if (passwordVisible) R.drawable.ic_24_visibility else R.drawable.ic_24_visibility_off
                          Icon(painter = painterResource(id = icon),
                               contentDescription = null,
                               tint = IconPrimary,
                               modifier = Modifier
                                   .clip(RoundedCornerShape(8.dp))
                                   .clickable { passwordVisible = !passwordVisible })
                      },
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
                          .clip(shape = RoundedCornerShape(8.dp)))

            errorMessage?.let {
                Spacer(Modifier.height(16.dp))

                Text(text = it, color = Color.Red, style = Body4Regular)
            }

            Spacer(Modifier.height(32.dp))

            if (isLoading) {
                CircularProgressIndicator(
                        modifier = Modifier.size(54.dp),
                        color = SurfaceProduct,
                        trackColor = SurfaceSecondaryInvert,
                )
            } else {
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .background(color = if (accountValue && passwordValue) SurfaceInvert else SurfaceSecondary, shape = CircleShape)
                    .debounceClickable(onClick = {
                        if (accountValue && passwordValue) onLoginClick()
                    })) {
                    Text(text = stringResource(R.string.login),
                         color = TextBackgroundDark,
                         style = Body1Bold,
                         modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp))
                }
            }

            Spacer(Modifier.height(24.dp))

            STFChips(text = stringResource(R.string.login_not_password),
                     size = STFChipsSize.Small,
                     type = STFChipsType.Stroke,
                     onClick = onNotPasswordClick)
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    E2MP3Theme {
        var inputAccount by remember { mutableStateOf("") }
        var inputPassword by remember { mutableStateOf("") }

        LoginScaffold(inputAccount = inputAccount,
                      inputPassword = inputPassword,
                      isLoading = false,
                      goBack = {},
                      onLoginClick = {},
                      onNotPasswordClick = {},
                      onInputAccountChange = { inputAccount = it },
                      onInputPasswordChange = { inputPassword = it })
    }
}