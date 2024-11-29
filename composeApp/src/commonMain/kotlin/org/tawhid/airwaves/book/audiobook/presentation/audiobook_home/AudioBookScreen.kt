package org.tawhid.airwaves.book.audiobook.presentation.audiobook_home

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.action_adventure
import airwaves.composeapp.generated.resources.audiobooks_not_found
import airwaves.composeapp.generated.resources.educational
import airwaves.composeapp.generated.resources.science_fiction
import airwaves.composeapp.generated.resources.search_result
import airwaves.composeapp.generated.resources.view_all
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.book.audiobook.domain.AudioBook
import org.tawhid.airwaves.book.audiobook.presentation.audiobook_home.components.AudioBookListItem
import org.tawhid.airwaves.book.audiobook.presentation.audiobook_home.components.AudioBookVerticalGridItem
import org.tawhid.airwaves.book.openbook.presentation.book_list.components.BookSearchBar
import org.tawhid.airwaves.book.presentation.components.SectionTitle
import org.tawhid.airwaves.core.theme.maxWidthIn
import org.tawhid.airwaves.core.theme.staggeredGridCellLarge
import org.tawhid.airwaves.core.theme.staggeredGridCellSmall
import org.tawhid.airwaves.core.theme.xxSmallPadding
import org.tawhid.airwaves.core.theme.xxxSmallPadding
import org.tawhid.airwaves.utils.SEARCH_TRIGGER_CHAR

@Composable
fun AudioBookScreenRoot(
    viewModel: AudioBookViewModel = koinViewModel(),
    onBookClick: (AudioBook) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    AudioBookScreen(
        state = state.value,
        onAction = { action ->
            when (action) {
                is AudioBookAction.OnAudioBookClick -> onBookClick(action.audioBook)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun AudioBookScreen(
    state: AudioBookState,
    onAction: (AudioBookAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val windowSizeClass = calculateWindowSizeClass()
    val isMediumExpandedWWSC by remember(windowSizeClass) {
        derivedStateOf {
            windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
        }
    }
    Column(
        modifier = Modifier
            .widthIn(max = maxWidthIn)
            .fillMaxSize()
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(
                if (isMediumExpandedWWSC) staggeredGridCellLarge else staggeredGridCellSmall
            ),
            verticalItemSpacing = xxSmallPadding,
            horizontalArrangement = Arrangement.spacedBy(xxSmallPadding),
            modifier = Modifier.padding(horizontal = xxxSmallPadding)
        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                BookSearchBar(
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = { onAction(AudioBookAction.OnSearchQueryChange(it)) },
                    onImeSearch = { keyboardController?.hide() },
                    modifier = Modifier.padding(xxSmallPadding).fillMaxWidth()
                )
            }

            if (state.searchQuery.length >= SEARCH_TRIGGER_CHAR) {
                if (state.isLoadingSearch) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                } else if (state.errorMsgSearch != null) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(Res.string.audiobooks_not_found),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                } else if (state.searchResult.isNotEmpty()) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        SectionTitle(title = stringResource(Res.string.search_result))
                    }
                    state.searchResult.forEach { audioBook ->
                        item(span = StaggeredGridItemSpan.FullLine) {
                            AudioBookListItem(
                                audioBook = audioBook,
                                onClick = { onAction(AudioBookAction.OnAudioBookClick(audioBook)) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            if (state.isLoadingScienceFiction) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else if (state.scienceFictionBooks.isNotEmpty()) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    SectionTitle(
                        title = stringResource(Res.string.science_fiction),
                        btnText = stringResource(Res.string.view_all)
                    )
                }
                items(items = state.scienceFictionBooks.take(15), key = { it.id }) { audioBook ->
                    AudioBookVerticalGridItem(
                        audioBook = audioBook,
                        onClick = {
                            onAction(AudioBookAction.OnAudioBookClick(audioBook))
                        }
                    )
                }
            }

            if (state.actionAdventureBooks.isNotEmpty()) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    SectionTitle(
                        title = stringResource(Res.string.action_adventure),
                        btnText = stringResource(Res.string.view_all)
                    )
                }
                items(items = state.actionAdventureBooks.take(15), key = { it.id }) { audioBook ->
                    AudioBookVerticalGridItem(
                        audioBook = audioBook,
                        onClick = {
                            onAction(AudioBookAction.OnAudioBookClick(audioBook))
                        }
                    )
                }
            }

            if (state.educationalBooks.isNotEmpty()) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    SectionTitle(
                        title = stringResource(Res.string.educational),
                        btnText = stringResource(Res.string.view_all)
                    )
                }
                items(items = state.educationalBooks.take(15), key = { it.id }) { audioBook ->
                    AudioBookVerticalGridItem(
                        audioBook = audioBook,
                        onClick = {
                            onAction(AudioBookAction.OnAudioBookClick(audioBook))
                        }
                    )
                }
            }
        }
    }
}