package org.tawhid.airwaves.core.player.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.core.theme.Shapes
import org.tawhid.airwaves.core.theme.imageSize

@Composable
fun PlayingOverlay(
    modifier: Modifier = Modifier,
    onPlayerClick : () -> Unit
) {
    val playerViewModel = koinViewModel<PlayerViewModel>()
    val state by playerViewModel.state.collectAsState()

    if (state.isPlaying) {
        if (state.isCollapsed) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    modifier = modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(5.dp),
                    onClick = { playerViewModel.onAction(PlayerAction.OnCollapseClick) }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Expand",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        } else {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clip(Shapes.small)
                    .background(androidx.compose.ui.graphics.Color.White)
                    .padding(2.dp)
                    .clip(Shapes.small)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(100))
                            .background(androidx.compose.ui.graphics.Color.White)
                            .padding(2.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(imageSize)
                                .clip(RoundedCornerShape(100))
                                .background(androidx.compose.ui.graphics.Color.White),
                            model = "https://icecast.walmradio.com:8443/christmas.png",
                            //error = painterResource(Res.drawable.logo),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f)
                    ) {
                        Text(text = "Song Name", maxLines = 1, color = MaterialTheme.colorScheme.onPrimary,style = MaterialTheme.typography.titleMedium)
                        Text(
                            modifier = Modifier.basicMarquee(
                                initialDelayMillis = 0,
                                iterations = Int.MAX_VALUE,
                            ),
                            text = "lorem ipsum dolor sit amet, consectetur adipiscing elit. lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                        )
                    }

                    IconButton(onClick = {
                        onPlayerClick()
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Pause or Play",

                        )
                    }

                    IconButton(
                        onClick = {
                            playerViewModel.onAction(PlayerAction.OnCollapseClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Collapse",
                        )
                    }
                }
            }
        }
    }
}