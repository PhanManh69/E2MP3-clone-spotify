package com.emanh.rootapp.presentation.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.emanh.rootapp.presentation.theme.SurfaceInvert
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceProductSuperDark
import com.emanh.rootapp.presentation.theme.SurfaceSecondary
import com.emanh.rootapp.presentation.theme.TextBackgroundDark
import com.emanh.rootapp.presentation.ui.signup.composable.SignUpTextField

@Composable
fun SignUpScreen() {
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    SignUpScaffold(inputFullName = uiState.inputFullName,
                   inputUsername = uiState.inputUsername,
                   inputEmail = uiState.inputEmail,
                   inputPassword = uiState.inputPassword,
                   inputConfirmPassword = uiState.inputConfirmPassword,
                   errorMessage = uiState.errorMessage,
                   isLoading = uiState.isLoading,
                   goBack = viewModel::goBack,
                   onSignUpClick = viewModel::onSignUpClick,
                   onLoginClick = viewModel::onLoginClick,
                   onInputFullNameChange = {
                       viewModel.onInputFullNameChange(it)
                   },
                   onInputUsernameChange = {
                       viewModel.onInputUsernameChange(it)
                   },
                   onInputEmailChange = {
                       viewModel.onInputEmailChange(it)
                   },
                   onInputPasswordChange = {
                       viewModel.onInputPasswordChange(it)
                   },
                   onInputConfirmPasswordChange = {
                       viewModel.onInputConfirmPasswordChange(it)
                   })
}

@Composable
private fun SignUpScaffold(
    modifier: Modifier = Modifier,
    inputFullName: String,
    inputUsername: String,
    inputEmail: String,
    inputPassword: String,
    inputConfirmPassword: String,
    errorMessage: String? = null,
    isLoading: Boolean,
    goBack: () -> Unit,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
    onInputFullNameChange: (String) -> Unit,
    onInputUsernameChange: (String) -> Unit,
    onInputEmailChange: (String) -> Unit,
    onInputPasswordChange: (String) -> Unit,
    onInputConfirmPasswordChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var fullNameValue by remember { mutableStateOf(false) }
    var usernameValue by remember { mutableStateOf(false) }
    var emailValue by remember { mutableStateOf(false) }
    var passwordValue by remember { mutableStateOf(false) }
    var confirmPasswordValue by remember { mutableStateOf(false) }

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

            Column(modifier = Modifier
                .imePadding()
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(32.dp)) {
                SignUpTextField(inputText = inputFullName,
                                titleId = R.string.fullname,
                                placeholderId = R.string.enter_fullname_your,
                                onInputTextChange = {
                                    onInputFullNameChange(it)
                                    fullNameValue = it.isNotEmpty()
                                })

                SignUpTextField(inputText = inputUsername,
                                titleId = R.string.username,
                                placeholderId = R.string.enter_username_your,
                                onInputTextChange = {
                                    onInputUsernameChange(it)
                                    usernameValue = it.isNotEmpty()
                                })

                SignUpTextField(inputText = inputEmail,
                                titleId = R.string.email,
                                placeholderId = R.string.enter_email_your,
                                keyboardType = KeyboardType.Email,
                                onInputTextChange = {
                                    onInputEmailChange(it)
                                    emailValue = it.isNotEmpty()
                                })

                SignUpTextField(inputText = inputPassword,
                                titleId = R.string.password,
                                placeholderId = R.string.enter_password,
                                isPassword = true,
                                keyboardType = KeyboardType.Password,
                                onInputTextChange = {
                                    onInputPasswordChange(it)
                                    passwordValue = it.isNotEmpty()
                                })

                SignUpTextField(inputText = inputConfirmPassword,
                                titleId = R.string.confirm_password,
                                placeholderId = R.string.enter_confirm_password,
                                isPassword = true,
                                keyboardType = KeyboardType.Password,
                                onInputTextChange = {
                                    onInputConfirmPasswordChange(it)
                                    confirmPasswordValue = it.isNotEmpty()
                                })
            }

            errorMessage?.let {
                Spacer(Modifier.height(16.dp))

                Text(text = it, color = Color.Red, style = Body4Regular)
            }

            Spacer(Modifier.height(32.dp))

            if (isLoading) {
                CircularProgressIndicator(
                        modifier = Modifier.size(54.dp),
                        color = SurfaceProduct,
                        trackColor = SurfaceProductSuperDark,
                )
            } else {
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .background(color = if (fullNameValue && usernameValue && emailValue && passwordValue && confirmPasswordValue) SurfaceInvert else SurfaceSecondary,
                                shape = CircleShape)
                    .debounceClickable(onClick = {
                        if (fullNameValue && usernameValue && emailValue && passwordValue && confirmPasswordValue) onSignUpClick()
                    })) {
                    Text(text = stringResource(R.string.sign_up),
                         color = TextBackgroundDark,
                         style = Body1Bold,
                         modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp))
                }
            }

            Spacer(Modifier.height(24.dp))

            STFChips(text = stringResource(R.string.login), size = STFChipsSize.Small, type = STFChipsType.Stroke, onClick = onLoginClick)
        }
    }
}

@Preview
@Composable
private fun SignUpPreview() {
    E2MP3Theme {
        var inputFullName by remember { mutableStateOf("") }
        var inputUsername by remember { mutableStateOf("") }
        var inputEmail by remember { mutableStateOf("") }
        var inputPassword by remember { mutableStateOf("") }
        var inputConfirmPassword by remember { mutableStateOf("") }

        SignUpScaffold(inputFullName = inputFullName,
                       inputUsername = inputUsername,
                       inputEmail = inputEmail,
                       inputPassword = inputPassword,
                       inputConfirmPassword = inputConfirmPassword,
                       isLoading = false,
                       goBack = {},
                       onSignUpClick = {},
                       onLoginClick = {},
                       onInputFullNameChange = { inputFullName = it },
                       onInputUsernameChange = { inputUsername = it },
                       onInputEmailChange = { inputEmail = it },
                       onInputPasswordChange = { inputPassword = it },
                       onInputConfirmPasswordChange = { inputConfirmPassword = it })
    }
}