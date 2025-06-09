package com.emanh.rootapp.presentation.ui.logincontrol

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.ui.logincontrol.composable.LoginControlButton
import com.emanh.rootapp.presentation.ui.logincontrol.composable.LoginControlButtonType
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body4Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.Title2Bold

@Composable
fun LoginControlScreen() {
    val viewModel = hiltViewModel<LoginControlViewModel>()

    LoginControlScaffold(
            onSignUpClick = viewModel::onSignUpClick,
            onWithGoogleClick = {},
            onWithFacebookClick = {},
            onWithAppleClick = {},
            onLoginClick = viewModel::onLoginClick,
    )
}

@Composable
private fun LoginControlScaffold(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onWithGoogleClick: () -> Unit,
    onWithFacebookClick: () -> Unit,
    onWithAppleClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val navigationBarsHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Box(modifier = modifier.background(SurfacePrimary)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .padding(bottom = navigationBarsHeight + 16.dp),
               horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.weight(1f))

            Icon(painter = painterResource(R.drawable.ic_logo_app), contentDescription = null, tint = IconPrimary, modifier = Modifier.size(44.dp))

            Spacer(Modifier.height(18.dp))

            Text(text = stringResource(R.string.millions_songs_free_spotify), color = TextPrimary, style = Title2Bold, textAlign = TextAlign.Center)

            Spacer(Modifier.height(32.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                LoginControlButton(title = stringResource(R.string.sign_up_free), type = LoginControlButtonType.Active, onClick = onSignUpClick)

                LoginControlButton(title = "${stringResource(R.string.continue_with)} Google",
                                   iconId = R.drawable.img_logo_google,
                                   type = LoginControlButtonType.Default,
                                   onClick = onWithGoogleClick)

                LoginControlButton(title = "${stringResource(R.string.continue_with)} Facebook",
                                   iconId = R.drawable.img_logo_facebook,
                                   type = LoginControlButtonType.Default,
                                   onClick = onWithFacebookClick)

                LoginControlButton(title = "${stringResource(R.string.continue_with)} Apple",
                                   iconId = R.drawable.img_logo_apple,
                                   type = LoginControlButtonType.Default,
                                   onClick = onWithAppleClick)

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(CircleShape)
                    .debounceClickable(onClick = onLoginClick),
                    contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.login), color = TextPrimary, style = Body4Bold)
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginControlScreenPreview() {
    E2MP3Theme {
        LoginControlScaffold(onSignUpClick = {}, onWithGoogleClick = {}, onWithFacebookClick = {}, onWithAppleClick = {}, onLoginClick = {})
    }
}