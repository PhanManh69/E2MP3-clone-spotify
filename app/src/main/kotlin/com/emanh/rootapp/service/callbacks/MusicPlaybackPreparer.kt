package com.emanh.rootapp.service.callbacks

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

@androidx.media3.common.util.UnstableApi
class MusicPlaybackPreparer(
    private val player: ExoPlayer
) : Player.Listener {

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        if (isPlaying) {
            player.play()
        } else {
            player.pause()
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            Player.STATE_READY -> {
                // Player is ready to play
            }
            Player.STATE_BUFFERING -> {
                // Player is buffering
            }
            Player.STATE_ENDED -> {
                // Playback has ended
                player.seekTo(0)
                player.pause()
            }
            Player.STATE_IDLE -> {
                // Player is idle
            }
        }
    }
}