package com.emanh.rootapp.presentation.ui.revenuedetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.STFLoading
import com.emanh.rootapp.presentation.theme.Body1Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SpecialR63
import com.emanh.rootapp.presentation.theme.SpecialR72
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceProductDark
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.ui.revenuedetails.composable.RevenueDetailsChart
import com.emanh.rootapp.presentation.ui.revenuedetails.composable.RevenueDetailsInfo
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import com.emanh.rootapp.utils.MyConstant.fakeSongs

@Composable
fun RevenueDetailScreen() {
    val viewModel = hiltViewModel<RevenueDetailViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading && uiState.song == null) {
        STFLoading()
    } else {
        RevenueDetailsScaffold(song = uiState.song!!,
                               revenueList = uiState.revenueList.random(),
                               goBack = {},
                               onAllClick = {},
                               onTotalAmountClick = {},
                               onWithdrawMoneyClick = {},
                               onRevenueClick = {})
    }
}

@Composable
private fun RevenueDetailsScaffold(
    modifier: Modifier = Modifier,
    song: SongsModel,
    revenueList: RevenueData,
    goBack: () -> Unit,
    onAllClick: () -> Unit,
    onTotalAmountClick: () -> Unit,
    onWithdrawMoneyClick: () -> Unit,
    onRevenueClick: () -> Unit,
) {
    Column(modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()
        .background(SurfacePrimary)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = goBack) {
                Icon(painter = painterResource(R.drawable.ic_24_musical_chevron_lt), contentDescription = null, tint = IconPrimary)
            }

            Text(text = stringResource(R.string.your_revenue_details), color = TextPrimary, style = Body1Bold)

            Box(Modifier.size(40.dp))
        }

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp),
                   contentPadding = PaddingValues(top = 24.dp, bottom = PADDING_BOTTOM_BAR.dp),
                   verticalArrangement = Arrangement.spacedBy(24.dp)) {
            item {
                RevenueDetailsInfo(totalAmount = revenueList.totalAmount,
                                   fluctuations = revenueList.fluctuations,
                                   isIncrease = revenueList.isIncrease,
                                   song = song,
                                   onAllClick = onAllClick,
                                   onTotalAmountClick = onTotalAmountClick,
                                   onWithdrawMoneyClick = onWithdrawMoneyClick,
                                   onRevenueClick = onRevenueClick)
            }

            item {
                RevenueDetailsChart(modifier = Modifier.height(400.dp),
                                    data = revenueList.sampleData,
                                    lineColor = if (revenueList.isIncrease) SurfaceProduct else SpecialR63,
                                    pointColor = if (revenueList.isIncrease) SurfaceProductDark else SpecialR72)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun RevenueDetailsPreview1() {
    E2MP3Theme {
        val revenueList = RevenueData(id = 0,
                                      totalAmount = "$2,402.03",
                                      fluctuations = "+12.78%",
                                      isIncrease = true,
                                      sampleData = listOf(10f, 25.7f, 20.5f, 30f, 45f, 40f, 53.2f, 60f))

        RevenueDetailsScaffold(song = fakeSongs.first(),
                               revenueList = revenueList,
                               goBack = {},
                               onAllClick = {},
                               onTotalAmountClick = {},
                               onWithdrawMoneyClick = {},
                               onRevenueClick = {})
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun RevenueDetailsPreview2() {
    E2MP3Theme {
        val revenueList = RevenueData(id = 2,
                                      totalAmount = "$8,182.18",
                                      fluctuations = "-2.23%",
                                      isIncrease = false,
                                      sampleData = listOf(32.3f, 42.1f, 45f, 72.3f, 80.2f, 78.2f, 76.1f, 74.4f))

        RevenueDetailsScaffold(song = fakeSongs.first(),
                               revenueList = revenueList,
                               goBack = {},
                               onAllClick = {},
                               onTotalAmountClick = {},
                               onWithdrawMoneyClick = {},
                               onRevenueClick = {})
    }
}