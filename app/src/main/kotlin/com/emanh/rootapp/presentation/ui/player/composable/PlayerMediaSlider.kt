package com.emanh.rootapp.presentation.ui.player.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfaceAlphaInvert
import com.emanh.rootapp.presentation.theme.SurfaceBackgroundLight
import com.emanh.rootapp.presentation.theme.TextTertiaryAlpha60

@Composable
fun PlayerMediaSlider(
    modifier: Modifier = Modifier,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    currentTime: String,
    remainingTime: String,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (Float) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var sliderSize by remember { mutableStateOf(IntSize.Zero) }
    val localDensity = LocalDensity.current

    Column(modifier = modifier.padding(horizontal = 24.dp)) {
        Box(modifier = Modifier
            .height(16.dp)
            .onGloballyPositioned { coordinates ->
                sliderSize = coordinates.size
            }) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(SurfaceAlphaInvert))

            Box(modifier = Modifier
                .width(with(localDensity) { (sliderSize.width * value / (valueRange.endInclusive - valueRange.start)).toDp() })
                .height(4.dp)
                .align(Alignment.CenterStart)
                .clip(CircleShape)
                .background(SurfaceBackgroundLight))

            Box(modifier = Modifier
                .offset(x = with(localDensity) {
                    (sliderSize.width * value / (valueRange.endInclusive - valueRange.start) - 6.dp.toPx()).toDp()
                })
                .size(12.dp)
                .align(Alignment.CenterStart)
                .clip(CircleShape)
                .background(SurfaceBackgroundLight))

            Slider(value = value,
                   onValueChange = onValueChange,
                   onValueChangeFinished = {
                       onValueChangeFinished(value)
                   },
                   valueRange = valueRange,
                   modifier = Modifier.fillMaxWidth(),
                   interactionSource = interactionSource,
                   colors = SliderDefaults.colors(thumbColor = Color.Transparent,
                                                  activeTrackColor = Color.Transparent,
                                                  inactiveTrackColor = Color.Transparent))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = currentTime, color = TextTertiaryAlpha60, style = Body6Regular)

            Text(text = remainingTime, color = TextTertiaryAlpha60, style = Body6Regular)
        }
    }
}

@Preview()
@Composable
fun PlayerMediaSliderPreview() {
    var sliderPosition by remember { mutableFloatStateOf(0.6f) }

    E2MP3Theme {
        PlayerMediaSlider(value = sliderPosition,
                          onValueChange = { sliderPosition = it },
                          onValueChangeFinished = {},
                          currentTime = "1:52",
                          remainingTime = "-1:03")
    }
}