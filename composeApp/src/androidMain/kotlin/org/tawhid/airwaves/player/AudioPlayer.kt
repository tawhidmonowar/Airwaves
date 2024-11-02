package org.tawhid.airwaves.player

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*


@OptIn(UnstableApi::class)
@Composable
fun AudioPlayer(
    audioURL: String,
    startTime: Color,
    endTime: Color,
    volumeIconColor: Color,
    playIconColor: Color,
    sliderTrackColor: Color,
    sliderIndicatorColor: Color
) {

    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    var isPlaying by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var currentTime by remember { mutableStateOf(0f) }
    var metadataText by remember { mutableStateOf("Loading metadata...") }  // State to hold metadata text

    DisposableEffect(audioURL) {
        val mediaItem = MediaItem.fromUri(audioURL)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isLoading = state == Player.STATE_BUFFERING
                if (state == Player.STATE_READY) {
                    isPlaying = exoPlayer.isPlaying
                    // Retrieve and format metadata
                    metadataText = buildString {
                        append("Title: ${exoPlayer.mediaMetadata.title}\n")
                        append("Artist: ${exoPlayer.mediaMetadata.artist}\n")
                        append("Album: ${exoPlayer.mediaMetadata.albumTitle}\n")
                        append("Genre: ${exoPlayer.mediaMetadata.genre}\n")
                        append("Station: ${exoPlayer.mediaMetadata.station}\n")
                        append("Station: ${exoPlayer.mediaMetadata.displayTitle}\n")
                        append("Station: ${exoPlayer.mediaMetadata.description}\n")
                        append("Station: ${exoPlayer.mediaMetadata.releaseDay}\n")
                        append("Station: ${exoPlayer.mediaMetadata.mediaType}\n")
                        append("Station: ${exoPlayer.mediaMetadata.recordingDay}\n")
                        append("Station: ${exoPlayer.mediaMetadata.userRating}\n")
                        append("Station: ${exoPlayer.mediaMetadata.totalTrackCount}\n")
                        append("Station: ${exoPlayer.mediaMetadata.overallRating}\n")
                        append("Station: ${exoPlayer.mediaMetadata.subtitle}\n")
                        append("Station: ${exoPlayer.mediaMetadata.albumArtist}\n")
                        append("Station: ${exoPlayer.mediaMetadata.trackNumber}\n")
                        append("Station: ${exoPlayer.mediaMetadata.composer}\n")
                        append("Station: ${exoPlayer.mediaMetadata.totalDiscCount}\n")
                        append("Station: ${exoPlayer.mediaMetadata.displayTitle}\n")
                        append("Station: ${exoPlayer.mediaMetadata.durationMs}\n")
                        append("Station: ${exoPlayer.mediaMetadata.artworkData}\n")
                        append("Station: ${exoPlayer.mediaMetadata.artworkDataType}\n")
                        append("Station: ${exoPlayer.mediaMetadata.mediaType}\n")
                        append("Station: ${exoPlayer.mediaMetadata.extras}\n")
                        append("Station: ${exoPlayer.mediaMetadata.writer}\n")
                        append("Station: ${exoPlayer.mediaMetadata.compilation}\n")
                        append("Station: ${exoPlayer.mediaMetadata.albumTitle}\n")
            


                    }
                    Log.d("RadioPlayer Metadata", metadataText)
                }
                if (state == Player.STATE_ENDED) {
                    isPlaying = false
                }
            }


        }

        exoPlayer.addListener(listener)



        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
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
                        exoPlayer.pause()
                    } else {
                        exoPlayer.play()
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

fun formatTime(ms: Long): String {
    val seconds = (ms / 1000) % 60
    val minutes = (ms / (1000 * 60)) % 60
    val hours = (ms / (1000 * 60 * 60)) % 24

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}