package org.tawhid.airwaves.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player


actual class PlayerController(
    private val context: Context
) {

    val player = ExoPlayer.Builder(context).build()
    val pathSource = "https://ia803108.us.archive.org/10/items/a_christmas_miscellany_2018_1807_librivox/christmasmiscellany2018_01_various_128kb.mp3"

    actual fun myName() {
        val mediaItem = MediaItem.fromUri(pathSource)

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


}