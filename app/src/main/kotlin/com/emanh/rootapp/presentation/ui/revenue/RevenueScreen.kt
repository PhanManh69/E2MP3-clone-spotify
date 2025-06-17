package com.emanh.rootapp.presentation.ui.revenue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.STFItem
import com.emanh.rootapp.presentation.composable.STFItemSize
import com.emanh.rootapp.presentation.composable.STFItemType
import com.emanh.rootapp.presentation.composable.STFLoading
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body1Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.utils.MyConstant.NOT_AVATAR
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import com.emanh.rootapp.utils.MyConstant.fakeSongs

@Composable
fun RevenueScreen(currentUser: UserInfo) {
    val viewModel = hiltViewModel<RevenueViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(currentUser) {
        viewModel.getYourSong(currentUser.id)
    }

    if (uiState.isLoading) {
        STFLoading()
    } else {
        RevenueScaffold(
                yourSong = uiState.yourSong,
                goBack = viewModel::goBack,
                onItemClick = {
                    viewModel.onItemClick(it)
                },
        )
    }
}

@Composable
private fun RevenueScaffold(
    modifier: Modifier = Modifier,
    yourSong: List<SongsModel>,
    goBack: () -> Unit,
    onItemClick: (Long) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier) {
        Column(modifier = Modifier
            .statusBarsPadding()
            .background(SurfacePrimary)
            .debounceClickable(indication = null, onClick = {
                focusManager.clearFocus()
            })) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = goBack) {
                    Icon(painter = painterResource(R.drawable.ic_24_musical_chevron_lt), contentDescription = null, tint = IconPrimary)
                }

                Text(text = stringResource(R.string.your_revenue), color = TextPrimary, style = Body1Bold)

                Box(Modifier.size(40.dp))
            }

            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 16.dp, bottom = PADDING_BOTTOM_BAR.dp)) {
                items(yourSong) { item ->
                    STFItem(imageUrl = item.avatarUrl ?: NOT_AVATAR,
                            title = item.title.orEmpty(),
                            label = item.subtitle.orEmpty(),
                            type = STFItemType.Music,
                            size = STFItemSize.Big,
                            onItemClick = {
                                onItemClick(item.id)
                            })
                }
            }
        }
    }
}

@Preview
@Composable
private fun RevenuePreview() {
    E2MP3Theme {
        RevenueScaffold(yourSong = fakeSongs, goBack = {}, onItemClick = {})
    }
}