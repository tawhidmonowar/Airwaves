package org.tawhid.airwaves.radio.presentation.radio_home

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.info
import airwaves.composeapp.generated.resources.radios
import airwaves.composeapp.generated.resources.recently_updated
import airwaves.composeapp.generated.resources.search
import airwaves.composeapp.generated.resources.setting
import airwaves.composeapp.generated.resources.verified
import airwaves.composeapp.generated.resources.view_more
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.core.presentation.components.EmbeddedSearchBar
import org.tawhid.airwaves.core.presentation.components.ErrorMsgView
import org.tawhid.airwaves.core.presentation.components.FeedTitle
import org.tawhid.airwaves.core.presentation.components.ShimmerEffect
import org.tawhid.airwaves.core.presentation.feed.Feed
import org.tawhid.airwaves.core.presentation.feed.row
import org.tawhid.airwaves.core.presentation.feed.single
import org.tawhid.airwaves.core.presentation.feed.title
import org.tawhid.airwaves.core.theme.compactFeedWidth
import org.tawhid.airwaves.core.theme.compactScreenPadding
import org.tawhid.airwaves.core.theme.expandedFeedWidth
import org.tawhid.airwaves.core.theme.expandedScreenPadding
import org.tawhid.airwaves.core.theme.extraSmall
import org.tawhid.airwaves.core.theme.mediumFeedWidth
import org.tawhid.airwaves.core.theme.mediumScreenPadding
import org.tawhid.airwaves.core.theme.small
import org.tawhid.airwaves.core.theme.thin
import org.tawhid.airwaves.core.theme.zero
import org.tawhid.airwaves.core.utils.RadioViewMoreType
import org.tawhid.airwaves.core.utils.WindowSizes
import org.tawhid.airwaves.radio.domain.Radio
import org.tawhid.airwaves.radio.presentation.radio_home.components.RadioGridItem
import org.tawhid.airwaves.radio.presentation.radio_home.components.RadioHorizontalGridItem
import org.tawhid.airwaves.radio.presentation.radio_home.components.RadioInfoDialog
import org.tawhid.airwaves.radio.presentation.radio_home.components.RadioListItem
import org.tawhid.airwaves.radio.presentation.radio_home.components.RadioSearchResult

@Composable
fun RadioHomeScreenRoot(
    viewModel: RadioHomeViewModel = koinViewModel(),
    onRadioClick: (Radio) -> Unit,
    onSettingClick: () -> Unit,
    onViewMoreClick: (String) -> Unit,
    innerPadding: PaddingValues,
    windowSize: WindowSizes
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val startPadding by animateDpAsState(
        targetValue = innerPadding.calculateStartPadding(
            LayoutDirection.Ltr
        )
    )
    val topPadding by animateDpAsState(targetValue = innerPadding.calculateTopPadding())
    val endPadding by animateDpAsState(
        targetValue = innerPadding.calculateEndPadding(
            LayoutDirection.Ltr
        )
    )
    val bottomPadding by animateDpAsState(targetValue = innerPadding.calculateBottomPadding())
    val animatedPadding = PaddingValues(
        start = startPadding,
        top = topPadding,
        end = endPadding,
        bottom = bottomPadding
    )
    Box(modifier = Modifier.fillMaxSize().padding(animatedPadding)) {
        RadioHomeScreen(
            windowSize = windowSize,
            state = state.value,
            onAction = { action ->
                when (action) {
                    is RadioHomeAction.OnRadioClick -> onRadioClick(action.radio)
                    is RadioHomeAction.OnSettingClick -> onSettingClick()
                    is RadioHomeAction.OnViewMoreClick -> onViewMoreClick(action.viewMoreType)
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RadioHomeScreen(
    state: RadioHomeState,
    windowSize: WindowSizes,
    onAction: (RadioHomeAction) -> Unit,
) {
    if (state.showRadioInfoDialog) {
        RadioInfoDialog(
            onDismissRequest = {
                onAction(RadioHomeAction.HideRadioInfoDialog)
            }
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val animatedStartPadding by animateDpAsState(
        targetValue = if (windowSize.isExpandedScreen) expandedScreenPadding
        else if (windowSize.isMediumScreen) mediumScreenPadding
        else compactScreenPadding,
        animationSpec = tween(durationMillis = 300)
    )

    val animatedEndPadding by animateDpAsState(
        targetValue = if (windowSize.isExpandedScreen) expandedScreenPadding
        else if (windowSize.isMediumScreen) mediumScreenPadding
        else compactScreenPadding,
        animationSpec = tween(durationMillis = 300)
    )

    val gridState = rememberLazyGridState()
    val radiosSize by mutableStateOf(state.radios.size)

    Surface {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(Res.string.radios))
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onAction(RadioHomeAction.ShowRadioInfoDialog)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = stringResource(Res.string.info),
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            onAction(RadioHomeAction.ActivateSearchMode)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = stringResource(Res.string.search),
                            )
                        }
                        if (windowSize.isCompactScreen) {
                            IconButton(onClick = {
                                onAction(RadioHomeAction.OnSettingClick)
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Settings,
                                    contentDescription = stringResource(Res.string.setting),
                                )
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
                if (state.isSearchActive) {
                    EmbeddedSearchBar(
                        query = state.searchQuery,
                        onQueryChange = { query ->
                            onAction(RadioHomeAction.OnSearchQueryChange(query))
                        },
                        onSearch = {
                            keyboardController?.hide()
                        },
                        content = {
                            RadioSearchResult(
                                state = state,
                                onRadioClick = { radio ->
                                    keyboardController?.hide()
                                    onAction(RadioHomeAction.OnRadioClick(radio))
                                }
                            )
                        },
                        onBack = {
                            onAction(RadioHomeAction.DeactivateSearchMode)
                        },
                        isActive = true
                    )
                }
            }
        ) { innerPadding ->

            val contentPadding = PaddingValues(horizontal = thin, vertical = thin)
            val verticalArrangement = if (!windowSize.isCompactScreen) Arrangement.spacedBy(small) else Arrangement.spacedBy(zero)
            val horizontalArrangement = if (!windowSize.isCompactScreen) Arrangement.spacedBy(small) else Arrangement.spacedBy(zero)

            val columns = when {
                windowSize.isExpandedScreen -> GridCells.Adaptive(expandedFeedWidth)
                windowSize.isMediumScreen -> GridCells.Adaptive(mediumFeedWidth)
                else -> GridCells.Adaptive(compactFeedWidth)
            }

            Feed(
                modifier = Modifier
                    .padding(
                        start = animatedStartPadding,
                        top = innerPadding.calculateTopPadding(),
                        end = animatedEndPadding,
                        bottom = innerPadding.calculateBottomPadding()
                    ).fillMaxSize(),
                columns = columns,
                state = gridState,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement
            ) {

                title(contentType = "verified-title") {
                    FeedTitle(
                        title = stringResource(Res.string.verified),
                        btnText = stringResource(Res.string.view_more),
                        onClick = { onAction(RadioHomeAction.OnViewMoreClick(viewMoreType = RadioViewMoreType.VERIFIED)) }
                    )
                }

                row(contentType = "verified-shimmer-effect") {
                    if (state.isVerifiedLoading) {
                        ShimmerEffect.RadioHorizontalGridItemShimmerEffect()
                    } else if (state.verifiedErrorMsg != null) {
                        ErrorMsgView(
                            errorMsg = state.verifiedErrorMsg,
                            onRetryClick = {
                                onAction(RadioHomeAction.LoadVerifiedRadios)
                            }
                        )
                    } else {
                        RadioHorizontalGridItem(
                            radios = state.verifiedRadios,
                            onAction = onAction
                        )
                    }
                }

                title(contentType = "latest-title") { FeedTitle(title = stringResource(Res.string.recently_updated)) }

                items(
                    count = radiosSize,
                    key = { index -> state.radios[index].id }
                ) { index ->
                    val radio = state.radios[index]

                    if (index == radiosSize - 1 && !state.endReached) {
                        onAction(RadioHomeAction.LoadRadios)
                    }
                    if (windowSize.isCompactScreen) {
                        RadioGridItem(
                            radio = radio,
                            onClick = {
                                onAction(RadioHomeAction.OnRadioClick(radio))
                            }
                        )
                    } else {
                        RadioListItem(
                            radio = radio,
                            onClick = {
                                onAction(RadioHomeAction.OnRadioClick(radio))
                            }
                        )
                    }
                }

                if (state.errorMsg != null) {
                    single {
                        ErrorMsgView(
                            errorMsg = state.errorMsg,
                            onRetryClick = {
                                onAction(RadioHomeAction.LoadRadios)
                            }
                        )
                    }
                } else if (state.isLoading) {
                    if (!state.isVerifiedLoading) {
                        single {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(extraSmall)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}