@file:Suppress("DEPRECATION")

package com.emanh.rootapp.utils

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import com.emanh.rootapp.data.db.entity.AlbumsEntity
import com.emanh.rootapp.data.db.entity.PlaylistsEntity
import com.emanh.rootapp.data.db.entity.SearchEntity
import com.emanh.rootapp.data.db.entity.SongsEntity
import com.emanh.rootapp.data.db.entity.UsersEntity
import com.emanh.rootapp.presentation.theme.SurfaceProductSuperDark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URL
import java.text.Normalizer

suspend fun loadProgress(
    timeSeconds: Long, startPositionSeconds: Long = 0, isPlaying: () -> Boolean, updateProgress: (Float) -> Unit, onFinish: () -> Unit
) {
    if (timeSeconds <= 0) {
        updateProgress(0f)
        onFinish()
        return
    }

    val totalTimeMillis = timeSeconds * 1000L
    val initialProgress = (startPositionSeconds.toFloat() / timeSeconds).coerceIn(0f, 1f)
    updateProgress(initialProgress)

    withContext(Dispatchers.Default) {
        var elapsedTimeMillis = startPositionSeconds * 1000L
        var lastTickTime = System.currentTimeMillis()

        while (isActive && elapsedTimeMillis < totalTimeMillis) {
            delay(16)

            val now = System.currentTimeMillis()

            if (isPlaying()) {
                val deltaTime = now - lastTickTime
                elapsedTimeMillis += deltaTime

                val boundedElapsedTime = elapsedTimeMillis.coerceAtMost(totalTimeMillis)
                val progress = boundedElapsedTime.toFloat() / totalTimeMillis

                updateProgress(progress)
            }

            lastTickTime = now
        }

        if (isActive) {
            updateProgress(1f)
            onFinish()
        }
    }
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "$minutes:${remainingSeconds.toString().padStart(2, '0')}"
}

@Composable
fun faunchedEffectAvatar(avatarUrl: String?): Color {
    var backgroundColor by remember { mutableStateOf(SurfaceProductSuperDark) }
    var palette by remember { mutableStateOf<Palette?>(null) }

    LaunchedEffect(avatarUrl) {
        try {
            val bitmap = withContext(Dispatchers.IO) {
                try {
                    val connection = URL(avatarUrl).openConnection()
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000
                    connection.connect()

                    val inputStream = connection.getInputStream()
                    BitmapFactory.decodeStream(inputStream)
                } catch (e: Exception) {
                    Log.e("faunchedEffectAvatar", "Failed to download image: ${e.message}")
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
                    Log.e("faunchedEffectAvatar", "Palette generation failed: ${e.message}")
                }
            } else {
                Log.e("faunchedEffectAvatar", "Failed to decode bitmap from URL")
            }
        } catch (e: Exception) {
            Log.e("faunchedEffectAvatar", "Error in LaunchedEffect: ${e.message}")
        }
    }

    return backgroundColor
}

fun List<UsersEntity>.withNormalizedUsers(): List<UsersEntity> {
    return this.map { user ->
        user.copy(normalizedSearchValue = user.name?.removeAccents())
    }
}

fun List<SongsEntity>.withNormalizedSongs(): List<SongsEntity> {
    return this.map { song ->
        song.copy(normalizedSearchValue = "${song.title?.removeAccents()} ${song.subtitle?.removeAccents()}")
    }
}

fun List<PlaylistsEntity>.withNormalizedPlaylists(): List<PlaylistsEntity> {
    return this.map { playlist ->
        playlist.copy(normalizedSearchValue = playlist.title?.removeAccents())
    }
}

fun List<AlbumsEntity>.withNormalizedAlbums(): List<AlbumsEntity> {
    return this.map { album ->
        album.copy(normalizedSearchValue = "${album.title?.removeAccents()} ${album.subtitle?.removeAccents()}")
    }
}

fun String.removeAccents(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    val noDiacritics = normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    return noDiacritics.replace('đ', 'd')
        .replace('Đ', 'D')
        .replace('ă', 'a')
        .replace('Ă', 'A')
        .replace('â', 'a')
        .replace('Â', 'A')
        .replace('ê', 'e')
        .replace('Ê', 'E')
        .replace('ô', 'o')
        .replace('Ô', 'O')
        .replace('ơ', 'o')
        .replace('Ơ', 'O')
        .replace('ư', 'u')
        .replace('Ư', 'U')
}