package org.tawhid.airwaves.app.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.book.audiobook.presentation.SelectedAudioBookViewModel
import org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail.AudioBookDetailAction
import org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail.AudioBookDetailScreenRoot
import org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail.AudioBookDetailViewModel
import org.tawhid.airwaves.book.openbook.presentation.SelectedBookViewModel
import org.tawhid.airwaves.book.openbook.presentation.book_detail.BookDetailAction
import org.tawhid.airwaves.book.openbook.presentation.book_detail.BookDetailScreenRoot
import org.tawhid.airwaves.book.openbook.presentation.book_detail.BookDetailViewModel
import org.tawhid.airwaves.book.presentation.BookHomeScreenRoot
import org.tawhid.airwaves.book.presentation.BookHomeViewModel
import org.tawhid.airwaves.core.player.presentation.PlayerViewModel
import org.tawhid.airwaves.presentations.home.HomeScreen
import org.tawhid.airwaves.presentations.podcasts.PodcastsScreen
import org.tawhid.airwaves.radio.presentation.SelectedRadioViewModel
import org.tawhid.airwaves.radio.presentation.radio_detail.RadioDetailAction
import org.tawhid.airwaves.radio.presentation.radio_detail.RadioDetailScreenRoot
import org.tawhid.airwaves.radio.presentation.radio_detail.RadioDetailViewModel
import org.tawhid.airwaves.radio.presentation.radio_home.RadioHomeScreenRoot
import org.tawhid.airwaves.radio.presentation.radio_home.RadioHomeViewModel

fun NavGraphBuilder.navGraph(
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
            val selectedAudioBookViewModel =
                it.sharedKoinViewModel<SelectedAudioBookViewModel>(rootNavController)
            LaunchedEffect(true) { selectedBookViewModel.onSelectBook(null) }
            BookHomeScreenRoot(
                viewModel = bookHomeViewModel,
                onBookClick = { book ->
                    selectedBookViewModel.onSelectBook(book)
                    rootNavController.navigate(
                        Route.BookDetail(book.id)
                    )
                },
                onAudioBookClick = { audioBook ->
                    selectedAudioBookViewModel.onSelectBook(audioBook)
                    rootNavController.navigate(
                        Route.AudioBookDetail(audioBook.id)
                    )
                },
                onSettingClick = {
                    rootNavController.navigate(
                        NavigationScreenRoute.Setting.route
                    )
                },
                innerPadding = innerPadding
            )
        }

        composable<Route.AudioBookDetail> { it ->
            val selectedAudioBookViewModel =
                it.sharedKoinViewModel<SelectedAudioBookViewModel>(rootNavController)
            val viewModel = koinViewModel<AudioBookDetailViewModel>()
            val selectedAudioBook by selectedAudioBookViewModel.selectedBook.collectAsStateWithLifecycle()
            LaunchedEffect(selectedAudioBook) {
                selectedAudioBook?.let {
                    viewModel.onAction(AudioBookDetailAction.OnSelectedBookChange(it))
                }
            }
            AudioBookDetailScreenRoot(
                viewModel = viewModel,
                onBackClick = {
                    rootNavController.navigateUp()
                }
            )
        }

        composable<Route.BookDetail>(
            enterTransition = {
                fadeIn(animationSpec = tween(100)) + slideInHorizontally { initialOffset ->
                    initialOffset
                }
            },
            exitTransition = {
                fadeOut(animationSpec = tween(100)) + slideOutHorizontally { initialOffset ->
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
            val radioHomeViewModel = koinViewModel<RadioHomeViewModel>()
            val selectedRadioViewModel = it.sharedKoinViewModel<SelectedRadioViewModel>(rootNavController)
            LaunchedEffect(true) {
                selectedRadioViewModel.onSelectedRadio(null)
            }
            RadioHomeScreenRoot(
                viewModel = radioHomeViewModel,
                onRadioClick = { radio ->
                    selectedRadioViewModel.onSelectedRadio(radio)
                    rootNavController.navigate(
                        Route.RadioDetail
                    )
                },
                onSettingClick = {
                    rootNavController.navigate(
                        NavigationScreenRoute.Setting.route
                    )
                },
                innerPadding = innerPadding
            )
        }

        composable<Route.RadioDetail> { it ->
            val selectedRadioViewModel = it.sharedKoinViewModel<SelectedRadioViewModel>(rootNavController)
            val viewModel = koinViewModel<RadioDetailViewModel>()
            val selectedRadio by selectedRadioViewModel.selectedRadio.collectAsState()

            LaunchedEffect(selectedRadio) {
                selectedRadio?.let {
                    viewModel.onAction(RadioDetailAction.OnSelectedRadioChange(it))
                }
            }
            RadioDetailScreenRoot(
                viewModel = viewModel,
                onBackClick = {
                    rootNavController.navigateUp()
                }
            )
        }

        composable<Route.PlayerToRadioDetail> {
            val viewModel = koinViewModel<RadioDetailViewModel>()
            val playerViewModel = koinViewModel<PlayerViewModel> ()
            val playerState by playerViewModel.state.collectAsState()
            val selectedRadio = playerState.selectedRadio

            LaunchedEffect(selectedRadio) {
                selectedRadio?.let {
                    viewModel.onAction(RadioDetailAction.OnSelectedRadioChange(it))
                }
            }
            RadioDetailScreenRoot(
                viewModel = viewModel,
                onBackClick = {
                    rootNavController.navigateUp()
                }
            )
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