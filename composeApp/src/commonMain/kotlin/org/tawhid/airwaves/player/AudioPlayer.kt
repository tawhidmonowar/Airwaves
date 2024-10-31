package org.tawhid.airwaves.player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
expect fun AudioPlayer(
    modifier: Modifier,
    url: String,
    startTime: Color,
    endTime: Color,
    volumeIconColor: Color,
    playIconColor: Color,
    sliderTrackColor: Color,
    sliderIndicatorColor: Color
)