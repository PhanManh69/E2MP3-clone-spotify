package com.emanh.rootapp.presentation.ui.logincontrol.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body4Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceStrokeSecondary
import com.emanh.rootapp.presentation.theme.TextBackgroundDark
import com.emanh.rootapp.presentation.theme.TextPrimary

enum class LoginControlButtonType {
    Default, Active
}

@Composable
fun LoginControlButton(
    modifier: Modifier = Modifier,
    title: String,
    iconId: Int = R.drawable.ic_logo_app_color,
    type: LoginControlButtonType = LoginControlButtonType.Default,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .clip(CircleShape)
        .debounceClickable(onClick = onClick)
        .then(if (type == LoginControlButtonType.Active) Modifier.background(color = SurfaceProduct, shape = CircleShape)
              else Modifier.border(width = 1.dp, color = SurfaceStrokeSecondary, shape = CircleShape))) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(painter = painterResource(iconId),
                 contentDescription = null,
                 tint = if (type == LoginControlButtonType.Active) Color.Transparent else Color.Unspecified,
                 modifier = Modifier.size(24.dp))

            Text(text = title, color = if (type == LoginControlButtonType.Active) TextBackgroundDark else TextPrimary, style = Body4Bold)

            Box(modifier = Modifier.size(24.dp))
        }
    }
}

@Preview
@Composable
private fun LoginButtonPreview1() {
    E2MP3Theme {
        LoginControlButton(title = stringResource(R.string.sign_up_free), type = LoginControlButtonType.Active, onClick = {})
    }
}

@Preview
@Composable
private fun LoginButtonPreview2() {
    E2MP3Theme {
        LoginControlButton(title = "${stringResource(R.string.continue_with)} Google", iconId = R.drawable.img_logo_google, onClick = {})
    }
}