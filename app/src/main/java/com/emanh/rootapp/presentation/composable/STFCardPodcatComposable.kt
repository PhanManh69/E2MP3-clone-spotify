package com.emanh.rootapp.presentation.composable

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil3.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.emanh.rootapp.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.composable.utils.shadowCustom
import com.emanh.rootapp.presentation.composable.utils.shimmerEffect
import com.emanh.rootapp.presentation.theme.AlphaN00_50
import com.emanh.rootapp.presentation.theme.Body6Bold
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundPrimary
import com.emanh.rootapp.presentation.theme.IconProduct
import com.emanh.rootapp.presentation.theme.SurfaceProductSuperDark
import com.emanh.rootapp.presentation.theme.TextBackgroundPrimary
import com.emanh.rootapp.presentation.theme.TextBackgroundSecondary
import com.emanh.rootapp.presentation.theme.Title4Bold
import com.emanh.rootapp.utils.MyConstant.IMAGE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

private const val TAG = "STFCardPodcat"

@Composable
fun STFCardPodcat(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    description: String,
    date: String,
    time: String,
    content: String,
    imageUrl: String,
    isLiked: Boolean,
    onAddPlaylist: (Boolean) -> Unit = {}
) {
    var backgroundColor by remember { mutableStateOf(SurfaceProductSuperDark) }
    var palette by remember { mutableStateOf<Palette?>(null) }

    val liked = remember(isLiked) { mutableStateOf(isLiked) }

    LaunchedEffect(imageUrl) {
        try {
            val bitmap = withContext(Dispatchers.IO) {
                try {
                    val connection = URL(imageUrl).openConnection()
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000
                    connection.connect()

                    val inputStream = connection.getInputStream()
                    BitmapFactory.decodeStream(inputStream)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to download image: ${e.message}")
                    null
                }
            }

            if (bitmap != null) {
                try {
                    val generatedPalette = Palette.from(bitmap).generate()
                    palette = generatedPalette

                    val selectedColor = when {
                        generatedPalette.darkVibrantSwatch != null -> generatedPalette.darkVibrantSwatch?.rgb

                        generatedPalette.darkMutedSwatch != null -> generatedPalette.darkMutedSwatch?.rgb

                        generatedPalette.vibrantSwatch != null -> generatedPalette.vibrantSwatch?.rgb

                        generatedPalette.mutedSwatch != null -> generatedPalette.mutedSwatch?.rgb

                        else -> generatedPalette.dominantSwatch?.rgb
                    }

                    if (selectedColor != null) backgroundColor = Color(selectedColor)
                } catch (e: Exception) {
                    Log.e(TAG, "Palette generation failed: ${e.message}")
                }
            } else {
                Log.e(TAG, "Failed to decode bitmap from URL")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in LaunchedEffect: ${e.message}")
        }
    }

    Column(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(top = 24.dp, bottom = 8.dp)
        .clip(shape = RoundedCornerShape(16.dp))
        .background(backgroundColor)) {
        STFCardPodcatTitle(title = title, subtitle = subtitle, description = description, liked = liked, onAddPlaylist = onAddPlaylist)

        STFCardPodcatImage(imageUrl = imageUrl)

        STFCardPodcatInfo(date = date, time = time, content = content)

        Spacer(modifier = Modifier.height(12.dp))

        STFCardPodcatButtons()
    }
}

@Composable
private fun STFCardPodcatTitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    description: String,
    liked: MutableState<Boolean>,
    onAddPlaylist: (Boolean) -> Unit
) {
    Row(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = TextBackgroundPrimary, style = Title4Bold)

            Text(text = "$subtitle • $description", color = TextBackgroundSecondary, style = Body6Regular)
        }

        Icon(painter = painterResource(if (liked.value) R.drawable.ic_24_plus_check else R.drawable.ic_24_plus_circle),
             contentDescription = null,
             tint = if (liked.value) IconProduct else IconBackgroundPrimary,
             modifier = Modifier
                 .clip(shape = RoundedCornerShape(8.dp))
                 .debounceClickable {
                     liked.value = true
                     onAddPlaylist(liked.value)
                 })
    }
}

@Composable
private fun STFCardPodcatImage(modifier: Modifier = Modifier, imageUrl: String) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.Asset("equalizer_illu.lottie"))

    Box(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(vertical = 44.dp), contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.fillMaxWidth()) {
            LottieAnimation(composition = lottieComposition,
                            modifier = Modifier
                                .weight(1f)
                                .height(32.dp)
                                .offset(x = 20.dp)
                                .alpha(0.4f),
                            contentScale = ContentScale.Crop,
                            iterations = LottieConstants.IterateForever)

            LottieAnimation(composition = lottieComposition,
                            modifier = Modifier
                                .weight(1f)
                                .height(32.dp)
                                .offset(x = (-20).dp)
                                .alpha(0.4f),
                            contentScale = ContentScale.Crop,
                            iterations = LottieConstants.IterateForever)
        }

        SubcomposeAsyncImage(model = imageUrl, contentDescription = null, contentScale = ContentScale.Crop, loading = {
            Box(modifier = Modifier
                .shimmerEffect()
                .shadowCustom(shapeRadius = 8.dp)
                .size(118.dp)
                .clip(shape = RoundedCornerShape(8.dp)))
        }, modifier = Modifier
            .shadowCustom(shapeRadius = 8.dp)
            .size(118.dp)
            .clip(shape = RoundedCornerShape(8.dp)))
    }
}

@Composable
private fun STFCardPodcatInfo(modifier: Modifier = Modifier, date: String, time: String, content: String) {
    Text(text = buildAnnotatedString {
        withStyle(style = Body6Bold.copy(color = TextBackgroundPrimary).toSpanStyle()) {
            append(date)
            append(" • ")
            append(time)
            append(" • ")
        }
        withStyle(style = Body6Regular.copy(color = TextBackgroundSecondary).toSpanStyle()) {
            append(content)
        }
    }, maxLines = 4, overflow = TextOverflow.Ellipsis, modifier = modifier.padding(horizontal = 16.dp))
}

@Composable
private fun STFCardPodcatButtons(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier
            .clip(shape = RoundedCornerShape(100))
            .background(color = AlphaN00_50, shape = RoundedCornerShape(100))
            .debounceClickable { }, verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.ic_24_sound_off),
                 contentDescription = null,
                 tint = IconBackgroundPrimary,
                 modifier = Modifier
                     .padding(vertical = 8.dp)
                     .padding(start = 12.dp)
                     .clip(shape = RoundedCornerShape(8.dp))
                     .debounceClickable { })

            Text(text = stringResource(R.string.preview_episoe),
                 color = TextBackgroundPrimary,
                 style = Body6Regular,
                 modifier = Modifier.padding(start = 8.dp, end = 12.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(painter = painterResource(R.drawable.ic_24_bullet),
             contentDescription = null,
             tint = IconBackgroundPrimary,
             modifier = Modifier
                 .clip(shape = RoundedCornerShape(8.dp))
                 .debounceClickable { })

        Image(painter = painterResource(R.drawable.ic_42_play),
              contentDescription = null,
              modifier = Modifier
                  .clip(shape = RoundedCornerShape(100))
                  .debounceClickable { })
    }
}

@Preview
@Composable
fun CardPodcatPreview() {
    E2MP3Theme {
        STFCardPodcat(title = "Title",
                      subtitle = "Subtitle",
                      description = "Description",
                      date = "24 thg 2, 2025",
                      time = "22min",
                      content = "Nói về những cấu chuyên chúng ta yêu một người nào đó mà không mong đợi được sự hồi đáp từ đối phương",
                      imageUrl = IMAGE_URL,
                      isLiked = false)
    }
}