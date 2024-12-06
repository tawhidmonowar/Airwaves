package org.tawhid.airwaves.radio.presentation.radio_home

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.audiobooks_not_found
import airwaves.composeapp.generated.resources.radios
import airwaves.composeapp.generated.resources.science_fiction
import airwaves.composeapp.generated.resources.search_result
import airwaves.composeapp.generated.resources.setting
import airwaves.composeapp.generated.resources.trending_radios
import airwaves.composeapp.generated.resources.view_all
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.book.audiobook.presentation.audiobook_home.AudioBookAction
import org.tawhid.airwaves.book.audiobook.presentation.audiobook_home.components.AudioBookVerticalGridItem
import org.tawhid.airwaves.core.presentation.components.SearchBar
import org.tawhid.airwaves.core.presentation.components.SectionTitle
import org.tawhid.airwaves.core.theme.staggeredGridCellLarge
import org.tawhid.airwaves.core.theme.staggeredGridCellSmall
import org.tawhid.airwaves.core.theme.xxSmallPadding
import org.tawhid.airwaves.core.theme.xxxSmallPadding
import org.tawhid.airwaves.radio.domain.Radio
import org.tawhid.airwaves.radio.presentation.radio_home.components.RadioListItem
import org.tawhid.airwaves.radio.presentation.radio_home.components.RadioVerticalGridItem
import org.tawhid.airwaves.utils.SEARCH_TRIGGER_CHAR

@Composable
fun RadioHomeScreenRoot(
    viewModel: RadioHomeViewModel = koinViewModel(),
    onRadioClick: (Radio) -> Unit,
    innerPadding: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    RadioHomeScreen(
        state = state.value,
        onAction = { action ->
            when (action) {
                is RadioHomeAction.OnRadioClick -> onRadioClick(action.radio)
                else -> Unit
            }
            viewModel.onAction(action)
        },
        innerPadding = innerPadding
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun RadioHomeScreen(
    state: RadioHomeState,
    onAction: (RadioHomeAction) -> Unit,
    innerPadding: PaddingValues
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val windowSizeClass = calculateWindowSizeClass()
    val isMediumExpandedWWSC by remember(windowSizeClass) {
        derivedStateOf {
            windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
        }
    }

    val scrollBehavior = if (isMediumExpandedWWSC) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    } else {
        TopAppBarDefaults.enterAlwaysScrollBehavior()
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
                        Text(text = stringResource(Res.string.radios))
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
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(
                        if (isMediumExpandedWWSC) staggeredGridCellLarge else staggeredGridCellSmall
                    ),
                    verticalItemSpacing = xxSmallPadding,
                    horizontalArrangement = Arrangement.spacedBy(xxSmallPadding),
                    modifier = Modifier.padding(horizontal = xxxSmallPadding)
                ) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        SearchBar(
                            searchQuery = state.searchQuery,
                            onSearchQueryChange = { onAction(RadioHomeAction.OnSearchQueryChange(it)) },
                            onImeSearch = { keyboardController?.hide() },
                            modifier = Modifier.padding(xxSmallPadding).fillMaxWidth()
                        )
                    }
                    if (state.searchQuery.length >= SEARCH_TRIGGER_CHAR) {
                        if (state.isSearchLoading) {
                            item(span = StaggeredGridItemSpan.FullLine) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        } else if (state.searchErrorMsg != null) {
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
                            state.searchResult.forEach { radio ->
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    RadioListItem(
                                        radio = radio,
                                        onClick = {

                                        }
                                    )
                                }
                            }
                        }
                    }

                    if (state.isTrendingLoading) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    } else if (state.trendingRadios.isNotEmpty()) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            SectionTitle(
                                title = stringResource(Res.string.trending_radios),
                                btnText = stringResource(Res.string.view_all)
                            )
                        }
                        items(items = state.trendingRadios.take(15), key = { it.id }) { radio ->
                            RadioVerticalGridItem(
                                radio = radio,
                                onClick = {
                                    onAction(RadioHomeAction.OnRadioClick(radio))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}