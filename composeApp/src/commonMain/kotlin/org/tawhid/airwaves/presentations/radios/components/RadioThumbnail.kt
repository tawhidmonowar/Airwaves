package org.tawhid.airwaves.presentations.radios.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import org.tawhid.airwaves.data.models.radio.RadioData
import org.tawhid.airwaves.theme.mediumPadding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import org.tawhid.airwaves.theme.imageSize


@Composable
fun RadioThumbnail(
    radioData: RadioData,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .shadow(2.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .background(Color.White)
            .padding(mediumPadding),
        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
    ) {
        AsyncImage(
            modifier = Modifier
                .size(imageSize)
                .clip(RoundedCornerShape(10))
                .background(Color.Gray),
            model = radioData.favicon,
            //error = painterResource(Res.drawable.logo),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(mediumPadding)
        ) {
            Text(
                text = radioData.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            radioData.homepage?.let {
                Text(
                    text = radioData.homepage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Text(
                text = radioData.url,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}