package org.tawhid.airwaves.core.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

actual class PlayerController(context: Context) {

    private val player = ExoPlayer.Builder(context).build()

    actual fun play(audioUrl: String) {
        val mediaItem = MediaItem.fromUri(audioUrl)

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
            }

            override fun onPlayerErrorChanged(error: PlaybackException?) {
                super.onPlayerErrorChanged(error)
            }
        })

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    actual fun pause() {
        if (player.isPlaying) {
            player.pause()
        }
    }
}