package org.tawhid.airwaves.book.audiobook.presentation.audiobook_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import org.tawhid.airwaves.core.theme.Shapes
import org.tawhid.airwaves.core.theme.cardMinSize
import org.tawhid.airwaves.core.theme.xSmallPadding
import org.tawhid.airwaves.core.theme.xxSmallPadding
import org.tawhid.airwaves.core.theme.xxxSmallPadding

@Composable
fun AudioBookVerticalGridItem(
    audioBook: AudioBook,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(xxxSmallPadding)
            .clip(Shapes.small)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(Modifier.fillMaxWidth()),
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
            audioBook.imgUrl?.let { BookImage(it) }
            Spacer(modifier = Modifier.height(xxSmallPadding))
            BookTitle(audioBook.title)
            audioBook.authors.firstOrNull()?.let { authorName ->
                BookAuthor(authorName)
            }
        }
    }
}

@Composable
fun BookImage(imgUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.65f)
            .clip(Shapes.small)
    ) {
        AsyncImage(
            model = imgUrl,
            //placeholder = painterResource(id = R.drawable.placeholder),
            //error = painterResource(id = R.drawable.error_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BookTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BookAuthor(authorName: String) {
    Text(
        text = authorName,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}
