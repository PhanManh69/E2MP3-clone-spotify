package com.emanh.rootapp.presentation.ui.testcomposable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.emanh.rootapp.presentation.ItemArtistsPreview
import com.emanh.rootapp.presentation.composable.AvatarUrlPreview
import com.emanh.rootapp.presentation.composable.AvatarUsernamePreview
import com.emanh.rootapp.presentation.composable.BrandPreview
import com.emanh.rootapp.presentation.composable.BrandShowNamePreview
import com.emanh.rootapp.presentation.composable.ButtonPrimaryPreview
import com.emanh.rootapp.presentation.composable.ButtonSecondaryPreview
import com.emanh.rootapp.presentation.composable.CardImagePreview
import com.emanh.rootapp.presentation.composable.CardPodcatPreview
import com.emanh.rootapp.presentation.composable.CardSlimPreview
import com.emanh.rootapp.presentation.composable.CarouselHeroAlbumPreview
import com.emanh.rootapp.presentation.composable.CarouselHeroArtistCirclePreview
import com.emanh.rootapp.presentation.composable.CarouselHeroArtistPreview
import com.emanh.rootapp.presentation.composable.CarouselHeroPlaylistPreview
import com.emanh.rootapp.presentation.composable.CarouselHorizontalMusicBSmallPreview
import com.emanh.rootapp.presentation.composable.CarouselHorizontalMusicBigPreview
import com.emanh.rootapp.presentation.composable.CarouselHorizontalMusicMediumPreview
import com.emanh.rootapp.presentation.composable.CarouselHorizontalPlaylistBigPreview
import com.emanh.rootapp.presentation.composable.ChipsNormalActivePreview
import com.emanh.rootapp.presentation.composable.ChipsNormalDefaultPreview
import com.emanh.rootapp.presentation.composable.ChipsNormalStrokePreview
import com.emanh.rootapp.presentation.composable.ChipsSmallActivePreview
import com.emanh.rootapp.presentation.composable.ChipsSmallDefaultPreview
import com.emanh.rootapp.presentation.composable.ChipsSmallStrokePreview
import com.emanh.rootapp.presentation.composable.EqualizerComposablePreview
import com.emanh.rootapp.presentation.composable.ItemArtistsFollowSmallPreview
import com.emanh.rootapp.presentation.composable.ItemArtistsMediumPreview
import com.emanh.rootapp.presentation.composable.ItemArtistsSmallPreview
import com.emanh.rootapp.presentation.composable.ItemMusicBigPreview
import com.emanh.rootapp.presentation.composable.ItemMusicMediumPreview
import com.emanh.rootapp.presentation.composable.ItemMusicSmallPreview
import com.emanh.rootapp.presentation.composable.LabelDefaultPreview
import com.emanh.rootapp.presentation.composable.LabelSelectedPreview
import com.emanh.rootapp.presentation.composable.MenuLibraryPreview
import com.emanh.rootapp.presentation.composable.STFHeader
import com.emanh.rootapp.presentation.composable.STFHeaderType
import com.emanh.rootapp.presentation.composable.StickyDefaultPreview
import com.emanh.rootapp.presentation.composable.StickyPlayerPreview
import com.emanh.rootapp.presentation.composable.TabIconDefaultPreview
import com.emanh.rootapp.presentation.composable.TabIconSelectPreview
import com.emanh.rootapp.presentation.composable.ThumbArtistsBigPreview
import com.emanh.rootapp.presentation.composable.ThumbArtistsSmallPreview
import com.emanh.rootapp.presentation.composable.ThumbArtistsXSmallPreview
import com.emanh.rootapp.presentation.composable.ThumbHeroAlbumPreview
import com.emanh.rootapp.presentation.composable.ThumbHeroArtistCirclePreview
import com.emanh.rootapp.presentation.composable.ThumbHeroArtistPreview
import com.emanh.rootapp.presentation.composable.ThumbHeroPlaylistPreview
import com.emanh.rootapp.presentation.composable.ThumbMusicBigPreview
import com.emanh.rootapp.presentation.composable.ThumbMusicMediumPreview
import com.emanh.rootapp.presentation.composable.ThumbMusicSmallPreview
import com.emanh.rootapp.presentation.composable.ThumbMusicXSmallPreview
import com.emanh.rootapp.presentation.composable.ThumbPodcastBigPreview
import com.emanh.rootapp.presentation.composable.ThumbPodcastSmallPreview
import com.emanh.rootapp.presentation.composable.ThumbVerticalBigPreview
import com.emanh.rootapp.presentation.composable.ThumbVerticalSmallPreview
import com.emanh.rootapp.presentation.composable.TitleArtistsPreview
import com.emanh.rootapp.presentation.composable.TitlePodcastPreview
import com.emanh.rootapp.utils.MyConstant.PADDING_BOTTOM_BAR
import com.emanh.rootapp.utils.MyConstant.chipSearchInputList
import com.emanh.rootapp.utils.MyConstant.chipsList
import kotlinx.coroutines.delay

@Composable
fun TestComposableScreen() {
    val focusManager = LocalFocusManager.current
    var currentMessage by remember { mutableStateOf("") }

    val allItems = listOf<@Composable () -> Unit>({ BrandPreview() },
                                                  { BrandShowNamePreview() },
                                                  { CardImagePreview() },
                                                  { CardPodcatPreview() },
                                                  { ButtonPrimaryPreview() },
                                                  { ButtonSecondaryPreview() },
                                                  { EqualizerComposablePreview() },
                                                  { TabIconSelectPreview() },
                                                  { TabIconDefaultPreview() },
                                                  { ChipsNormalDefaultPreview() },
                                                  { ChipsSmallDefaultPreview() },
                                                  { ChipsNormalActivePreview() },
                                                  { ChipsSmallActivePreview() },
                                                  { ChipsNormalStrokePreview() },
                                                  { ChipsSmallStrokePreview() },
                                                  { AvatarUrlPreview() },
                                                  { AvatarUsernamePreview() },
                                                  { LabelDefaultPreview() },
                                                  { LabelSelectedPreview() },
                                                  { MenuLibraryPreview() },
                                                  { StickyDefaultPreview() },
                                                  { StickyPlayerPreview() },
                                                  { ThumbMusicBigPreview() },
                                                  { ThumbPodcastBigPreview() },
                                                  { ThumbArtistsBigPreview() },
                                                  { ThumbMusicMediumPreview() },
                                                  { ThumbMusicSmallPreview() },
                                                  { ThumbPodcastSmallPreview() },
                                                  { ThumbArtistsSmallPreview() },
                                                  { ThumbMusicXSmallPreview() },
                                                  { ThumbArtistsXSmallPreview() },
                                                  { ThumbHeroAlbumPreview() },
                                                  { ThumbHeroArtistPreview() },
                                                  { ThumbHeroArtistCirclePreview() },
                                                  { ThumbHeroPlaylistPreview() },
                                                  { CarouselHorizontalMusicBigPreview() },
                                                  { CarouselHorizontalPlaylistBigPreview() },
                                                  { CarouselHorizontalMusicMediumPreview() },
                                                  { CarouselHorizontalMusicBSmallPreview() },
                                                  { CarouselHeroAlbumPreview() },
                                                  { CarouselHeroArtistPreview() },
                                                  { CarouselHeroArtistCirclePreview() },
                                                  { CarouselHeroPlaylistPreview() },
                                                  { TitlePodcastPreview() },
                                                  { TitleArtistsPreview() },
                                                  { ItemMusicBigPreview() },
                                                  { ItemMusicMediumPreview() },
                                                  { ItemArtistsMediumPreview() },
                                                  { ItemMusicSmallPreview() },
                                                  { ItemArtistsSmallPreview() },
                                                  { ItemArtistsFollowSmallPreview() },
                                                  { ItemArtistsPreview() },
                                                  { CardSlimPreview() })

    var visibleItemCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (visibleItemCount < allItems.size) {
            delay(100L)
            visibleItemCount++
        }
    }

    STFHeader(inputText = currentMessage,
              searchChipsList = chipSearchInputList,
              type = STFHeaderType.HeaderSearchInput,
              onInputTextChange = { currentMessage = it },
              modifier = Modifier.clickable(interactionSource = remember { MutableInteractionSource() },
                                            indication = null) { focusManager.clearFocus() },
              content = {
                  Column(modifier = it, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                      Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                          ThumbVerticalBigPreview()
                          ThumbVerticalSmallPreview()
                      }

                      LazyColumn(modifier = Modifier.fillMaxWidth(),
                                 contentPadding = PaddingValues(bottom = PADDING_BOTTOM_BAR.dp),
                                 verticalArrangement = Arrangement.spacedBy(16.dp),
                                 horizontalAlignment = Alignment.CenterHorizontally) {
                          items(count = visibleItemCount) { index ->
                              AnimatedVisibility(visible = true, enter = fadeIn(animationSpec = tween(durationMillis = 300))) {
                                  allItems.getOrNull(index)?.invoke()
                              }
                          }
                      }
                  }
              })
}