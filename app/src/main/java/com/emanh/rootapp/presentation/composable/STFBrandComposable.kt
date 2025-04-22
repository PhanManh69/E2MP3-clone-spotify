package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.theme.Body4Black
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.Heading
import com.emanh.rootapp.presentation.theme.SurfaceInvert

@Composable
fun STFBrand(modifier: Modifier = Modifier, showName: Boolean = true) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Icon(painter = painterResource(id = R.drawable.ic_logo_app),
             contentDescription = null,
             tint = SurfaceInvert,
             modifier = Modifier.size(107.dp))

        if (showName) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.spotify), style = Heading, color = SurfaceInvert)

                Text(text = stringResource(R.string.r), style = Body4Black, color = SurfaceInvert, modifier = Modifier.padding(bottom = 12.dp))
            }
        }
    }
}

@Preview
@Composable
fun BrandPreview() {
    E2MP3Theme {
        STFBrand(showName = false)
    }
}

@Preview
@Composable
fun BrandShowNamePreview() {
    E2MP3Theme {
        STFBrand()
    }
}