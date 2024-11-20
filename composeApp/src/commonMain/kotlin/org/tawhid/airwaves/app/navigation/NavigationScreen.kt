package org.tawhid.airwaves.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.app.navigation.components.BottomNavigationBar
import org.tawhid.airwaves.app.navigation.components.NavigationItem
import org.tawhid.airwaves.app.navigation.components.NavigationSideBar
import org.tawhid.airwaves.app.navigation.components.navigationItemsLists
import org.tawhid.airwaves.book.presentation.SelectedBookViewModel
import org.tawhid.airwaves.book.presentation.book_detail.BookDetailAction
import org.tawhid.airwaves.book.presentation.book_detail.BookDetailScreenRoot
import org.tawhid.airwaves.book.presentation.book_detail.BookDetailViewModel
import org.tawhid.airwaves.book.presentation.book_list.BookListScreenRoot
import org.tawhid.airwaves.book.presentation.book_list.BookListViewModel
import org.tawhid.airwaves.presentations.books.BooksScreen
import org.tawhid.airwaves.presentations.home.HomeScreen
import org.tawhid.airwaves.presentations.podcasts.PodcastsScreen
import org.tawhid.airwaves.presentations.radios.RadioScreen

@Composable
fun NavigationScreenRoot() {
    val rootNavController = rememberNavController()
    NavHost(
        navController = rootNavController,
        startDestination = Route.BookGraph
    ) {
        navigation<Route.BookGraph>(
            startDestination = Route.NavScreen
        ) {
            composable<Route.NavScreen> {
                NavigationScreen(rootNavController)
            }
            composable<Route.BookList>(
                popEnterTransition = { slideInHorizontally() }
            ) {

                val viewModel = koinViewModel<BookListViewModel>()
                val selectedBookViewModel =
                    it.sharedKoinViewModel<SelectedBookViewModel>(rootNavController)

                LaunchedEffect(true) {
                    selectedBookViewModel.onSelectBook(null)
                }
                BookListScreenRoot(
                    viewModel = viewModel,
                    onBookClick = { book ->
                        selectedBookViewModel.onSelectBook(book)
                        rootNavController.navigate(
                            Route.BookDetail(book.id)
                        )
                    }
                )
            }
            composable<Route.BookDetail>(
                enterTransition = {
                    slideInHorizontally { initialOffset ->
                        initialOffset
                    }
                },
                exitTransition = {
                    slideOutHorizontally { initialOffset ->
                        initialOffset
                    }
                }
            ) {
                val selectedBookViewModel =
                    it.sharedKoinViewModel<SelectedBookViewModel>(rootNavController)
                val viewModel = koinViewModel<BookDetailViewModel>()
                val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                LaunchedEffect(selectedBook) {
                    selectedBook?.let {
                        viewModel.onAction(BookDetailAction.OnSelectedBookChange(it))
                    }
                }

                BookDetailScreenRoot(
                    viewModel = viewModel,
                    onBackClick = {
                        rootNavController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun NavigationScreen(rootNavController: NavHostController) {
    val windowSizeClass = calculateWindowSizeClass()
    val isMediumExpandedWWSC by remember(windowSizeClass) {
        derivedStateOf {
            windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
        }
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }
    val navigationItem by remember {
        derivedStateOf {
            navigationItemsLists.find { it.route == currentRoute }
        }
    }
    val isMainScreenVisible by remember(isMediumExpandedWWSC) {
        derivedStateOf {
            navigationItem != null
        }
    }
    val isBottomBarVisible by remember(isMediumExpandedWWSC) {
        derivedStateOf {
            if (!isMediumExpandedWWSC) {
                navigationItem != null
            } else {
                false
            }
        }
    }

    NavigationScreenExtend(
        rootNavController = rootNavController,
        navController = navController,
        currentRoute = currentRoute,
        isMediumExpandedWWSC = isMediumExpandedWWSC,
        isBottomBarVisible = isBottomBarVisible,
        isMainScreenVisible = isMainScreenVisible,
        onItemClick = { currentNavigationItem ->
            navController.navigate(currentNavigationItem.route) {
                popUpTo(navController.graph.startDestinationRoute ?: "") {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Composable
private fun NavigationScreenExtend(
    rootNavController: NavHostController,
    navController: NavHostController,
    currentRoute: String?,
    isMediumExpandedWWSC: Boolean,
    isBottomBarVisible: Boolean,
    isMainScreenVisible: Boolean,
    onItemClick: (NavigationItem) -> Unit,
) {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight })
            ) {
                BottomNavigationBar(
                    items = navigationItemsLists,
                    currentRoute = currentRoute,
                    onItemClick = { currentNavigationItem ->
                        onItemClick(currentNavigationItem)
                    }
                )
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = isMediumExpandedWWSC && isMainScreenVisible,
                enter = slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
            ) {
                NavigationSideBar(
                    items = navigationItemsLists,
                    currentRoute = currentRoute,
                    onItemClick = { currentNavigationItem ->
                        onItemClick(currentNavigationItem)
                    }
                )
            }
            NavHost(
                navController = navController,
                startDestination = Graph.NAVIGATION_SCREEN_GRAPH,
                modifier = Modifier.padding(innerPadding)
            ) {
                navGraph(rootNavController = rootNavController, innerPadding = innerPadding)
            }
        }
    }
}

private fun NavGraphBuilder.navGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues
) {
    navigation(
        startDestination = NavigationScreenRoute.Home.route,
        route = Graph.NAVIGATION_SCREEN_GRAPH
    ) {
        composable(route = NavigationScreenRoute.Home.route) {
            HomeScreen()
        }
        composable(route = NavigationScreenRoute.Podcast.route) {
            PodcastsScreen()
        }
        composable(route = NavigationScreenRoute.Book.route) {
            BooksScreen(rootNavController)
        }
        composable(route = NavigationScreenRoute.Radio.route) {
            RadioScreen(rootNavController)
        }
    }
}