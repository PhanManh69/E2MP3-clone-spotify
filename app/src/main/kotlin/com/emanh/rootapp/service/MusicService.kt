package com.emanh.rootapp.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.emanh.rootapp.service.callbacks.MusicPlayerNotificationListener
import com.emanh.rootapp.utils.MyConstant.MUSIC_PLAYBACK
import com.emanh.rootapp.utils.MyConstant.NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject

@androidx.media3.common.util.UnstableApi
@AndroidEntryPoint
class MusicService : MediaSessionService() {

    @Inject
    lateinit var exoPlayer: ExoPlayer

    private lateinit var mediaSession: MediaSession
    private lateinit var musicNotificationManager: MusicNotificationManager

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    var isForegroundService = false

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        setupExoPlayer()
        mediaSession = MediaSession.Builder(this, exoPlayer).setSessionActivity(getSessionActivityPendingIntent()).build()
        musicNotificationManager = MusicNotificationManager(this, mediaSession, MusicPlayerNotificationListener(this))
        setupPlayerListener()
    }

    private fun setupExoPlayer() {
        exoPlayer.apply {
            setAudioAttributes(AudioAttributes.Builder().setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).setUsage(C.USAGE_MEDIA).build(), true)
            setWakeMode(C.WAKE_MODE_NONE)
            setHandleAudioBecomingNoisy(true)
        }
    }

    private fun setupPlayerListener() {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        Log.d(TAG, "Player is ready")
                    }

                    Player.STATE_BUFFERING -> {
                        Log.d(TAG, "Player is buffering")
                    }

                    Player.STATE_ENDED -> {
                        Log.d(TAG, "Playback ended")
                        exoPlayer.seekTo(0)
                        exoPlayer.pause()
                    }

                    Player.STATE_IDLE -> {
                        Log.d(TAG, "Player is idle")
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                Log.d(TAG, "Is playing changed: $isPlaying")
            }

            override fun onPlayerError(error: PlaybackException) {
                Log.e(TAG, "Player error: ${error.message}")
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()

        serviceScope.cancel()
        exoPlayer.stop()
        exoPlayer.release()
        mediaSession.release()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        return START_NOT_STICKY
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    @SuppressLint("ObsoleteSdkInt")
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, MUSIC_PLAYBACK, NotificationManager.IMPORTANCE_LOW).apply {
                setSound(null, null)
                description = "Music playback notifications"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun getSessionActivityPendingIntent(): PendingIntent {
        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName)?.let {
            PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_IMMUTABLE)
        }
        return activityIntent ?: PendingIntent.getActivity(this, 0, Intent(), PendingIntent.FLAG_IMMUTABLE)
    }

    companion object {
        private const val TAG = "MusicService"
    }
}