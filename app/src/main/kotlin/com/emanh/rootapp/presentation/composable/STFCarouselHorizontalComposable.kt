package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body4Bold
import com.emanh.rootapp.presentation.theme.Body6Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.theme.Title4Bold
import com.emanh.rootapp.utils.MyConstant.carouselThumbData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class STFCarouselType {
    MusicBig, MusicBig2, MusicMedium, MusicSmall, Playlist, Artists
}

@Immutable
data class STFCarouselThumbData(
    val id: Long = 0, val imageUrl: String = "", val title: String = "", val subtitle: String = "", val description: String = ""
)

@Immutable
data class STFLayoutCarouselData(
    val onlyTitle: Boolean = true, val isPlaylist: Boolean = false, val isMusicMedium: Boolean = false, val isMusicSmall: Boolean = false
)

private fun layoutCarouselFactory(type: STFCarouselType): STFLayoutCarouselData {
    return when (type) {
        STFCarouselType.MusicBig -> STFLayoutCarouselData(onlyTitle = false)
        STFCarouselType.MusicBig2 -> STFLayoutCarouselData()
        STFCarouselType.MusicMedium -> STFLayoutCarouselData(isMusicMedium = true)
        STFCarouselType.MusicSmall -> STFLayoutCarouselData(isMusicSmall = true)
        STFCarouselType.Playlist -> STFLayoutCarouselData(isPlaylist = true)
        STFCarouselType.Artists -> STFLayoutCarouselData(onlyTitle = false)
    }
}

@Composable
fun STFCarouselHorizontal(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    userName: String = "",
    table: String = "",
    title: String = "",
    viewAll: String? = null,
    type: STFCarouselType,
    typeThumb: List<STFThumbType> = emptyList(),
    thumbItem: List<STFCarouselThumbData>,
    onAvatarClick: () -> Unit = {},
    onViewAll: () -> Unit = {},
    onThumbClick: (Long) -> Unit = {}
) {
    var toggled by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val layoutCategory = remember(type) { layoutCarouselFactory(type) }
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(initialValue = 1f,
                                                 targetValue = if (toggled) 0.75f else 1f,
                                                 animationSpec = infiniteRepeatable(tween(200), RepeatMode.Reverse))

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically) {
            if (!layoutCategory.onlyTitle) {
                STFAvatar(avatarUrl = avatarUrl, userName = userName, onClick = onAvatarClick, modifier = Modifier.size(48.dp))
            }

            Column(modifier = Modifier
                .weight(1f)
                .then(if (layoutCategory.onlyTitle) Modifier else Modifier.height(48.dp))) {
                if (!layoutCategory.onlyTitle) {
                    Text(text = table, color = TextSecondary, style = Body6Regular, maxLines = 1, overflow = TextOverflow.Ellipsis)

                    Spacer(modifier = Modifier.weight(1f))
                }

                Text(text = title, color = TextPrimary, style = Title4Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            viewAll?.let {
                Box {
                    Text(text = it,
                         color = TextSecondary,
                         style = Body4Bold,
                         maxLines = 1,
                         overflow = TextOverflow.Ellipsis,
                         modifier = Modifier
                             .graphicsLayer {
                                 scaleX = scale
                                 scaleY = scale
                                 transformOrigin = TransformOrigin.Center
                             }
                             .debounceClickable(indication = null) {
                                 coroutineScope.launch {
                                     toggled = true
                                     delay(100L)
                                     toggled = false
                                     onViewAll()
                                 }
                             })
                }
            }
        }

        LazyRow(modifier = Modifier.padding(bottom = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            itemsIndexed(thumbItem) { index, item ->
                if (layoutCategory.isPlaylist) {
                    STFThumbPlaylist(imageUrl = item.imageUrl, description = item.description, onClick = { onThumbClick(item.id) })
                } else {
                    STFThumb(imageUrl = item.imageUrl,
                             title = item.title,
                             subtitle = item.subtitle,
                             description = item.description,
                             type = when (type) {
                                 STFCarouselType.MusicBig2 -> STFThumbType.Music2
                                 STFCarouselType.Artists, STFCarouselType.MusicBig -> typeThumb[index]
                                 else -> STFThumbType.Music
                             },
                             size = if (layoutCategory.isMusicMedium) STFThumbSize.Medium
                             else if (layoutCategory.isMusicSmall) STFThumbSize.Small else STFThumbSize.Big,
                             onClick = { onThumbClick(item.id) })
                }
            }
        }
    }
}

@Preview
@Composable
fun CarouselHorizontalMusicBigPreview() {
    E2MP3Theme {
        STFCarouselHorizontal(userName = "emanh",
                              table = "Nội dung khác giống",
                              title = "Sơn Tùng M-TP",
                              type = STFCarouselType.Artists,
                              typeThumb = listOf(STFThumbType.Music,
                                                 STFThumbType.Artists,
                                                 STFThumbType.Music,
                                                 STFThumbType.Music2,
                                                 STFThumbType.Artists,
                                                 STFThumbType.Artists,
                                                 STFThumbType.Artists),
                              thumbItem = carouselThumbData,
                              onAvatarClick = {},
                              onThumbClick = {})
    }
}

@Preview
@Composable
fun CarouselHorizontalMusicBig2Preview() {
    E2MP3Theme {
        STFCarouselHorizontal(userName = "emanh",
                              table = "Nội dung khác giống",
                              title = "Sơn Tùng M-TP",
                              type = STFCarouselType.MusicBig2,
                              thumbItem = carouselThumbData,
                              onAvatarClick = {},
                              onThumbClick = {})
    }
}

@Preview
@Composable
fun CarouselHorizontalPlaylistBigPreview() {
    E2MP3Theme {
        STFCarouselHorizontal(title = "emanh", type = STFCarouselType.Playlist, thumbItem = carouselThumbData, onAvatarClick = {}, onThumbClick = {})
    }
}

@Preview
@Composable
fun CarouselHorizontalMusicMediumPreview() {
    E2MP3Theme {
        STFCarouselHorizontal(title = "Sơn Tùng M-TP",
                              type = STFCarouselType.MusicMedium,
                              thumbItem = carouselThumbData,
                              onAvatarClick = {},
                              onThumbClick = {})
    }
}

@Preview
@Composable
fun CarouselHorizontalMusicBSmallPreview() {
    E2MP3Theme {
        STFCarouselHorizontal(title = "Sơn Tùng M-TP",
                              viewAll = "Hiện tất cả",
                              type = STFCarouselType.MusicSmall,
                              thumbItem = carouselThumbData,
                              onAvatarClick = {},
                              onThumbClick = {})
    }
}