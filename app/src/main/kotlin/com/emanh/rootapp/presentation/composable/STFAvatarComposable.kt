package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.AvatarBlu
import com.emanh.rootapp.presentation.theme.AvatarFocusBlu
import com.emanh.rootapp.presentation.theme.AvatarRed
import com.emanh.rootapp.presentation.theme.AvatarViolet
import com.emanh.rootapp.presentation.theme.Body2Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextBackgroundDark
import com.emanh.rootapp.utils.MyConstant.AVATAR_URL
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun STFAvatar(modifier: Modifier = Modifier, avatarUrl: String? = null, userName: String, onClick: () -> Unit = {}) {
    var isImageLoaded by remember { mutableStateOf(false) }
    var isLoadFailed by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val listAvatarColor = listOf(AvatarViolet, AvatarRed, AvatarBlu, AvatarFocusBlu)
    val avatarColor = listAvatarColor.random()

    DisposableEffect(avatarUrl) {
        scope.launch {
            delay(10000L)
            if (!isImageLoaded) {
                isLoadFailed = true
            }
        }

        onDispose {
            scope.cancel()
        }
    }

    if (avatarUrl != null && !isLoadFailed) {
        AsyncImage(modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .debounceClickable { onClick() }
            .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                   model = ImageRequest.Builder(LocalContext.current)
                       .data(avatarUrl)
                       .crossfade(true)
                       .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> })
                       .build(),
                   contentDescription = null,
                   contentScale = ContentScale.Crop)
    } else {
        Box(modifier = modifier
            .size(32.dp)
            .background(color = avatarColor, shape = CircleShape)
            .clip(CircleShape)
            .debounceClickable { onClick() }) {
            Text(text = userName.first().uppercase(), color = TextBackgroundDark, style = Body2Regular, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
fun AvatarUrlPreview() {
    E2MP3Theme {
        STFAvatar(avatarUrl = AVATAR_URL, userName = "emanh", onClick = {})
    }
}

@Preview
@Composable
fun AvatarUsernamePreview() {
    E2MP3Theme {
        STFAvatar(userName = "emanh", onClick = {})
    }
}