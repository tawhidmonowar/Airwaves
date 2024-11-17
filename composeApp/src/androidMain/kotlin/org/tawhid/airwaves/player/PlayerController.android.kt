package org.tawhid.airwaves.player

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer


actual class PlayerController(context: Context) {

    val player = ExoPlayer.Builder(context).build()

    actual fun prepare(pathSource: String) {

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

    actual fun start() {
        player.play()
    }

    actual fun pause() {
        if (player.isPlaying)
            player.pause()
    }

    actual fun stop() {
        player.stop()
    }

    actual fun release() {
        player.release()
    }


    @Composable
    actual fun playCompose(audioUrl: String) {

        var isPlaying by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(true) }
        var currentTime by remember { mutableStateOf(0f) }
        var metadataText by remember { mutableStateOf("Loading metadata...") }
        // State to hold metadata text
        DisposableEffect(audioUrl) {
            val mediaItem = MediaItem.fromUri(audioUrl)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true
            val listener = object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    isLoading = state == Player.STATE_BUFFERING
                    if (state == Player.STATE_READY) {
                        isPlaying = player.isPlaying
                        // Retrieve and format metadata
                        metadataText = buildString {
                            append("Title: ${player.mediaMetadata.title}\n")
                            append("Artist: ${player.mediaMetadata.artist}\n")
                            append("Album: ${player.mediaMetadata.albumTitle}\n")
                            append("Genre: ${player.mediaMetadata.genre}\n")
                            append("Station: ${player.mediaMetadata.station}\n")

                        }
                        Log.d("RadioPlayer Metadata", metadataText)
                    }
                    if (state == Player.STATE_ENDED) {
                        isPlaying = false
                    }
                }
            }
            player.addListener(listener)
            onDispose {
                player.removeListener(listener)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(text = metadataText)  // Display metadata in Text view
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        if (isPlaying) {
                            player.pause()
                        } else {
                            player.play()
                        }
                    }) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.PlayArrow else Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }

    actual fun isPlaying(): Boolean {
        return player.isPlaying
    }
}
