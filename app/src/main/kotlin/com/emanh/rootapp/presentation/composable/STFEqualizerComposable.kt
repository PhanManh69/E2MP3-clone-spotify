package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconProduct
import com.emanh.rootapp.utils.MyConstant.equalizerIconList
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun STFEqualizerComposable(
    modifier: Modifier = Modifier, intervalMillis: Long = 150L
) {
    val scope = rememberCoroutineScope()

    var currentIndex by remember { mutableIntStateOf(0) }

    DisposableEffect(Unit) {
        scope.launch {
            while (true) {
                delay(intervalMillis)
                currentIndex = (currentIndex + 1) % equalizerIconList.size
            }
        }

        onDispose {
            scope.cancel()
        }
    }

    Icon(painter = painterResource(id = equalizerIconList[currentIndex]),
         contentDescription = null,
         tint = IconProduct,
         modifier = modifier.size(24.dp))
}

@Preview
@Composable
fun EqualizerComposablePreview() {
    E2MP3Theme {
        STFEqualizerComposable()
    }
}
