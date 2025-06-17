package com.emanh.rootapp.presentation.ui.upload.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body3Regular
import com.emanh.rootapp.presentation.theme.E2MP3Theme
import com.emanh.rootapp.presentation.theme.SurfacePrimary
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.SurfaceProductSuperDark
import com.emanh.rootapp.presentation.theme.SurfaceSecondaryInvert
import com.emanh.rootapp.presentation.theme.SurfaceTertiary
import com.emanh.rootapp.presentation.theme.TextPrimary

@Composable
fun UploadDialogGenres(
    selectedGenres: List<Int>,
    genresList: List<Int>,
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onCheckedChange: (genreResId: Int, isChecked: Boolean) -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Box(modifier = Modifier
            .fillMaxSize()
            .debounceClickable(indication = null, onClick = onDismissRequest), contentAlignment = Alignment.Center) {
            Surface(shape = RoundedCornerShape(16.dp), tonalElevation = 8.dp, modifier = Modifier.padding(vertical = 120.dp, horizontal = 36.dp)) {
                Column(modifier = Modifier.border(width = 1.dp, color = SurfaceSecondaryInvert, shape = RoundedCornerShape(16.dp))) {
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(SurfacePrimary)) {
                        items(genresList) { item ->
                            val isChecked = item in selectedGenres

                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = {
                                    onCheckedChange(item, !isChecked)
                                }), verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = isChecked,
                                         colors = CheckboxColors(checkedBoxColor = SurfaceProduct,
                                                                 checkedCheckmarkColor = SurfaceProductSuperDark,
                                                                 uncheckedCheckmarkColor = SurfaceProductSuperDark,
                                                                 uncheckedBoxColor = SurfacePrimary,
                                                                 disabledCheckedBoxColor = TextPrimary,
                                                                 disabledUncheckedBoxColor = TextPrimary,
                                                                 disabledIndeterminateBoxColor = TextPrimary,
                                                                 checkedBorderColor = SurfaceProductSuperDark,
                                                                 uncheckedBorderColor = TextPrimary,
                                                                 disabledBorderColor = TextPrimary,
                                                                 disabledUncheckedBorderColor = TextPrimary,
                                                                 disabledIndeterminateBorderColor = TextPrimary),
                                         onCheckedChange = {
                                             onCheckedChange(item, !isChecked)
                                         })

                                Text(text = stringResource(item),
                                     color = TextPrimary,
                                     style = Body3Regular,
                                     modifier = Modifier
                                         .padding(vertical = 12.dp)
                                         .padding(start = 8.dp))
                            }
                        }
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(SurfaceTertiary)
                        .debounceClickable(onClick = onConfirmClick),
                        contentAlignment = Alignment.Center) {
                        Text(text = stringResource(R.string.confirm),
                             color = TextPrimary,
                             style = Body3Regular,
                             modifier = Modifier
                                 .padding(vertical = 12.dp)
                                 .padding(start = 8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun UploadDialogGenresPreview() {
    E2MP3Theme {
        UploadDialogGenres(selectedGenres = listOf(R.string.genre_chill, R.string.genre_romantic, R.string.genre_blues, R.string.genre_v_pop),
                           genresList = listOf(R.string.genre_chill,
                                               R.string.genre_hip_hop,
                                               R.string.genre_energizing,
                                               R.string.genre_sol,
                                               R.string.genre_romantic,
                                               R.string.genre_party,
                                               R.string.genre_concentrate,
                                               R.string.genre_blues,
                                               R.string.genre_indie,
                                               R.string.genre_jazz,
                                               R.string.genre_lating,
                                               R.string.genre_edm,
                                               R.string.genre_v_pop,
                                               R.string.genre_pop,
                                               R.string.genre_r_and_b,
                                               R.string.genre_rook),
                           onConfirmClick = {},
                           onDismissRequest = {},
                           onCheckedChange = { _, _ -> })
    }
}