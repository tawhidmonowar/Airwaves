package org.tawhid.airwaves.book.presentation

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.books
import airwaves.composeapp.generated.resources.setting
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.book.openbook.presentation.book_list.BookListScreenRoot
import org.tawhid.airwaves.book.openbook.presentation.book_list.BookListViewModel
import org.tawhid.airwaves.theme.Shapes
import org.tawhid.airwaves.theme.xSmallPadding
import org.tawhid.airwaves.theme.xxSmallPadding

@Composable
fun BookHomeScreenRoot(
    viewModel: BookHomeViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
    onSettingClick: () -> Unit,
    innerPadding: PaddingValues
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookHomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookHomeAction.OnSettingClick -> onSettingClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        onBookClick = onBookClick,
        innerPadding = innerPadding
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3WindowSizeClassApi::class
)
@Composable
private fun BookHomeScreen(
    state: BookHomeState,
    onAction: (BookHomeAction) -> Unit,
    onBookClick: (Book) -> Unit,
    innerPadding: PaddingValues
) {

    val windowSizeClass = calculateWindowSizeClass()
    val isMediumExpandedWWSC by remember(windowSizeClass) {
        derivedStateOf {
            windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
        }
    }

    val pagerState = rememberPagerState { 2 }
    val bookListViewModel = koinViewModel<BookListViewModel>()
    val scrollBehavior = if (isMediumExpandedWWSC) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    } else {
        TopAppBarDefaults.enterAlwaysScrollBehavior()
    }

    val searchVisible by remember { mutableStateOf(false) }
    val searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(BookHomeAction.OnTabSelected(pagerState.currentPage))
    }

    val animatedStartPadding by animateDpAsState(
        targetValue = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
        animationSpec = tween(durationMillis = 300)
    )
    Surface(
        modifier = Modifier.padding(
            start = animatedStartPadding,
            top = innerPadding.calculateTopPadding(),
            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
            bottom = innerPadding.calculateBottomPadding()
        ),
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
                            onAction(BookHomeAction.OnSettingClick)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = stringResource(Res.string.setting),
                            )
                        }
                        IconButton(onClick = {
                            onAction(BookHomeAction.OnSettingClick)
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
                        .padding(horizontal = xSmallPadding, vertical = xxSmallPadding)
                        .clip(Shapes.medium)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    selectedTabIndex = state.selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    divider = {},
                    indicator = {}
                ) {
                    Tab(
                        modifier = Modifier.weight(1f),
                        selected = state.selectedTabIndex == 0,
                        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedContentColor = Color.Black.copy(alpha = 0.8f),
                        onClick = {
                            onAction(BookHomeAction.OnTabSelected(0))
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .fillMaxSize()
                                .background(
                                    color = if (state.selectedTabIndex == 0) MaterialTheme.colorScheme.primary else Color.Transparent,
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
                        unselectedContentColor = Color.Black.copy(alpha = 0.8f),
                        onClick = {
                            onAction(BookHomeAction.OnTabSelected(1))
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .fillMaxSize()
                                .background(
                                    color = if (state.selectedTabIndex == 1) MaterialTheme.colorScheme.primary else Color.Transparent,
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

                HorizontalPager(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    state = pagerState
                ) { pageIndex ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
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