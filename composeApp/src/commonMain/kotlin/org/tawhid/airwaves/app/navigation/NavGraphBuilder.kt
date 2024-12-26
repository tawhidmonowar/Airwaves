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
import org.tawhid.airwaves.core.player.data.mappers.toRadio
import org.tawhid.airwaves.core.player.presentation.PlayerViewModel
import org.tawhid.airwaves.core.setting.SettingScreenRoot
import org.tawhid.airwaves.core.setting.SettingViewModel
import org.tawhid.airwaves.core.utils.RadioViewMoreType
import org.tawhid.airwaves.presentations.home.HomeScreen
import org.tawhid.airwaves.presentations.podcasts.PodcastsScreen
import org.tawhid.airwaves.radio.presentation.SharedRadioViewModel
import org.tawhid.airwaves.radio.presentation.radio_detail.RadioDetailAction
import org.tawhid.airwaves.radio.presentation.radio_detail.RadioDetailScreenRoot
import org.tawhid.airwaves.radio.presentation.radio_detail.RadioDetailViewModel
import org.tawhid.airwaves.radio.presentation.radio_home.RadioHomeScreenRoot
import org.tawhid.airwaves.radio.presentation.radio_home.RadioHomeViewModel
import org.tawhid.airwaves.radio.presentation.view_more.RadioViewMoreAction
import org.tawhid.airwaves.radio.presentation.view_more.RadioViewMoreViewModel
import org.tawhid.airwaves.radio.presentation.view_more.ViewMoreScreenRoot

fun NavGraphBuilder.navGraphBuilder(
    rootNavController: NavController,
    settingViewModel: SettingViewModel,
    innerPadding: PaddingValues
) {
    composable<Route.Home> {
        HomeScreen()
    }

    composable<Route.Podcast> {
        PodcastsScreen()
    }

    composable<Route.Book> {
        val bookHomeViewModel = koinViewModel<BookHomeViewModel>()
        val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(rootNavController)
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
                    Route.Setting
                )
            },
            innerPadding = innerPadding
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
        val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(rootNavController)
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

    composable<Route.Setting> {
        SettingScreenRoot(
            viewModel = settingViewModel,
            innerPadding = innerPadding,
            onBackClick = {
                rootNavController.navigateUp()
            }
        )
    }

    navigation<Route.RadioGraph>(
        startDestination = Route.Radio
    ) {
        composable<Route.Radio> {
            val radioHomeViewModel = koinViewModel<RadioHomeViewModel>()
            val sharedRadioViewModel = it.sharedKoinViewModel<SharedRadioViewModel>(rootNavController)
            LaunchedEffect(true) {
                sharedRadioViewModel.updateSelectedRadio(null)
                sharedRadioViewModel.updateViewMoreType(null)
            }
            RadioHomeScreenRoot(
                viewModel = radioHomeViewModel,
                onRadioClick = { radio ->
                    sharedRadioViewModel.updateSelectedRadio(radio)
                    rootNavController.navigate(
                        Route.RadioDetail
                    )
                },
                onSettingClick = {
                    rootNavController.navigate(
                        Route.Setting
                    )
                },
                onViewMoreClick = { type ->
                    sharedRadioViewModel.updateViewMoreType(type)
                    rootNavController.navigate(
                        Route.RadioViewMore
                    )
                },
                innerPadding = innerPadding
            )
        }
        composable<Route.RadioViewMore> {
            val sharedRadioViewModel = it.sharedKoinViewModel<SharedRadioViewModel>(rootNavController)
            val viewModel = koinViewModel<RadioViewMoreViewModel>()
            val currentViewMoreType by sharedRadioViewModel.currentViewMoreType.collectAsState()

            currentViewMoreType?.let { type ->
                viewModel.onAction(RadioViewMoreAction.OnViewMoreTypeChange(type))
            }

            ViewMoreScreenRoot(
                viewModel = viewModel,
                onRadioClick = { radio ->
                    sharedRadioViewModel.updateSelectedRadio(radio)
                    rootNavController.navigate(Route.RadioDetail)
                },
                onBackClick = { rootNavController.navigateUp() },
                innerPadding = innerPadding
            )
        }
        composable<Route.RadioDetail> { it ->
            val sharedRadioViewModel =
                it.sharedKoinViewModel<SharedRadioViewModel>(rootNavController)
            val viewModel = koinViewModel<RadioDetailViewModel>()
            val selectedRadio by sharedRadioViewModel.currentSelectedRadio.collectAsState()
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
            val playerViewModel = koinViewModel<PlayerViewModel>()
            val playerState by playerViewModel.state.collectAsState()
            val selectedRadio = playerState.selectedPlayer?.toRadio()
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