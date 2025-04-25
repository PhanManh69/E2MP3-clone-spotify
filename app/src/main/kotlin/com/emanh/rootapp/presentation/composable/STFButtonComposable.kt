package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.theme.Body4Black
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfaceAlpha0
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceStrokeSecondary
import com.emanh.rootapp.presentation.theme.TextBackgroundDark
import com.emanh.rootapp.presentation.theme.TextPrimary

enum class STFButtonType {
    Primary, Secondary
}

@Composable
fun STFButton(
    modifier: Modifier = Modifier, text: String, type: STFButtonType = STFButtonType.Primary, onClick: () -> Unit = {}
) {
    when (type) {
        STFButtonType.Primary -> Button(onClick = onClick,
                                        modifier = modifier,
                                        shape = RoundedCornerShape(100),
                                        colors = ButtonDefaults.buttonColors(contentColor = TextBackgroundDark, containerColor = SurfaceProduct)) {
            Text(text = text, textAlign = TextAlign.Center, style = Body4Black)
        }

        STFButtonType.Secondary -> Button(onClick = onClick,
                                          modifier = modifier,
                                          shape = RoundedCornerShape(100),
                                          border = BorderStroke(1.dp, SurfaceStrokeSecondary),
                                          colors = ButtonDefaults.buttonColors(contentColor = TextPrimary, containerColor = SurfaceAlpha0)) {
            Text(text = text, textAlign = TextAlign.Center, style = Body4Black)
        }
    }
}

@Preview
@Composable
fun ButtonPrimaryPreview() {
    E2MP3Theme {
        STFButton(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), text = "Button", type = STFButtonType.Primary, onClick = {})
    }
}

@Preview
@Composable
fun ButtonSecondaryPreview() {
    E2MP3Theme {
        STFButton(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), text = "Button", type = STFButtonType.Secondary, onClick = {})
    }
}