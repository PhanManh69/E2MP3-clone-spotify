package com.emanh.rootapp.presentation.ui.signup.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.theme.Body1Bold
import com.emanh.rootapp.presentation.theme.Body4Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceFieldInvert
import com.emanh.rootapp.presentation.theme.SurfaceTertiary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary

@Composable
fun SignUpTextField(
    modifier: Modifier = Modifier,
    inputText: String,
    titleId: Int,
    placeholderId: Int,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onInputTextChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        Text(text = stringResource(titleId), color = TextPrimary, style = Body1Bold, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(8.dp))

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
                  keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                  visualTransformation = if (isPassword) {
                      if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                  } else VisualTransformation.None,
                  trailingIcon = {
                      val icon = if (passwordVisible) R.drawable.ic_24_visibility else R.drawable.ic_24_visibility_off
                      if (isPassword) {
                          Icon(painter = painterResource(id = icon),
                               contentDescription = null,
                               tint = IconPrimary,
                               modifier = Modifier
                                   .clip(RoundedCornerShape(8.dp))
                                   .clickable { passwordVisible = !passwordVisible })
                      }
                  },
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
}

@Preview
@Composable
private fun SignUpTextFieldPreview1() {
    E2MP3Theme {
        SignUpTextField(inputText = "",
                        titleId = R.string.fullname,
                        placeholderId = R.string.enter_title_song,
                        isPassword = false,
                        onInputTextChange = {})
    }
}

@Preview
@Composable
private fun SignUpTextFieldPreview2() {
    E2MP3Theme {
        SignUpTextField(inputText = "",
                        titleId = R.string.password,
                        placeholderId = R.string.enter_password,
                        isPassword = true,
                        onInputTextChange = {})
    }
}