package com.emanh.rootapp.app.main.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emanh.rootapp.data.db.entity.UserInfo
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.STFPlayerSticky
import com.emanh.rootapp.presentation.composable.STFPlayerStickyEmpty
import com.emanh.rootapp.presentation.composable.STFPlayerStickyLoading
import com.emanh.rootapp.presentation.composable.navbar.STFTabbar
import com.emanh.rootapp.presentation.ui.player.PlayerScreen

@Composable
fun MainBottomButton(
    modifier: Modifier = Modifier,
    currentProgress: Float,
    timeline: Long,
    headerTitle: String,
    headerSubtitle: String,
    isLoading: Boolean,
    isPlayed: Boolean,
    showPlayerSheet: Boolean,
    currentUser: UserInfo,
    single: SongsModel?,
    navController: NavHostController,
    artistsList: List<UsersModel>,
    onPlayerStickyClick: () -> Unit,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit,
    onValueTimeLineChange: (Float) -> Unit,
    onSliderPositionChangeFinished: (Float) -> Unit,
    onPlayPauseClick: (isPlayed: Boolean) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (single != null) {
            if (isLoading) {
                STFPlayerStickyLoading()
            } else {
                STFPlayerSticky(song = single,
                                isPlayed = isPlayed,
                                currentProgress = currentProgress,
                                onMusicalClick = {},
                                onPlayerStickyClick = onPlayerStickyClick,
                                onPlayPauseClick = onPlayPauseClick)
            }

            if (showPlayerSheet) {
                PlayerScreen(headerTitle = headerTitle,
                             headerSubtitle = headerSubtitle,
                             totalDuration = timeline,
                             currentProgress = currentProgress,
                             isPlayed = isPlayed,
                             currentUser = currentUser,
                             song = single,
                             artistsList = artistsList,
                             onBackClick = onBackClick,
                             onForwardClick = onForwardClick,
                             onPlayPauseClick = onPlayPauseClick,
                             onValueChange = onValueTimeLineChange,
                             onValueChangeFinished = onSliderPositionChangeFinished)
            }
        } else {
            STFPlayerStickyEmpty()
        }

        STFTabbar(navController = navController)
    }
}