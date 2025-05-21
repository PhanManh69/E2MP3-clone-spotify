package com.emanh.rootapp.presentation.ui.player.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body5Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.Title5Bold

@Composable
fun PlayerLyrics(modifier: Modifier = Modifier, lyrics: String?, backgroundColor: Color, onShowLyrics: () -> Unit) {
    Box(modifier = modifier
        .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
        .debounceClickable(indication = null, onClick = onShowLyrics)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = stringResource(R.string.lirics_song), color = TextPrimary, style = Body5Bold)

            Box {
                if (lyrics == null) {
                    Text(text = stringResource(R.string.not_lyrics_song),
                         color = TextPrimary,
                         style = Title5Bold,
                         textAlign = TextAlign.Center,
                         modifier = Modifier.padding(vertical = 16.dp))
                } else {

                    Text(text = lyrics, color = TextBackgroundPrimary, style = Title5Bold, maxLines = 8)
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .align(Alignment.TopCenter)
                    .background(brush = Brush.verticalGradient(0f to backgroundColor, 1f to Color.Transparent)))

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .align(Alignment.BottomCenter)
                    .background(brush = Brush.verticalGradient(0f to Color.Transparent, 1f to backgroundColor)))
            }
        }
    }
}

@Preview
@Composable
private fun PlayerLyricsPreview() {
    E2MP3Theme {
        PlayerLyrics(lyrics = "Em là ai từ đâu bước đến nơi đây dịu dàng chân phương (dịu dàng chân phương)\n" + "Em là ai tựa như ánh nắng ban mai ngọt ngào trong sương (ngọt ngào trong sương)\n" + "Ngắm em thật lâu, con tim anh yếu mềm\n" + "Đắm say từ phút đó, từng giây trôi yêu thêm\n" + "\n" + "Bao ngày qua bình minh đánh thức xua tan bộn bề nơi anh (bộn bề nơi anh)\n" + "Bao ngày qua niềm thương nỗi nhớ bay theo bầu trời trong xanh (bầu trời trong xanh)\n" + "Liếc đôi hàng mi mong manh, anh thẫn thờ\n" + "Muốn hôn nhẹ mái tóc, bờ môi em, anh mơ\n" + "\n" + "Cầm tay anh (anh), dựa vai anh (anh)\n" + "Kề bên anh, nơi này có anh\n" + "Gió mang câu tình ca, ngàn ánh sao vụt qua, nhẹ ôm lấy em\n" + "(Yêu em, thương em, con tim anh chân thành)\n" + "Cầm tay anh (anh), dựa vai anh (anh)\n" + "Kề bên anh, nơi này có anh\n" + "Khép đôi mi thật lâu\n" + "Nguyện mãi bên cạnh nhau, yêu say đắm như ngày đầu\n" + "\n" + "Mùa xuân đến bình yên, cho anh những giấc mơ\n" + "Hạ lưu giữ ngày mưa, ngọt ngào nên thơ\n" + "Mùa thu lá vàng rơi, đông sang, anh nhớ em",
                     backgroundColor = Color.DarkGray,
                     onShowLyrics = {})
    }
}