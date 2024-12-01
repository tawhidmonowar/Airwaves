package org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.tawhid.airwaves.book.audiobook.domain.AudioBookTracks
import org.tawhid.airwaves.core.theme.LightBlue
import org.tawhid.airwaves.core.theme.Shapes
import org.tawhid.airwaves.core.theme.xSmallPadding
import org.tawhid.airwaves.core.theme.xxSmallPadding

@Composable
fun AudioBookTrackItem(
    audioBookTracks: AudioBookTracks,
) {
    Surface(
        shape = Shapes.small,
        modifier = Modifier
            .padding(horizontal = xxSmallPadding),
        color = LightBlue.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier
                .padding(xSmallPadding)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(xSmallPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                audioBookTracks.title?.let { title ->
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                audioBookTracks.listenUrl?.let { listenUrl ->
                    Text(
                        text = listenUrl,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                audioBookTracks.playtime?.let { playtime ->
                    Text(
                        text = "Play Time: $playtime",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Icon(
                modifier = Modifier
                    .size(36.dp),
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null
            )
        }
    }
}