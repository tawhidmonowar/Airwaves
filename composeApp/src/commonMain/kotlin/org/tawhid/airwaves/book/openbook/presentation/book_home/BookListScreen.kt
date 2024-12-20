package org.tawhid.airwaves.book.openbook.presentation.book_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.book.openbook.presentation.book_home.components.SavedBookList
import org.tawhid.airwaves.book.openbook.presentation.book_home.components.VerticalBookListItem
import org.tawhid.airwaves.core.theme.cardMinSize
import org.tawhid.airwaves.core.theme.cardMinSizeSmall
import org.tawhid.airwaves.core.theme.xSmallPadding
import org.tawhid.airwaves.core.theme.xxSmallPadding
import org.tawhid.airwaves.core.theme.xxxSmallPadding

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookListAction.OnBookClick -> onBookClick(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {
    val windowSizeClass = calculateWindowSizeClass()
    val isMediumExpandedWWSC by remember(windowSizeClass) {
        derivedStateOf {
            windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
        }
    }

    Column(
        modifier = Modifier.widthIn(max = 1000.dp).fillMaxSize()
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(
                if (isMediumExpandedWWSC) {
                    cardMinSize
                } else {
                    cardMinSizeSmall
                }
            ),
            verticalItemSpacing = xxSmallPadding,
            horizontalArrangement = Arrangement.spacedBy(xxSmallPadding),
            modifier = Modifier.padding(horizontal = xxxSmallPadding)
        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                if (state.savedBooks.isEmpty()) {
                    Text(
                        text = "No saved books!",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                } else {
                    SavedBookList(
                        books = state.savedBooks,
                        onBookClick = { book ->
                            onAction(BookListAction.OnBookClick(book))
                        }
                    )
                }
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(xSmallPadding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Trending Books", style = MaterialTheme.typography.titleMedium)
                }

            }

            items(items = state.trendingBooks.take(
                if (isMediumExpandedWWSC) {
                    96
                } else {
                    99
                }
            ),
                key = { it.id }
            ) { book ->
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    when {
                        state.errorMsg != null -> {
                            Text(
                                text = state.errorMsg.asString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        state.trendingBooks.isEmpty() -> {
                            Text(
                                text = "No search result",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        else -> {
                            VerticalBookListItem(
                                book = book,
                                onClick = {
                                    onAction(BookListAction.OnBookClick(book))
                                }
                            )
                        }
                    }
                }
            }
        }


        /*        item{
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        if (state.savedBooks.isEmpty()) {
                            Text(
                                text = "No saved books!",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            SavedBookList(
                                books = state.savedBooks,
                                onBookClick = { book ->
                                    onAction(BookListAction.OnBookClick(book))
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }*/
        /* item {
             TrendingBookList(
                 books = state.trendingBooks,
                 onBookClick = { book ->
                     onAction(BookListAction.OnBookClick(book))
                 },
                 modifier = Modifier.fillMaxSize()
             )
         }*/
    }
}


/*

    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }
    val searchResultListState = rememberLazyListState()
    val savedBooksListState = rememberLazyListState()

    LaunchedEffect(state.searchResult) {
        searchResultListState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }

    */


/*
    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = { query ->
                onAction(BookListAction.OnSearchQueryChange(query))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth().padding(15.dp)
        )

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    selectedTabIndex = state.selectedTabIndex,
                    containerColor = DesertWhite,
                    indicator = { tabPosition ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPosition[state.selectedTabIndex]),
                            height = 3.dp,
                            color = SandYellow
                        )
                    }
                ) {
                    Tab(
                        modifier = Modifier
                            .weight(1f),
                        selected = state.selectedTabIndex == 0,
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        onClick = {
                            onAction(BookListAction.OnTabSelected(0))
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 12.dp),
                            text = "Search Result"
                        )
                    }
                    Tab(
                        modifier = Modifier
                            .weight(1f),
                        selected = state.selectedTabIndex == 1,
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        onClick = {
                            onAction(BookListAction.OnTabSelected(1))
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 12.dp),
                            text = "Saved"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))



                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    state = pagerState
                ) { pageIndex ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMsg != null -> {
                                            Text(
                                                text = state.errorMsg.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        state.trendingBooks.isEmpty() -> {
                                            Text(
                                                text = "No search result",
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onBackground
                                            )
                                        }

                                        else -> {
                                            BookList(
                                                books = state.trendingBooks,
                                                onBookClick = { book ->
                                                    onAction(BookListAction.OnBookClick(book))
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = searchResultListState
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (state.savedBooks.isEmpty()) {
                                    Text(
                                        text = "No saved books!",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                } else {
                                    BookList(
                                        books = state.savedBooks,
                                        onBookClick = { book ->
                                            onAction(BookListAction.OnBookClick(book))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = savedBooksListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }



}

 */