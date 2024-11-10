package org.tawhid.airwaves.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(ExperimentalFoundationApi::class, KoinExperimentalAPI::class)
@Composable
fun PlayingOverlay(
    isPlaying: Boolean,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val viewModel = koinViewModel<PlayerViewModel>()

    if (isPlaying) {
        if (viewModel.isCollapsed) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End // Align to the end (right)
            ) {
                IconButton(
                    modifier = modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(5.dp),
                    onClick = { viewModel.toggleCollapsed() }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Expand"
                    )
                }
            }
        } else {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(5.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
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
                            .clip(shape = MaterialTheme.shapes.medium)
                            .background(Color.Gray)
                    )

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f)
                    ) {
                        Text(text = "Song Name", maxLines = 1)
                        Text(
                            modifier = Modifier.basicMarquee(
                                delayMillis = 0,
                                initialDelayMillis = 0,
                                iterations = Int.MAX_VALUE,
                            ),
                            text = "lorem ipsum dolor sit amet, consectetur adipiscing elit. lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                            maxLines = 1,
                        )
                    }

                    IconButton(onClick = {viewModel.stop()}) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Pause or Play"
                        )
                    }

                    IconButton(
                        onClick = { viewModel.toggleCollapsed() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Collapse"
                        )
                    }
                }
            }
        }
    }
}
