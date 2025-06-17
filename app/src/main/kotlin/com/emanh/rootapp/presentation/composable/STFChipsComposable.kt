package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.theme.AlphaN00_0
import com.emanh.rootapp.presentation.theme.Body6Bold
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.Body7Bold
import com.emanh.rootapp.presentation.theme.Body7Regular
import com.emanh.rootapp.presentation.theme.ChipsSecondary
import com.emanh.rootapp.presentation.theme.ChipsSelect
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfaceAlpha0
import com.emanh.rootapp.presentation.theme.SurfaceInvert
import com.emanh.rootapp.presentation.theme.TextBackgroundDark
import com.emanh.rootapp.presentation.theme.TextPrimary

enum class STFChipsType {
    Default, Active, Stroke
}

enum class STFChipsSize {
    Normal, Small
}

@Immutable
data class STFChipStyle(
    val textStyle: TextStyle, val height: Dp, val border: Dp, val borderColor: Color
)

@Immutable
data class STFChipsResource(
    val height: Dp, val border: Dp, val textStyle: TextStyle, val textColor: Color, val backgroundColor: Color, val borderColor: Color
)

private fun chipsResourceFactory(size: STFChipsSize, state: STFChipsType): STFChipsResource {
    val (textStyle, height, border, borderColor) = when (size to state) {
        STFChipsSize.Normal to STFChipsType.Default -> STFChipStyle(Body6Regular, 32.dp, 0.dp, AlphaN00_0)
        STFChipsSize.Normal to STFChipsType.Active -> STFChipStyle(Body6Regular, 32.dp, 0.dp, AlphaN00_0)
        STFChipsSize.Normal to STFChipsType.Stroke -> STFChipStyle(Body6Bold, 32.dp, 1.dp, SurfaceInvert)

        STFChipsSize.Small to STFChipsType.Default -> STFChipStyle(Body7Regular, 30.dp, 0.dp, AlphaN00_0)
        STFChipsSize.Small to STFChipsType.Active -> STFChipStyle(Body7Regular, 30.dp, 0.dp, AlphaN00_0)
        STFChipsSize.Small to STFChipsType.Stroke -> STFChipStyle(Body7Bold, 30.dp, 1.dp, SurfaceInvert)

        else -> error("Unsupported size-state combination")
    }

    val textColor = when (state) {
        STFChipsType.Active -> TextBackgroundDark
        else -> TextPrimary
    }

    val backgroundColor = when (state) {
        STFChipsType.Default -> ChipsSecondary
        STFChipsType.Active -> ChipsSelect
        STFChipsType.Stroke -> SurfaceAlpha0
    }

    return STFChipsResource(height = height,
                            border = border,
                            textStyle = textStyle,
                            textColor = textColor,
                            backgroundColor = backgroundColor,
                            borderColor = borderColor)
}

@Composable
fun STFChips(
    modifier: Modifier = Modifier,
    text: String,
    size: STFChipsSize = STFChipsSize.Normal,
    type: STFChipsType = STFChipsType.Default,
    onClick: () -> Unit = {}
) {
    val shipsResource = remember(type) { chipsResourceFactory(size, type) }

    Box(modifier = modifier
        .height(shipsResource.height)
        .background(color = shipsResource.backgroundColor, shape = RoundedCornerShape(100))
        .border(width = shipsResource.border, color = shipsResource.borderColor, shape = RoundedCornerShape(100))
        .clip(shape = RoundedCornerShape(100))
        .clickable { onClick() }) {
        Text(text = text,
             style = shipsResource.textStyle,
             color = shipsResource.textColor,
             modifier = Modifier
                 .padding(horizontal = 16.dp)
                 .align(Alignment.Center))
    }
}

@Preview
@Composable
fun ChipsNormalDefaultPreview() {
    E2MP3Theme {
        STFChips(text = "Label", size = STFChipsSize.Normal, type = STFChipsType.Default, onClick = {})
    }
}

@Preview
@Composable
fun ChipsSmallDefaultPreview() {
    E2MP3Theme {
        STFChips(text = "Label", size = STFChipsSize.Small, type = STFChipsType.Default, onClick = {})
    }
}

@Preview
@Composable
fun ChipsNormalActivePreview() {
    E2MP3Theme {
        STFChips(text = "Label", size = STFChipsSize.Normal, type = STFChipsType.Active, onClick = {})
    }
}

@Preview
@Composable
fun ChipsSmallActivePreview() {
    E2MP3Theme {
        STFChips(text = "Label", size = STFChipsSize.Small, type = STFChipsType.Active, onClick = {})
    }
}

@Preview
@Composable
fun ChipsNormalStrokePreview() {
    E2MP3Theme {
        STFChips(text = "Label", size = STFChipsSize.Normal, type = STFChipsType.Stroke, onClick = {})
    }
}

@Preview
@Composable
fun ChipsSmallStrokePreview() {
    E2MP3Theme {
        STFChips(text = "Label", size = STFChipsSize.Small, type = STFChipsType.Stroke, onClick = {})
    }
}