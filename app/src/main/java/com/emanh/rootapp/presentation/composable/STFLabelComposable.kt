package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.emanh.rootapp.presentation.theme.Body5Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfaceAlpha0
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.TextSecondary

enum class STFLabelType {
    Default, Selected
}

private fun getStrokeColorFactory(type: STFLabelType): Color {
    return when (type) {
        STFLabelType.Default -> SurfaceAlpha0
        STFLabelType.Selected -> SurfaceProduct
    }
}

@Composable
fun STFLabel(modifier: Modifier = Modifier, title: String, type: STFLabelType = STFLabelType.Default, onClick: () -> Unit = {}) {
    val current = LocalDensity.current
    val strokeColor = remember(type) { getStrokeColorFactory(type) }
    val titleDirection = remember { mutableStateOf(Size.Zero) }

    Box(modifier = modifier
        .clip(shape = RoundedCornerShape(8.dp))
        .clickable { onClick() }) {
        Column(modifier = Modifier.padding(horizontal = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = title,
                 color = TextSecondary,
                 style = Body5Regular,
                 modifier = Modifier.onGloballyPositioned { titleDirection.value = it.size.toSize() })

            HorizontalDivider(thickness = 2.dp,
                              color = strokeColor,
                              modifier = Modifier.width(with(current) { titleDirection.value.width.toDp() }))
        }
    }
}

@Preview
@Composable
fun LabelDefaultPreview() {
    E2MP3Theme {
        STFLabel(title = "Label", type = STFLabelType.Default, onClick = {})
    }
}

@Preview
@Composable
fun LabelSelectedPreview() {
    E2MP3Theme {
        STFLabel(title = "Label", type = STFLabelType.Selected, onClick = {})
    }
}