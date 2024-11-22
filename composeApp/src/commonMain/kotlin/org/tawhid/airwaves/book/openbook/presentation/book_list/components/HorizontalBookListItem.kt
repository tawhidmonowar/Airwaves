package org.tawhid.airwaves.book.openbook.presentation.book_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.theme.LightBlue
import org.tawhid.airwaves.theme.SandYellow
import org.tawhid.airwaves.theme.Shapes
import org.tawhid.airwaves.theme.cardMinSize
import org.tawhid.airwaves.theme.imageSizeMedium
import org.tawhid.airwaves.theme.xSmallPadding
import org.tawhid.airwaves.theme.xxSmallPadding
import kotlin.math.round

@Composable
fun HorizontalBookListItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemModifier = Modifier
        .padding(horizontal = xSmallPadding, vertical = xxSmallPadding)
        .clip(Shapes.medium)
        .background(color = MaterialTheme.colorScheme.surfaceVariant)
        .clickable(onClick = onClick)

    Surface(
        modifier = modifier.then(itemModifier),
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(xSmallPadding)
                .fillMaxWidth()
                .widthIn(max = cardMinSize)
                .height(IntrinsicSize.Min),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.65f)
                    .clip(Shapes.medium)
            ) {
                AsyncImage(
                    model = book.imgUrl,
                    //error = painterResource(Res.drawable.logo),,
                    //placeholder = painterResource(id = R.drawable.placeholder), // Add a placeholder for better UX
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(xxSmallPadding))

            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            book.authors.firstOrNull()?.let { authorName ->
                Text(
                    text = authorName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}