package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body7Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconTabbar
import com.emanh.rootapp.presentation.theme.IconTabbarSelect
import com.emanh.rootapp.presentation.theme.TextTabbar
import com.emanh.rootapp.presentation.theme.TextTabbarSelect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class STFTabIconType {
    Default, Select
}

@Composable
fun STFTabIcon(modifier: Modifier = Modifier, icon: Int, type: STFTabIconType = STFTabIconType.Default, onClick: () -> Unit = {}) {
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val animatedPadding by animateDpAsState(if (toggled) 4.dp else 0.dp)

    Box(modifier = modifier) {
        Icon(painter = painterResource(id = icon),
             contentDescription = null,
             tint = if (type == STFTabIconType.Default) IconTabbar else IconTabbarSelect,
             modifier = Modifier
                 .width(96.dp)
                 .height(32.dp)
                 .padding(animatedPadding)
                 .align(Alignment.Center)
                 .debounceClickable(indication = null) {
                     coroutineScope.launch {
                         toggled = true
                         delay(100L)
                         toggled = false
                         delay(100L)
                         onClick()
                     }
                 })
    }
}

@Composable
fun STFTabTitle(modifier: Modifier = Modifier, title: String, type: STFTabIconType = STFTabIconType.Default) {
    Text(modifier = modifier.padding(bottom = 32.dp),
         text = title,
         style = Body7Regular,
         color = if (type == STFTabIconType.Default) TextTabbar else TextTabbarSelect,
         textAlign = TextAlign.Center)
}

@Preview
@Composable
fun TabIconDefaultPreview() {
    E2MP3Theme {
        STFTabIcon(icon = R.drawable.ic_32_home, type = STFTabIconType.Default, onClick = {})
    }
}

@Preview
@Composable
fun TabIconSelectPreview() {
    E2MP3Theme {
        STFTabIcon(icon = R.drawable.ic_32_home, type = STFTabIconType.Select, onClick = {})
    }
}

@Preview
@Composable
fun TabTitleDefaultPreview() {
    E2MP3Theme {
        STFTabTitle(title = stringResource(R.string.home), type = STFTabIconType.Select)
    }
}

@Preview
@Composable
fun TabTitleSelectPreview() {
    E2MP3Theme {
        STFTabTitle(title = stringResource(R.string.home), type = STFTabIconType.Select)
    }
}