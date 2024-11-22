package org.tawhid.airwaves.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.app.navigation.components.BottomNavigationBar
import org.tawhid.airwaves.app.navigation.components.NavigationSideBar
import org.tawhid.airwaves.app.navigation.components.navigationItemsLists
import org.tawhid.airwaves.book.openbook.presentation.SelectedBookViewModel
import org.tawhid.airwaves.book.openbook.presentation.book_detail.BookDetailAction
import org.tawhid.airwaves.book.openbook.presentation.book_detail.BookDetailScreenRoot
import org.tawhid.airwaves.book.openbook.presentation.book_detail.BookDetailViewModel
import org.tawhid.airwaves.book.presentation.BookHomeScreenRoot
import org.tawhid.airwaves.book.presentation.BookHomeViewModel
import org.tawhid.airwaves.core.presentation.setting.SettingScreenRoot
import org.tawhid.airwaves.core.presentation.setting.SettingViewModel
import org.tawhid.airwaves.presentations.home.HomeScreen
import org.tawhid.airwaves.presentations.podcasts.PodcastsScreen
import org.tawhid.airwaves.utils.DeviceType
import org.tawhid.airwaves.utils.getDeviceType

@Composable
fun NavigationScreenRoot(
    settingViewModel: SettingViewModel = koinViewModel()
) {
    NavigationScreen(settingViewModel)
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun NavigationScreen(
    settingViewModel: SettingViewModel
) {
    val windowSizeClass = calculateWindowSizeClass()
    val isMediumExpandedWWSC by remember(windowSizeClass) {
        derivedStateOf {
            windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
        }
    }
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
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
                        rootNavController.navigate(currentNavigationItem.route) {
                            popUpTo(NavigationScreenRoute.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        val isDesktop = remember {
            getDeviceType() == DeviceType.Desktop
        }

        val contentPadding = if (isDesktop) {
            PaddingValues(start = 80.dp)
        } else {
            if (isMediumExpandedWWSC && isMainScreenVisible) {
                PaddingValues(start = 80.dp)
            } else {
                innerPadding
            }
        }

        NavHost(
            navController = rootNavController,
            startDestination = Graph.NAVIGATION_SCREEN_GRAPH,
        ) {
            navGraph(rootNavController = rootNavController, innerPadding = contentPadding)
            composable<Route.Setting> {
                SettingScreenRoot(
                    viewModel = settingViewModel,
                    onBackClick = {
                        rootNavController.navigateUp()
                    }
                )
            }
        }



        AnimatedVisibility(
            visible = isMediumExpandedWWSC && isMainScreenVisible,
            enter = slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
        ) {
            NavigationSideBar(
                items = navigationItemsLists,
                currentRoute = currentRoute,
                onItemClick = { currentNavigationItem ->
                    rootNavController.navigate(currentNavigationItem.route) {
                        popUpTo(NavigationScreenRoute.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.navGraph(
    rootNavController: NavController,
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
            val bookHomeViewModel = koinViewModel<BookHomeViewModel>()
            val selectedBookViewModel =
                it.sharedKoinViewModel<SelectedBookViewModel>(rootNavController)

            LaunchedEffect(true) {
                selectedBookViewModel.onSelectBook(null)
            }

            BookHomeScreenRoot(
                viewModel = bookHomeViewModel,
                onBookClick = { book ->
                    selectedBookViewModel.onSelectBook(book)
                    rootNavController.navigate(
                        Route.BookDetail(book.id)
                    )
                },
                onSettingClick = {
                    rootNavController.navigate(
                        Route.Setting
                    )
                },
                innerPadding = innerPadding
            )
        }
        composable<Route.BookDetail>(
            enterTransition = {
                fadeIn(animationSpec = tween(100)) +
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
            },
            exitTransition = {
                fadeOut(animationSpec = tween(100)) +
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }

            }
        ) { it ->

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

        composable(route = NavigationScreenRoute.Radio.route) {

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