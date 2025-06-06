package com.emanh.rootapp.presentation.ui.upload.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.theme.Body4Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfaceFieldInvert
import com.emanh.rootapp.presentation.theme.SurfaceTertiary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary

@Composable
fun UploadTextField(modifier: Modifier = Modifier, inputText: String, placeholderId: Int, onInputTextChange: (String) -> Unit) {
    TextField(value = inputText,
              onValueChange = {
                  if (it.length <= 225) {
                      onInputTextChange(it)
                  }
              },
              placeholder = {
                  Text(text = stringResource(placeholderId), style = Body4Regular, color = TextSecondary)
              },
              textStyle = Body4Regular,
              maxLines = 1,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
              colors = TextFieldDefaults.colors(
                      focusedContainerColor = SurfaceFieldInvert,
                      unfocusedContainerColor = SurfaceTertiary,
                      disabledContainerColor = Color.Transparent,
                      focusedIndicatorColor = Color.Transparent,
                      unfocusedIndicatorColor = Color.Transparent,
                      cursorColor = TextPrimary,
                      focusedTextColor = TextPrimary,
                      unfocusedTextColor = TextPrimary,
              ),
              modifier = modifier
                  .fillMaxWidth()
                  .clip(shape = RoundedCornerShape(8.dp)))
}

@Preview
@Composable
private fun UploadTextFieldPreview() {
    E2MP3Theme {
        UploadTextField(inputText = "", placeholderId = R.string.view_profile, onInputTextChange = {})
    }
}