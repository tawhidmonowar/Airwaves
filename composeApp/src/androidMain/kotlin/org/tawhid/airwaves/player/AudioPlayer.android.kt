package org.tawhid.airwaves.player
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
        AudioPlayer(
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
