package com.emanh.rootapp.presentation.composable.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

fun Modifier.shimmerEffect(): Modifier = composed {
    val shimmerColors = listOf(Color(0xFFEEEEEE), Color(0xFFC0C0C0), Color(0xFFEEEEEE))

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(initialValue = 0f,
                                                targetValue = 1000f,
                                                animationSpec = infiniteRepeatable(animation = tween(durationMillis = 1500, easing = LinearEasing),
                                                                                   repeatMode = RepeatMode.Restart),
                                                label = "shimmer")

    drawWithCache {
        val brush = Brush.linearGradient(colors = shimmerColors,
                                         start = Offset(translateAnim.value - 400f, 0f),
                                         end = Offset(translateAnim.value, size.height),
                                         tileMode = TileMode.Clamp)

        onDrawWithContent {
            drawContent()
            drawRect(brush = brush, alpha = 0.6f)
        }
    }
}