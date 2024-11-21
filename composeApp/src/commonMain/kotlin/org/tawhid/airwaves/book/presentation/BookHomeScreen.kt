package org.tawhid.airwaves.book.presentation

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.books
import airwaves.composeapp.generated.resources.setting
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.book.openbook.presentation.book_list.BookListScreenRoot
import org.tawhid.airwaves.book.openbook.presentation.book_list.BookListViewModel
import org.tawhid.airwaves.theme.DesertWhite

@Composable
fun BookHomeScreenRoot(
    bookHomeViewModel: BookHomeViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
    innerPadding: PaddingValues
) {
    val state by bookHomeViewModel.state.collectAsStateWithLifecycle()
    BookHomeScreen(
        state = state,
        onAction = bookHomeViewModel::onAction,
        onBookClick = onBookClick,
        innerPadding = innerPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookHomeScreen(
    onBookClick: (Book) -> Unit,
    state: BookHomeState,
    onAction: (BookHomeAction) -> Unit,
    innerPadding: PaddingValues
) {
    val pagerState = rememberPagerState { 2 }
    val bookListViewModel = koinViewModel<BookListViewModel>()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(BookHomeAction.OnTabSelected(pagerState.currentPage))
    }

    Surface(
        modifier = Modifier.padding(innerPadding),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(Res.string.books))
                    },
                    navigationIcon = {
                        IconButton(onClick = { TODO() }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = stringResource(Res.string.setting),
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = stringResource(Res.string.setting),
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    modifier = Modifier
                        .padding(10.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    selectedTabIndex = state.selectedTabIndex,
                    containerColor = DesertWhite,
                    indicator = { tabPosition ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPosition[state.selectedTabIndex]),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                ) {
                    Tab(
                        modifier = Modifier.weight(1f),
                        selected = state.selectedTabIndex == 0,
                        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        onClick = {
                            onAction(BookHomeAction.OnTabSelected(0))
                        }
                    ) {
                        val backgroundColor by animateColorAsState(
                            targetValue = if (state.selectedTabIndex == 0) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.Transparent
                            }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                                .background(
                                    color = backgroundColor,
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(vertical = 12.dp),
                                text = "Audiobooks"
                            )
                        }
                    }
                    Tab(
                        modifier = Modifier
                            .weight(1f),
                        selected = state.selectedTabIndex == 1,
                        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        onClick = {
                            onAction(BookHomeAction.OnTabSelected(1))
                        }
                    ) {

                        val backgroundColor by animateColorAsState(
                            targetValue = if (state.selectedTabIndex == 1)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Transparent
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                                .background(
                                    color = backgroundColor,
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(vertical = 12.dp),
                                text = "Open Library"
                            )
                        }
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
                                Text(text = "Audiobooks")
                            }

                            1 -> {
                                BookListScreenRoot(
                                    viewModel = bookListViewModel,
                                    onBookClick = onBookClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}