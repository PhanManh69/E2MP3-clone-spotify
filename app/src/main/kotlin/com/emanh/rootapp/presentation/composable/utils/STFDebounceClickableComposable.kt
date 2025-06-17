package com.emanh.rootapp.presentation.composable.utils

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun Modifier.debounceClickable(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = LocalIndication.current,
    debounceInterval: Long = 800,
    onClick: () -> Unit,
): Modifier {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    return clickable(
        interactionSource = interactionSource, 
        indication = indication
    ) {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastClickTime) >= debounceInterval) {
            lastClickTime = currentTime
            onClick()
        }
    }
}
