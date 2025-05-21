package com.emanh.rootapp.presentation.ui.artist.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.domain.model.UsersModel
import com.emanh.rootapp.presentation.composable.utils.debounceClickable
import com.emanh.rootapp.presentation.theme.Body5Bold
import com.emanh.rootapp.presentation.theme.IconBackgroundDark
import com.emanh.rootapp.presentation.theme.IconPrimary
import com.emanh.rootapp.presentation.theme.SurfaceAlphaSticky
import com.emanh.rootapp.presentation.theme.SurfaceProduct
import com.emanh.rootapp.presentation.theme.TextPrimary

@Composable
fun ArtistHeader(
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    headerAlpha: Float,
    positionY: Dp,
    backgroundColor: Color,
    artist: UsersModel,
    nestedScrollConnection: NestedScrollConnection,
    onBackClick: () -> Unit,
    onPausePlayClick: () -> Unit
) {
    ConstraintLayout(modifier = modifierIcon) {
        val (header, icon) = createRefs()
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .alpha(headerAlpha)
            .background(backgroundColor)
            .nestedScroll(nestedScrollConnection)
            .constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        if (positionY <= 88.dp) {
            Box(modifier = Modifier
                .offset(y = (if (positionY <= 64.dp) (-24).dp else positionY - 88.dp))
                .padding(end = 16.dp)
                .background(color = SurfaceProduct, shape = CircleShape)
                .clip(CircleShape)
                .constrainAs(icon) {
                    top.linkTo(header.bottom)
                    end.linkTo(parent.end)
                }
                .debounceClickable(onClick = onPausePlayClick)) {
                Icon(painter = painterResource(R.drawable.ic_32_play),
                     contentDescription = null,
                     tint = IconBackgroundDark,
                     modifier = Modifier.padding(8.dp))
            }
        }
    }

    Row(modifier = modifier.padding(start = 16.dp, top = 48.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(color = SurfaceAlphaSticky, shape = CircleShape)
            .debounceClickable(onClick = onBackClick)) {
            Icon(painter = painterResource(R.drawable.ic_24_musical_chevron_lt),
                 contentDescription = null,
                 tint = IconPrimary,
                 modifier = Modifier.align(Alignment.Center))
        }

        Text(text = artist.name.orEmpty(),
             color = TextPrimary,
             style = Body5Bold,
             maxLines = 1,
             overflow = TextOverflow.Ellipsis,
             modifier = Modifier
                 .padding(end = 72.dp)
                 .alpha(headerAlpha)
                 .nestedScroll(nestedScrollConnection))
    }
}