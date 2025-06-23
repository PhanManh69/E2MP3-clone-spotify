package com.emanh.rootapp.network.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerNotificationManager
import com.emanh.e2mp3.spotify.R
import com.emanh.rootapp.utils.MyConstant.NOTIFICATION_CHANNEL_ID
import com.emanh.rootapp.utils.MyConstant.NOTIFICATION_ID

@UnstableApi
class MusicNotificationManager(
    private val context: Context, session: MediaSession, notificationListener: PlayerNotificationManager.NotificationListener
) {
    private val notificationManager: PlayerNotificationManager

    init {
        val mediaDescriptionAdapter = object : PlayerNotificationManager.MediaDescriptionAdapter {
            override fun getCurrentContentTitle(player: Player): String {
                return player.currentMediaItem?.mediaMetadata?.title?.toString() ?: "Unknown Title"
            }

            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                val intent = context.packageManager?.getLaunchIntentForPackage(context.packageName)
                return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }

            override fun getCurrentContentText(player: Player): String {
                return player.currentMediaItem?.mediaMetadata?.artist?.toString() ?: "Unknown Artist"
            }

            override fun getCurrentLargeIcon(
                player: Player, callback: PlayerNotificationManager.BitmapCallback
            ): Bitmap? {
                return null
            }
        }

        notificationManager = PlayerNotificationManager.Builder(context, NOTIFICATION_ID, NOTIFICATION_CHANNEL_ID)
            .setMediaDescriptionAdapter(mediaDescriptionAdapter)
            .setNotificationListener(notificationListener)
            .setChannelImportance(NotificationManager.IMPORTANCE_LOW)
            .setSmallIconResourceId(R.drawable.ic_24_equalizer_3)
            .build()

        notificationManager.setMediaSessionToken(session.sessionCompatToken)
    }
}