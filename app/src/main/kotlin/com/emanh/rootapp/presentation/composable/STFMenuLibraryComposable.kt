package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body7Regular
import com.emanh.rootapp.presentation.theme.ChipsSecondary
import com.emanh.rootapp.presentation.theme.ChipsSelectDark
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceFieldInvert
import com.emanh.rootapp.utils.MyConstant.sampleLibraryData

enum class STFMenuLibraryType {
    Default, Category, Subcategory
}

@Immutable
data class PrimaryLibraryData(
    val primaryLibrary: List<SecondaryLibraryData>
)

@Immutable
data class SecondaryLibraryData(
    val title: String, val secondaryLibrary: List<String>
)

@Composable
fun STFMenuLibrary(
    modifier: Modifier = Modifier,
    type: STFMenuLibraryType = STFMenuLibraryType.Default,
    libraryList: PrimaryLibraryData,
    onPrimaryChipsClick: () -> Unit = {},
    onSecondaryChipsClick: () -> Unit = {}
) {
    var currentType by remember { mutableStateOf(type) }
    var selectedPrimary by remember { mutableStateOf<SecondaryLibraryData?>(null) }
    var selectedSecondary by remember { mutableStateOf("") }

    val current = LocalDensity.current
    val chipsDirection = remember { mutableStateOf(Size.Zero) }
    val visible = currentType != STFMenuLibraryType.Default

    Row(modifier = modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(100))
        .animateContentSize(animationSpec = tween(durationMillis = 400))) {
        AnimatedVisibility(visible = visible, enter = fadeIn() + expandHorizontally(), exit = fadeOut() + shrinkHorizontally()) {
            Row {
                STFIconClose {
                    currentType = STFMenuLibraryType.Default
                    selectedPrimary = null
                    selectedSecondary = ""
                }

                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        when (currentType) {
            STFMenuLibraryType.Default -> {
                LazyRow(modifier = Modifier
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(100)), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(libraryList.primaryLibrary) { item ->
                        STFChips(text = item.title, size = STFChipsSize.Small, onClick = {
                            onPrimaryChipsClick()
                            selectedPrimary = item
                            currentType = STFMenuLibraryType.Category
                        })
                    }
                }
            }

            STFMenuLibraryType.Category -> {
                selectedPrimary?.let { primary ->
                    Box(modifier = Modifier.weight(1f)) {
                        LazyRow(modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(100)),
                                contentPadding = PaddingValues(start = with(current) { chipsDirection.value.width.toDp() + 8.dp }),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(primary.secondaryLibrary) { item ->
                                STFChips(text = item, size = STFChipsSize.Small, onClick = {
                                    onSecondaryChipsClick()
                                    selectedSecondary = item
                                    currentType = STFMenuLibraryType.Subcategory
                                })
                            }
                        }

                        STFChips(modifier = Modifier.onGloballyPositioned { chipsDirection.value = it.size.toSize() },
                                 text = primary.title,
                                 size = STFChipsSize.Small,
                                 type = STFChipsType.Active,
                                 onClick = {
                                     currentType = STFMenuLibraryType.Default
                                 })
                    }
                }
            }

            STFMenuLibraryType.Subcategory -> {
                selectedPrimary?.let { primary ->
                    ChipsWithBadge(modifier = Modifier, primaryText = primary.title, badgeText = selectedSecondary, onBadgeClick = {
                        currentType = STFMenuLibraryType.Category
                    }, onPrimaryClick = {
                        currentType = STFMenuLibraryType.Default
                    })
                }
            }
        }
    }
}

@Composable
private fun STFIconClose(
    modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Icon(painter = painterResource(R.drawable.ic_24_close),
         contentDescription = null,
         tint = IconPrimary,
         modifier = modifier
             .size(30.dp)
             .background(color = ChipsSecondary, shape = CircleShape)
             .clip(CircleShape)
             .debounceClickable { onClick() })
}

@Composable
private fun ChipsWithBadge(
    modifier: Modifier = Modifier, primaryText: String, badgeText: String, onBadgeClick: () -> Unit, onPrimaryClick: () -> Unit
) {
    val current = LocalDensity.current
    val chipsDirection = remember { mutableStateOf(Size.Zero) }

    Box(modifier = modifier) {
        Box(modifier = Modifier
            .height(30.dp)
            .background(color = ChipsSelectDark, shape = RoundedCornerShape(100))
            .clip(RoundedCornerShape(100))
            .debounceClickable { onBadgeClick() }) {
            Text(text = badgeText,
                 style = Body7Regular,
                 color = SurfaceFieldInvert,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis,
                 modifier = Modifier
                     .padding(start = with(current) { chipsDirection.value.width.toDp() + 8.dp }, end = 16.dp)
                     .align(Alignment.Center))
        }

        STFChips(modifier = Modifier.onGloballyPositioned { chipsDirection.value = it.size.toSize() },
                 text = primaryText,
                 size = STFChipsSize.Small,
                 type = STFChipsType.Active,
                 onClick = onPrimaryClick)
    }
}

@Preview
@Composable
fun MenuLibraryPreview() {
    E2MP3Theme {
        STFMenuLibrary(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), libraryList = sampleLibraryData)
    }
}