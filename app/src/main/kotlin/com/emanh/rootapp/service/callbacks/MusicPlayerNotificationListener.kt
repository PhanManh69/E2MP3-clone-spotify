package com.emanh.rootapp.service.callbacks

import android.app.Notification
import android.app.Service.STOP_FOREGROUND_REMOVE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.media3.ui.PlayerNotificationManager
import com.emanh.rootapp.service.MusicService
import com.emanh.rootapp.utils.MyConstant.NOTIFICATION_ID

@androidx.media3.common.util.UnstableApi
class MusicPlayerNotificationListener(
    private val musicService: MusicService
) : PlayerNotificationManager.NotificationListener {

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        musicService.apply {
            stopForeground(STOP_FOREGROUND_REMOVE)
            isForegroundService = false
            stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int, notification: Notification, ongoing: Boolean
    ) {
        musicService.apply {
            if (ongoing && !isForegroundService) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        if (ContextCompat.checkSelfPermission(applicationContext,
                                                              android.Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return
                        }
                    }

                    createNotificationChannel()

                    ContextCompat.startForegroundService(this, Intent(applicationContext, this::class.java))

                    if (notification.extras == null || notification.contentIntent == null) {
                        Log.e("EManh Debug", "Failed Notification!")
                        return
                    }

                    startForeground(NOTIFICATION_ID, notification)
                    isForegroundService = true

                } catch (e: IllegalStateException) {
                    Log.e("EManh Debug", "Failed to startForeground: ${e.message}")
                }
            }
        }
    }
}