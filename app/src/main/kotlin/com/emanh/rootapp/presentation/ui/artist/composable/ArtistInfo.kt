package com.emanh.rootapp.presentation.ui.artist.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.SongsModel
import com.emanh.rootapp.presentation.theme.Body2Bold
import com.emanh.rootapp.presentation.theme.Body5Regular
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary

@Composable
fun ArtistInfo(
    modifier: Modifier = Modifier,
    genre: String,
    viewMonth: Int,
    isFollowing: Boolean,
    modifierPausePlay: Modifier,
    songsList: List<SongsModel>,
    onFollowClick: () -> Unit,
    onMoreClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onPausePlayClick: () -> Unit
) {
    Column(modifier = modifier) {
        ArtistButton(modifier = Modifier.padding(16.dp),
                     viewMonth = viewMonth,
                     isFollowing = isFollowing,
                     avatarSongUrl = songsList.firstOrNull()?.avatarUrl.orEmpty(),
                     onFollowClick = onFollowClick,
                     onMoreClick = onMoreClick,
                     onShuffleClick = onShuffleClick,
                     onPausePlayClick = onPausePlayClick,
                     modifierPausePlay = modifierPausePlay)

        Text(text = genre, color = TextSecondary, style = Body5Regular, modifier = Modifier.padding(horizontal = 16.dp))

        Text(text = stringResource(R.string.popular_songs),
             color = TextPrimary,
             style = Body2Bold,
             maxLines = 1,
             overflow = TextOverflow.Ellipsis,
             modifier = Modifier
                 .padding(horizontal = 16.dp)
                 .padding(top = 24.dp))
    }
}