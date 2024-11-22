package org.tawhid.airwaves.book.openbook.presentation.book_list.components


import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.theme.xSmallPadding
import org.tawhid.airwaves.theme.xxSmallPadding

@Composable
fun SavedBookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(xSmallPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Saved Books", style = MaterialTheme.typography.titleMedium)
            Text(text = "View All", style = MaterialTheme.typography.titleMedium)
        }
        LazyRow(
            state = listState,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            listState.scrollBy(-dragAmount)
                        }
                    }
                }
        ) {
            items(
                items = books,
                key = { it.id }
            ) { book ->
                HorizontalBookListItem(
                    book = book,
                    onClick = {
                        onBookClick(book)
                    }
                )
            }
        }
    }
}