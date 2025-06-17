package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.theme.Body5Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.TextPrimary
import kotlinx.coroutines.delay

@Composable
fun STFMarqueeText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = Body5Bold,
    textColor: Color = TextPrimary,
    alignment: Alignment.Horizontal = Alignment.Start,
    gradientEdgeColor: Color = Color.Transparent,
    gradientEdgeWidth: Int = 16,
    showGradientEdge: Boolean = true,
    delayStart: Long = 1000L,
    spaceBetween: Int = 40,
    speed: Float = 30f
) {
    val textWidth = remember { mutableIntStateOf(0) }
    val containerWidth = remember { mutableIntStateOf(0) }
    val scrollPosition = remember { Animatable(0f) }
    val textFits = remember(textWidth.intValue, containerWidth.intValue) {
        containerWidth.intValue > textWidth.intValue
    }

    val density = LocalDensity.current
    val spaceBetweenPx = with(density) { spaceBetween.dp.toPx() }

    LaunchedEffect(textFits, textWidth.intValue, containerWidth.intValue) {
        if (!textFits && textWidth.intValue > 0 && containerWidth.intValue > 0) {
            delay(delayStart)
            scrollPosition.animateTo(targetValue = -textWidth.intValue.toFloat() - spaceBetweenPx,
                                     animationSpec = infiniteRepeatable(animation = tween(durationMillis = ((textWidth.intValue + containerWidth.intValue) / speed * 1000).toInt(),
                                                                                          easing = LinearEasing), repeatMode = RepeatMode.Restart))
        }
    }

    SubcomposeLayout(modifier = modifier.clipToBounds()) { constraints ->
        val mainText = subcompose("mainText") {
            Text(text = text, color = textColor, style = textStyle, maxLines = 1, overflow = TextOverflow.Clip, softWrap = false)
        }

        val mainTextPlaceable = mainText.first()
            .measure(Constraints(minWidth = 0, maxWidth = Constraints.Infinity, minHeight = constraints.minHeight, maxHeight = constraints.maxHeight))

        textWidth.intValue = mainTextPlaceable.width
        containerWidth.intValue = constraints.maxWidth

        if (textFits) {
            layout(constraints.maxWidth, mainTextPlaceable.height) {
                val xPosition = when (alignment) {
                    Alignment.Start -> 0
                    Alignment.End -> constraints.maxWidth - mainTextPlaceable.width
                    Alignment.CenterHorizontally -> (constraints.maxWidth - mainTextPlaceable.width) / 2
                    else -> 0
                }

                mainTextPlaceable.place(xPosition, 0)
            }
        } else {
            val spacing = subcompose("space") {
                Spacer(modifier = Modifier.width(spaceBetween.dp))
            }.first().measure(Constraints.fixed(spaceBetween, 0))

            val secondTextPlaceable = subcompose("secondText") {
                Text(text = text, color = textColor, style = textStyle, maxLines = 1, overflow = TextOverflow.Clip, softWrap = false)
            }.first()
                .measure(Constraints(minWidth = 0,
                                     maxWidth = Constraints.Infinity,
                                     minHeight = constraints.minHeight,
                                     maxHeight = constraints.maxHeight))

            layout(constraints.maxWidth, mainTextPlaceable.height) {
                val scrollPos = scrollPosition.value.toInt()
                val firstTextOffset = scrollPos % (textWidth.intValue + spaceBetweenPx.toInt())

                mainTextPlaceable.place(firstTextOffset, 0)
                spacing.place(firstTextOffset + textWidth.intValue, 0)
                secondTextPlaceable.place(firstTextOffset + textWidth.intValue + spaceBetweenPx.toInt(), 0)

                if (showGradientEdge) {
                    val startGradient = subcompose("startGradient") {
                        GradientEdge(startColor = gradientEdgeColor, endColor = Color.Transparent)
                    }.first().measure(Constraints.fixed(gradientEdgeWidth, mainTextPlaceable.height))

                    val endGradient = subcompose("endGradient") {
                        GradientEdge(startColor = Color.Transparent, endColor = gradientEdgeColor)
                    }.first().measure(Constraints.fixed(gradientEdgeWidth, mainTextPlaceable.height))

                    startGradient.place(0, 0)
                    endGradient.place(constraints.maxWidth - gradientEdgeWidth, 0)
                }
            }
        }
    }
}

@Composable
private fun GradientEdge(
    startColor: Color,
    endColor: Color,
) {
    Box(modifier = Modifier
        .fillMaxHeight()
        .background(brush = Brush.horizontalGradient(
                0f to startColor, 1f to endColor,
        )))
}

@Preview()
@Composable
fun MarqueeTextPreview() {
    E2MP3Theme {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(SurfacePrimary), horizontalAlignment = Alignment.CenterHorizontally) {
            STFMarqueeText(text = "This is a very long text that will need to scroll because it doesn't fit in the container width.",
                           alignment = Alignment.CenterHorizontally)

            Spacer(modifier = Modifier.height(16.dp))

            STFMarqueeText(text = "Short text", alignment = Alignment.CenterHorizontally)
        }
    }
}