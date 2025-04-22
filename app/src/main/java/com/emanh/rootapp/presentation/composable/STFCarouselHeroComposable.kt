package com.emanh.rootapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.theme.Body2Bold
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.utils.MyConstant.carouselHeroThumbData
enum class STFCarouselHeroType {
    Album, Artist, ArtistCircle, Song, Playlist
}

@Immutable
data class STFCarouselHeroThumbData(val id: Int = 0, val imageUrl: String = "", val title: String = "", val subtitle: String = "")

@Immutable
data class STFLayoutCarouselHeroData(
    val isAlbum: Boolean = false,
    val isArtist: Boolean = false,
    val isArtistCircle: Boolean = false,
    val isSong: Boolean = false,
    val isPlaylist: Boolean = false
)

private fun layoutCarouselHeroFactory(type: STFCarouselHeroType): STFLayoutCarouselHeroData {
    return when (type) {
        STFCarouselHeroType.Album -> STFLayoutCarouselHeroData(isAlbum = true)
        STFCarouselHeroType.Artist -> STFLayoutCarouselHeroData(isArtist = true)
        STFCarouselHeroType.ArtistCircle -> STFLayoutCarouselHeroData(isArtistCircle = true)
        STFCarouselHeroType.Playlist -> STFLayoutCarouselHeroData(isPlaylist = true)
        STFCarouselHeroType.Song -> STFLayoutCarouselHeroData(isSong = true)
    }
}

@Composable
fun STFCarouselHero(
    modifier: Modifier = Modifier,
    title: String,
    type: STFCarouselHeroType,
    thumbItem: List<STFCarouselHeroThumbData>,
    onThumbClick: (Int) -> Unit = {},
) {
    val layoutCategoryHero = remember(type) { layoutCarouselHeroFactory(type) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, color = TextPrimary, style = Body2Bold, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
        }

        LazyRow(modifier = Modifier.padding(bottom = 32.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(thumbItem) { item ->
                STFThumbHero(imageUrl = item.imageUrl,
                             title = item.title,
                             subtitle = if (!layoutCategoryHero.isArtistCircle) item.subtitle else "",
                             type = if (layoutCategoryHero.isAlbum) STFThumbHeroType.Album
                             else if (layoutCategoryHero.isArtist) STFThumbHeroType.Artist
                             else if (layoutCategoryHero.isSong) STFThumbHeroType.Song
                             else if (layoutCategoryHero.isArtistCircle) STFThumbHeroType.ArtistCircle
                             else STFThumbHeroType.Playlist,
                             onClick = { onThumbClick(item.id) })
            }
        }
    }
}

@Preview
@Composable
fun CarouselHeroAlbumPreview() {
    E2MP3Theme {
        STFCarouselHero(title = "Title", thumbItem = carouselHeroThumbData, type = STFCarouselHeroType.Album, onThumbClick = {})
    }
}

@Preview
@Composable
fun CarouselHeroArtistPreview() {
    E2MP3Theme {
        STFCarouselHero(title = "Title", thumbItem = carouselHeroThumbData, type = STFCarouselHeroType.Artist, onThumbClick = {})
    }
}

@Preview
@Composable
fun CarouselHeroArtistCirclePreview() {
    E2MP3Theme {
        STFCarouselHero(title = "Title", thumbItem = carouselHeroThumbData, type = STFCarouselHeroType.ArtistCircle, onThumbClick = {})
    }
}

@Preview
@Composable
fun CarouselHeroSongPreview() {
    E2MP3Theme {
        STFCarouselHero(title = "Title", thumbItem = carouselHeroThumbData, type = STFCarouselHeroType.Song, onThumbClick = {})
    }
}

@Preview
@Composable
fun CarouselHeroPlaylistPreview() {
    E2MP3Theme {
        STFCarouselHero(title = "Title", thumbItem = carouselHeroThumbData, type = STFCarouselHeroType.Playlist, onThumbClick = {})
    }
}