package org.tawhid.airwaves.radio.presentation.radio_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.tawhid.airwaves.book.audiobook.domain.AudioBook
import org.tawhid.airwaves.core.theme.LightBlue
import org.tawhid.airwaves.core.theme.Shapes
import org.tawhid.airwaves.core.theme.imageSize
import org.tawhid.airwaves.core.theme.xSmallPadding
import org.tawhid.airwaves.core.theme.xxSmallPadding
import org.tawhid.airwaves.radio.domain.Radio

@Composable
fun RadioListItem(
    radio: Radio,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = Shapes.small,
        modifier = modifier
            .padding(horizontal = xxSmallPadding)
            .clickable(
                onClick = onClick
            ),
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
            Box(
                modifier = Modifier.height(imageSize).clip(Shapes.extraSmall),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = radio.imgUrl,
                    //placeholder = painterResource(id = R.drawable.placeholder),
                    //error = painterResource(id = R.drawable.error_image),
                    contentDescription = radio.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(
                        ratio = 1f
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = radio.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = radio.url,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                radio.homepage?.let { homepage ->
                    Text(
                        text = homepage,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Icon(
                modifier = Modifier
                    .size(36.dp),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}