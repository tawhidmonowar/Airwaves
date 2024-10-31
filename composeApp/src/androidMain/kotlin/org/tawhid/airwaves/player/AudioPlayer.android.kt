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

@Composable
actual fun AudioPlayer(
    modifier: Modifier,
    url: String,
    startTime: Color,
    endTime: Color,
    volumeIconColor: Color,
    playIconColor: Color,
    sliderTrackColor: Color,
    sliderIndicatorColor: Color
) {
    if (isAudioFile(url)) {
        ExoPlayerAudioPlayer(
            audioURL = url,
            startTime = startTime,
            endTime = endTime,
            volumeIconColor = volumeIconColor,
            playIconColor = playIconColor,
            sliderTrackColor = sliderTrackColor,
            sliderIndicatorColor = sliderIndicatorColor
        )
    }
}

fun isAudioFile(url: String?): Boolean {
    return true
}

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerAudioPlayer(
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
    var isPlayingAudio by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var currentTime by remember { mutableStateOf(0f) }
    var duration by remember { mutableStateOf(0f) }
    var volume by remember { mutableStateOf(1f) }

    DisposableEffect(audioURL) {
        val mediaItem = MediaItem.fromUri(audioURL)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isLoading = state == Player.STATE_BUFFERING
                if (state == Player.STATE_READY) {
                    //duration = exoPlayer.duration.toFloat()
                    isPlayingAudio = exoPlayer.isPlaying
                }
                if (state == Player.STATE_ENDED) {
                    isPlayingAudio = false
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                isPlayingAudio = isPlaying
            }
        }
        exoPlayer.addListener(listener)

        val updateTimeJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                if (exoPlayer.isPlaying) {
                    currentTime = exoPlayer.currentPosition.toFloat()
                }
                delay(1000L)
            }
        }

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
            updateTimeJob.cancel()
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
            Slider(
                value = currentTime,
                onValueChange = { exoPlayer.seekTo(it.toLong()) },
                // valueRange = 0f..duration,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = sliderIndicatorColor,
                    activeTrackColor = sliderTrackColor
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formatTime(currentTime.toLong()))
                Text(formatTime(duration.toLong()))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    if (isPlayingAudio) {
                        exoPlayer.pause()
                    } else {
                        exoPlayer.play()
                    }
                }) {
                    Icon(
                        imageVector = if (isPlayingAudio) Icons.Default.Delete else Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = playIconColor
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = {
                    volume = if (volume == 1f) 0f else 1f
                    exoPlayer.volume = volume
                }) {
                    Icon(
                        imageVector = if (volume == 1f) Icons.Default.Delete else Icons.Default.Delete,
                        contentDescription = null,
                        tint = volumeIconColor
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
