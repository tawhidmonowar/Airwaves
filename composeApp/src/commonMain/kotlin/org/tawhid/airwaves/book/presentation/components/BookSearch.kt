package org.tawhid.airwaves.book.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.book.openbook.presentation.book_list.BookListAction
import org.tawhid.airwaves.book.openbook.presentation.book_list.BookListViewModel
import org.tawhid.airwaves.core.presentation.components.EmbeddedSearchBar

@Composable
fun BookSearch(
    bookListViewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
    onBackClick: () -> Unit,
) {
    val state by bookListViewModel.state.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    EmbeddedSearchBar(
        searchQuery = state.searchQuery,
        onSearchQueryChange = { query ->
            bookListViewModel.onAction(BookListAction.OnSearchQueryChange(query))
        },
        onImeSearch = {
            keyboardController?.hide()
        },
        content = {
            BookSearchResult(
                state = state,
                onBookClick = onBookClick
            )
        },
        onBackClick = {
            onBackClick()
        }
    )
}
