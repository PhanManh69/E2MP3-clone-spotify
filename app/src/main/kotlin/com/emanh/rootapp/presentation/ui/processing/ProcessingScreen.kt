package com.emanh.rootapp.presentation.ui.processing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.theme.Body1Bold
import com.emanh.rootapp.presentation.theme.Body2Bold
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.TextPrimary

@Composable
fun ProcessingScreen(currentUser: UserInfo) {
    val viewModel = hiltViewModel<ProcessingViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(currentUser) {
        viewModel.getProcessingSongs(currentUser.id)
    }

    ProcessingScaffold(songsList = uiState.songsList, goBack = viewModel::goBack)
}

@Composable
private fun ProcessingScaffold(modifier: Modifier = Modifier, songsList: List<SongsModel>, goBack: () -> Unit) {
    Column(modifier = modifier
        .statusBarsPadding()
        .background(SurfacePrimary)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = goBack) {
                Icon(painter = painterResource(R.drawable.ic_24_musical_chevron_lt), contentDescription = null, tint = IconPrimary)
            }

            Text(text = "Bài hát đang chờ xác thực", color = TextPrimary, style = Body1Bold)

            Box(Modifier.size(40.dp))
        }
        if (songsList.isEmpty()) {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = "Không có bài hát đang chờ xác thực", color = TextPrimary, style = Body2Bold, modifier = Modifier.padding(bottom = 48.dp))
            }
        } else {
            LazyColumn(modifier = Modifier
                .weight(1f)
                .imePadding(), contentPadding = PaddingValues(vertical = 24.dp)) {

                items(items = songsList, key = { song -> song.id }) { song ->
                    STFItem(imageUrl = song.avatarUrl.orEmpty(),
                            title = song.title.orEmpty(),
                            label = song.subtitle.orEmpty(),
                            iconId = R.drawable.ic_24_bullet,
                            type = STFItemType.Music,
                            size = STFItemSize.Small)
                }
            }
        }
    }
}