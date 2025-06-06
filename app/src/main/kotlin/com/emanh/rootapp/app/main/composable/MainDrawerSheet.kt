package com.emanh.rootapp.app.main.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.presentation.composable.STFAvatar
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body3Regular
import com.emanh.rootapp.presentation.theme.Body4Black
import com.emanh.rootapp.presentation.theme.Body4Regular
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceSeparator
import com.emanh.rootapp.presentation.theme.SurfaceTertiary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextTertiary
import com.emanh.rootapp.presentation.theme.Title5Bold

@Composable
fun MainDrawerSheet(modifier: Modifier = Modifier, currentUser: UserInfo, onUploadClick: () -> Unit, onLogoutClick: () -> Unit) {
    var showExitDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier.background(SurfaceTertiary)) {

        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            STFAvatar(modifier = Modifier.size(48.dp), avatarUrl = currentUser.avatarUrl, userName = currentUser.name, onClick = {})

            Column {
                Text(text = currentUser.username, color = TextPrimary, style = Title5Bold)

                Text(text = stringResource(R.string.view_profile), color = TextTertiary, style = Body6Regular)
            }
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = SurfaceSeparator)

        MainButton(titleId = R.string.new_release, iconId = R.drawable.ic_32_news, onClick = {})

        MainButton(titleId = R.string.recently, iconId = R.drawable.ic_32_cronology, onClick = {})

        MainButton(titleId = R.string.settings_and_privacy, iconId = R.drawable.ic_32_gear, onClick = {})

        if (currentUser.isArtist) {
            MainButton(titleId = R.string.add_song, iconId = R.drawable.ic_32_upload, onClick = onUploadClick)
        }

        MainButton(titleId = R.string.logout, iconId = R.drawable.ic_32_exit, onClick = { showExitDialog = true })
    }

    if (showExitDialog) {
        AlertDialog(
                onDismissRequest = { showExitDialog = false },
                containerColor = SurfaceTertiary,
                shape = RoundedCornerShape(16.dp),
                title = {
                    Text(text = stringResource(R.string.confirm_logout), color = TextPrimary, style = Title5Bold)
                },
                text = {
                    Text(text = stringResource(R.string.are_you_sure_you_want_to_logout), color = TextTertiary, style = Body4Regular)
                },
                confirmButton = {
                    TextButton(onClick = {
                        showExitDialog = false
                        onLogoutClick()
                    }) {
                        Text(stringResource(R.string.logout), color = TextPrimary, style = Body4Black)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) {
                        Text(stringResource(R.string.cancel), color = TextPrimary, style = Body4Black)
                    }
                },
        )
    }
}

@Composable
private fun MainButton(modifier: Modifier = Modifier, titleId: Int, iconId: Int, onClick: () -> Unit) {
    Box(modifier = modifier
        .fillMaxWidth()
        .debounceClickable(onClick = onClick)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(painter = painterResource(iconId), contentDescription = null, tint = IconPrimary, modifier = Modifier.size(32.dp))

            Text(text = stringResource(titleId), color = TextPrimary, style = Body3Regular)
        }
    }
}