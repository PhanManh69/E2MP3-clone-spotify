package com.emanh.rootapp.utils

import kotlinx.coroutines.delay

suspend fun loadProgress(
    isPlaying: () -> Boolean, updateProgress: (Float) -> Unit, onFinish: () -> Unit
) {
    var i = 0
    while (i <= 100) {
        if (isPlaying()) {
            updateProgress(i.toFloat() / 100)
            i++
        }
        delay(100)
    }
    onFinish()
}