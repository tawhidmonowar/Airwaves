package org.tawhid.airwaves.book.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.book.openbook.presentation.book_list.BookListState
import org.tawhid.airwaves.book.openbook.presentation.book_list.components.BookList
import org.tawhid.airwaves.theme.DesertWhite

@Composable
fun BookSearchResult(
    state: BookListState,
    onBookClick: (Book) -> Unit
) {
    val searchResultListState = rememberLazyListState()

    LaunchedEffect(state.searchResult) {
        searchResultListState.animateScrollToItem(0)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = DesertWhite
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    when {
                        state.errorMsg != null -> {
                            Text(
                                text = state.errorMsg.toString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        state.searchResult.isEmpty() -> {
                            Text(
                                text = "No search result",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        else -> {
                            BookList(
                                books = state.searchResult,
                                onBookClick = { book ->
                                    onBookClick(book)
                                },
                                modifier = Modifier.fillMaxSize(),
                                scrollState = searchResultListState
                            )
                        }
                    }
                }
            }
        }
    }
}