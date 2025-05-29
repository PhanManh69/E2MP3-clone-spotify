package com.emanh.rootapp.presentation.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body4Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.IconBackgroundDark
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceField
import com.emanh.rootapp.presentation.theme.SurfaceFieldInvert
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.TextPrimary
import com.emanh.rootapp.presentation.theme.TextSecondary
import com.emanh.rootapp.presentation.theme.Title4Bold
import com.emanh.rootapp.utils.MyConstant.chipSearchInputList
import com.emanh.rootapp.utils.MyConstant.sampleLibraryData
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.emanh.rootapp.presentation.theme.Body1Bold

enum class STFHeaderType {
    HeaderHome, HeaderSearch, HeaderSearchInput, HeaderLibrary
}

private fun getHeaderPositionFactory(type: STFHeaderType): Dp {
    return when (type) {
        STFHeaderType.HeaderHome -> 48.dp
        STFHeaderType.HeaderSearch -> 64.dp
        STFHeaderType.HeaderSearchInput -> 118.dp
        STFHeaderType.HeaderLibrary -> 102.dp
    }
}

@Composable
fun STFHeader(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    userName: String = "",
    inputText: String = "",
    type: STFHeaderType,
    searchChipsList: List<Int>? = null,
    libraryList: PrimaryLibraryData = PrimaryLibraryData(primaryLibrary = emptyList()),
    onAvatarClick: () -> Unit = {},
    onChipsHomeClick: (Int) -> Unit = {},
    onPhotoClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onChipsSearchInputClick: (Int) -> Unit = {},
    onPlusClick: () -> Unit = {},
    onPrimaryChipsClick: () -> Unit = {},
    onSecondaryChipsClick: () -> Unit = {},
    onInputTextChange: (String) -> Unit = {},
    focusRequester: FocusRequester? = null,
    content: @Composable (Modifier) -> Unit = {}
) {
    val maxSearchStickPosition = if (type == STFHeaderType.HeaderSearch) 128.dp else 80.dp
    val minSearchStickPosition = 48.dp
    val paddingContent = remember(type) { getHeaderPositionFactory(type) }

    var currentPosition by remember { mutableStateOf(maxSearchStickPosition) }
    var locationScale by remember { mutableFloatStateOf(1f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newSearchStickPosition = currentPosition + delta.dp
                val previousSearchStickPosition = currentPosition

                currentPosition = newSearchStickPosition.coerceIn(minSearchStickPosition, maxSearchStickPosition)
                val consumed = currentPosition - previousSearchStickPosition

                locationScale = currentPosition / maxSearchStickPosition

                return Offset(0f, consumed.value)
            }
        }
    }

    Box(Modifier.nestedScroll(nestedScrollConnection)) {
        content(Modifier
                    .fillMaxSize()
                    .padding(top = paddingContent, bottom = currentPosition)
                    .offset {
                        IntOffset(0, currentPosition.roundToPx())
                    })

        when (type) {
            STFHeaderType.HeaderHome -> STFHeaderHome(modifier = modifier,
                                                      avatarUrl = avatarUrl,
                                                      userName = userName,
                                                      currentPosition = currentPosition,
                                                      onAvatarClick = onAvatarClick,
                                                      onChipsClick = onChipsHomeClick)

            STFHeaderType.HeaderSearch -> STFHeaderSearch(modifier = modifier.align(Alignment.TopCenter),
                                                          avatarUrl = avatarUrl,
                                                          userName = userName,
                                                          currentPosition = currentPosition,
                                                          onAvatarClick = onAvatarClick,
                                                          onPhotoClick = onPhotoClick,
                                                          onSearchClick = onSearchClick)

            STFHeaderType.HeaderSearchInput -> STFHeaderSearchInput(modifier = modifier,
                                                                    inputText = inputText,
                                                                    currentPosition = currentPosition,
                                                                    chipsList = searchChipsList,
                                                                    onInputTextChange = onInputTextChange,
                                                                    onBackClick = onBackClick,
                                                                    onChipsClick = onChipsSearchInputClick,
                                                                    focusRequester = focusRequester)

            STFHeaderType.HeaderLibrary -> STFHeaderLibrary(modifier = modifier,
                                                            avatarUrl = avatarUrl,
                                                            userName = userName,
                                                            currentPosition = currentPosition,
                                                            libraryList = libraryList,
                                                            onAvatarClick = onAvatarClick,
                                                            onSearchClick = onSearchClick,
                                                            onPlusClick = onPlusClick,
                                                            onPrimaryChipsClick = onPrimaryChipsClick,
                                                            onSecondaryChipsClick = onSecondaryChipsClick)
        }
    }
}

@Composable
private fun STFHeaderHome(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    userName: String,
    currentPosition: Dp,
    onAvatarClick: () -> Unit,
    onChipsClick: (Int) -> Unit,
) {
    val chipsList = listOf(R.string.all, R.string.music, R.string.podcasts)

    var selectedChipIndex by remember { mutableIntStateOf(0) }

    Box(modifier = modifier
        .background(color = SurfacePrimary)
        .offset {
            IntOffset(x = 0, (currentPosition).roundToPx())
        }) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            STFAvatar(avatarUrl = avatarUrl, userName = userName, onClick = onAvatarClick)

            Spacer(modifier = Modifier.width(12.dp))

            chipsList.forEachIndexed { index, chipResId ->
                val chipType = if (index == selectedChipIndex) STFChipsType.Active else STFChipsType.Default

                STFChips(text = stringResource(id = chipResId), type = chipType, onClick = {
                    selectedChipIndex = index
                    onChipsClick(selectedChipIndex)
                })

                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
private fun STFHeaderSearch(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    userName: String,
    currentPosition: Dp,
    onAvatarClick: () -> Unit,
    onPhotoClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    Box(modifier = modifier.background(color = SurfacePrimary)) {
        Row(modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 80.dp)
            .animateContentSize(animationSpec = tween(durationMillis = 300)),
            verticalAlignment = Alignment.CenterVertically) {
            STFAvatar(avatarUrl = avatarUrl, userName = userName, onClick = onAvatarClick)

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = stringResource(R.string.search), color = TextPrimary, style = Title4Bold)

            Spacer(modifier = Modifier.weight(1f))

            Icon(painter = painterResource(R.drawable.ic_24_photo),
                 contentDescription = null,
                 tint = IconPrimary,
                 modifier = Modifier
                     .clip(shape = RoundedCornerShape(8.dp))
                     .debounceClickable { onPhotoClick() })
        }
    }

    STFHeaderSearchStick(
            modifier = Modifier.offset {
                IntOffset(x = 0, (currentPosition).roundToPx())
            },
            onSearchClick = onSearchClick,
    )
}

@Composable
private fun STFHeaderSearchStick(
    modifier: Modifier = Modifier, onSearchClick: () -> Unit
) {
    Box(modifier = modifier.background(color = SurfacePrimary)) {
        Column(modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = SurfaceField, shape = RoundedCornerShape(8.dp))
                .clip(shape = RoundedCornerShape(8.dp))
                .debounceClickable { onSearchClick() }) {
                Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(painter = painterResource(R.drawable.ic_32_search),
                         contentDescription = null,
                         tint = IconBackgroundDark,
                         modifier = Modifier.size(32.dp))

                    Text(text = stringResource(id = R.string.what_do_you_want_to_listen_to), style = Body4Regular, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
private fun STFHeaderSearchInput(
    modifier: Modifier = Modifier,
    inputText: String,
    currentPosition: Dp,
    chipsList: List<Int>? = null,
    onInputTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onChipsClick: (Int) -> Unit,
    focusRequester: FocusRequester? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val chipsSelected = remember { mutableIntStateOf(-1) }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(modifier = modifier
        .background(color = SurfaceFieldInvert)
        .offset {
            IntOffset(x = 0, (currentPosition).roundToPx())
        }, verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Row(modifier = Modifier
            .background(color = SurfaceFieldInvert)
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.ic_24_musical_chevron_lt),
                 contentDescription = null,
                 tint = IconPrimary,
                 modifier = Modifier
                     .size(24.dp)
                     .clip(shape = RoundedCornerShape(8.dp))
                     .debounceClickable { onBackClick() })

            TextField(value = inputText,
                      onValueChange = onInputTextChange,
                      placeholder = {
                          Text(text = stringResource(id = R.string.what_do_you_want_to_listen_to), style = Body4Regular, color = TextSecondary)
                      },
                      textStyle = Body4Regular,
                      singleLine = false,
                      maxLines = 1,
                      interactionSource = interactionSource,
                      colors = TextFieldDefaults.colors(
                              focusedContainerColor = Color.Transparent,
                              unfocusedContainerColor = Color.Transparent,
                              disabledContainerColor = Color.Transparent,
                              focusedIndicatorColor = Color.Transparent,
                              unfocusedIndicatorColor = Color.Transparent,
                              cursorColor = SurfaceProduct,
                              focusedTextColor = TextPrimary,
                              unfocusedTextColor = TextPrimary,
                      ),
                      modifier = Modifier
                          .weight(1f)
                          .then(focusRequester?.let { Modifier.focusRequester(it) } ?: Modifier))

            Icon(painter = painterResource(id = R.drawable.ic_24_close),
                 contentDescription = null,
                 tint = IconPrimary,
                 modifier = Modifier
                     .alpha(if (isFocused && inputText.isNotEmpty()) 1f else 0f)
                     .clip(shape = RoundedCornerShape(8.dp))
                     .debounceClickable {
                         onInputTextChange("")
                     })
        }

        LazyRow(modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (chipsList != null) {
                items(chipsList.size) { index ->
                    STFChips(text = stringResource(chipsList[index]),
                             size = STFChipsSize.Small,
                             type = if (index == chipsSelected.intValue) STFChipsType.Stroke else STFChipsType.Default,
                             onClick = {
                                 if (chipsSelected.intValue == index) {
                                     chipsSelected.intValue = -1
                                     onChipsClick(-1)
                                     return@STFChips
                                 }
                                 chipsSelected.intValue = index
                                 onChipsClick(chipsSelected.intValue)
                             })
                }
            } else {
                item {
                    Box(modifier = Modifier.height(30.dp)) {
                        Text(text = stringResource(R.string.history_search),
                             color = TextPrimary,
                             style = Body1Bold,
                             modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@Composable
private fun STFHeaderLibrary(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    userName: String,
    currentPosition: Dp,
    libraryList: PrimaryLibraryData,
    onAvatarClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPlusClick: () -> Unit,
    onPrimaryChipsClick: () -> Unit,
    onSecondaryChipsClick: () -> Unit
) {
    Box(modifier = modifier
        .background(color = SurfacePrimary)
        .offset {
            IntOffset(0, (currentPosition).roundToPx())
        }) {
        Column(modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                STFAvatar(avatarUrl = avatarUrl, userName = userName, onClick = onAvatarClick)

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = stringResource(R.string.your_library), color = TextPrimary, style = Title4Bold, modifier = Modifier.weight(1f))

                Icon(painter = painterResource(R.drawable.ic_32_search),
                     contentDescription = null,
                     tint = IconPrimary,
                     modifier = Modifier
                         .size(32.dp)
                         .clip(shape = RoundedCornerShape(8.dp))
                         .debounceClickable { onSearchClick() })

                Spacer(modifier = Modifier.width(12.dp))

                Icon(painter = painterResource(R.drawable.ic_32_plus),
                     contentDescription = null,
                     tint = IconPrimary,
                     modifier = Modifier
                         .size(32.dp)
                         .clip(shape = RoundedCornerShape(8.dp))
                         .debounceClickable { onPlusClick() })
            }

            STFMenuLibrary(libraryList = libraryList, onPrimaryChipsClick = onPrimaryChipsClick, onSecondaryChipsClick = onSecondaryChipsClick)
        }
    }
}

@Preview
@Composable
fun HeaderHomePreview() {
    E2MP3Theme {
        STFHeaderHome(userName = "emanh", currentPosition = 0.dp, onAvatarClick = {}, onChipsClick = {})
    }
}

@Preview
@Composable
fun HeaderSearchPreview() {
    E2MP3Theme {
        STFHeaderSearch(userName = "emanh", currentPosition = 10.dp, onAvatarClick = {}, onPhotoClick = {}, onSearchClick = {})
    }
}

@Preview
@Composable
fun HeaderSearchStickPreview() {
    E2MP3Theme {
        STFHeaderSearchStick(onSearchClick = {})
    }
}

@Preview
@Composable
fun HeaderSearchInputPreview1() {
    E2MP3Theme {
        var currentMessage by remember { mutableStateOf("") }

        STFHeaderSearchInput(
                inputText = currentMessage,
                currentPosition = 0.dp,
                chipsList = chipSearchInputList,
                onInputTextChange = { currentMessage = it },
                onBackClick = {},
                onChipsClick = {},
        )
    }
}

@Preview
@Composable
fun HeaderSearchInputPreview2() {
    E2MP3Theme {
        var currentMessage by remember { mutableStateOf("") }

        STFHeaderSearchInput(
                inputText = currentMessage,
                currentPosition = 0.dp,
                onInputTextChange = { currentMessage = it },
                onBackClick = {},
                onChipsClick = {},
        )
    }
}

@Preview
@Composable
fun HeaderLibraryPreview() {
    E2MP3Theme {
        STFHeaderLibrary(userName = "emanh",
                         currentPosition = 0.dp,
                         libraryList = sampleLibraryData,
                         onAvatarClick = {},
                         onSearchClick = {},
                         onPlusClick = {},
                         onPrimaryChipsClick = {},
                         onSecondaryChipsClick = {})
    }
}