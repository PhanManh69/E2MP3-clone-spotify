package com.emanh.rootapp.presentation.ui.revenuedetails.composable

import android.graphics.Color
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.Body3Regular
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.ProductG56
import com.emanh.rootapp.presentation.theme.ProductG64
import com.emanh.rootapp.presentation.theme.SpecialR63
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceSeek
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.theme.Title2Bold
import com.emanh.rootapp.utils.MyConstant.CUSTOM_SHADER
import com.emanh.rootapp.utils.MyConstant.fakeSongs

@Composable
fun RevenueDetailsInfo(
    modifier: Modifier = Modifier,
    totalAmount: String,
    fluctuations: String,
    isIncrease: Boolean,
    song: SongsModel,
    onAllClick: () -> Unit,
    onTotalAmountClick: () -> Unit,
    onWithdrawMoneyClick: () -> Unit,
    onRevenueClick: () -> Unit,
) {
    var isImageLoaded by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(24.dp))
        .border(width = 1.dp, color = SurfaceProduct, shape = RoundedCornerShape(24.dp))
        .then(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Modifier.drawWithCache {
            val shader = RuntimeShader(CUSTOM_SHADER)
            val shaderBrush = ShaderBrush(shader)
            shader.setFloatUniform("resolution", size.width, size.height)
            onDrawBehind {
                shader.setColorUniform("color1", Color.valueOf(ProductG64.red, ProductG64.green, ProductG64.blue, ProductG64.alpha))
                shader.setColorUniform("color2", Color.valueOf(ProductG56.red, ProductG56.green, ProductG56.blue, ProductG56.alpha))
                drawRect(shaderBrush)
            }
        } else Modifier.background(color = SurfaceProduct, shape = RoundedCornerShape(24.dp)))) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AsyncImage(modifier = Modifier
                    .size(92.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .then(if (!isImageLoaded) Modifier.shimmerEffect() else Modifier),
                           model = ImageRequest.Builder(LocalContext.current)
                               .data(song.avatarUrl)
                               .crossfade(true)
                               .listener(onSuccess = { _, _ -> isImageLoaded = true }, onError = { _, _ -> })
                               .build(),
                           contentDescription = null,
                           contentScale = ContentScale.Crop)

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "${stringResource(R.string.your_current_amount)} (USD)", color = TextSecondary, style = Body3Regular)

                    Text(text = totalAmount, color = TextPrimary, style = Title2Bold)

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Box(modifier = Modifier.background(color = if (isIncrease) SurfaceProduct else SpecialR63,
                                                           shape = RoundedCornerShape(8.dp))) {
                            Text(text = fluctuations,
                                 color = TextPrimary,
                                 style = Body6Regular,
                                 modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp))
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = song.title.orEmpty(), color = TextPrimary, style = Body3Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)

                Text(text = song.subtitle.orEmpty(), color = TextSecondary, style = Body6Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                RevenueDetailsButton(iconId = R.drawable.ic_24_all, titleId = R.string.all, onClick = onAllClick)

                RevenueDetailsButton(iconId = R.drawable.ic_24_counter, titleId = R.string.total_amount, onClick = onTotalAmountClick)

                RevenueDetailsButton(iconId = R.drawable.ic_24_payment_arrow_down, titleId = R.string.withdraw_money, onClick = onWithdrawMoneyClick)

                RevenueDetailsButton(iconId = R.drawable.ic_24_payments, titleId = R.string.revenue, onClick = onRevenueClick)
            }
        }
    }
}

@Composable
private fun RevenueDetailsButton(modifier: Modifier = Modifier, iconId: Int, titleId: Int, onClick: () -> Unit) {
    Column(modifier = modifier.width(72.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = SurfaceSeek, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick), contentAlignment = Alignment.Center) {
            Icon(painter = painterResource(iconId), contentDescription = stringResource(titleId), tint = TextPrimary, modifier = Modifier.size(24.dp))
        }

        Text(text = stringResource(titleId), color = TextPrimary, style = Body6Regular)
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun RevenueDetailsInfoPreview1() {
    E2MP3Theme {
        RevenueDetailsInfo(totalAmount = "$2,402.03",
                           fluctuations = "+2.24%",
                           isIncrease = true,
                           song = fakeSongs.first(),
                           onAllClick = {},
                           onTotalAmountClick = {},
                           onWithdrawMoneyClick = {},
                           onRevenueClick = {})
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun RevenueDetailsInfoPreview2() {
    E2MP3Theme {
        RevenueDetailsInfo(totalAmount = "$2,402.03",
                           fluctuations = "-2.24%",
                           isIncrease = false,
                           song = fakeSongs.first(),
                           onAllClick = {},
                           onTotalAmountClick = {},
                           onWithdrawMoneyClick = {},
                           onRevenueClick = {})
    }
}