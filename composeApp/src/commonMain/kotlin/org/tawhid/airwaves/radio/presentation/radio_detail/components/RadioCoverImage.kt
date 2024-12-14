package org.tawhid.airwaves.radio.presentation.radio_detail.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.radio_cover
import airwaves.composeapp.generated.resources.radio_cover_background
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AnimatedRadioCoverImage(
    modifier: Modifier = Modifier,
    isSongPlaying: Boolean = true,
    painter: Painter
) {
    var currentRotation by remember {
        mutableFloatStateOf(0f)
    }
    val rotation = remember {
        Animatable(currentRotation)
    }
    LaunchedEffect(isSongPlaying) {
        if (isSongPlaying) {
            rotation.animateTo(
                targetValue = currentRotation + 360f, animationSpec = infiniteRepeatable(
                    animation = tween(5000, easing = LinearEasing), repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        } else {
            if (currentRotation > 0f) {
                rotation.animateTo(
                    targetValue = currentRotation + 50, animationSpec = tween(
                        1250, easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
        }
    }
    RadioCoverImage(
        modifier = modifier,
        painter = painter,
        rotationDegrees = rotation.value
    )
}

@Composable
private fun RadioCoverImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    rotationDegrees: Float = 0f
) {
    Box(
        modifier = modifier
            .aspectRatio(1.0f)
            .clip(RoundedCornerShape(100))
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotationDegrees),
            painter = painterResource(Res.drawable.radio_cover_background),
            contentDescription = stringResource(Res.string.radio_cover_background)
        )
        Image(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .rotate(rotationDegrees)
                .aspectRatio(1.0f)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(100)),
            painter = painter,
            contentDescription = stringResource(Res.string.radio_cover)
        )
    }
}