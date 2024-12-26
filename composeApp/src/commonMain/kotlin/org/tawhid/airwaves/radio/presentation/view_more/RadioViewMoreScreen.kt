package org.tawhid.airwaves.radio.presentation.view_more

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.go_back
import airwaves.composeapp.generated.resources.radios
import airwaves.composeapp.generated.resources.retry
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.core.theme.extraSmall
import org.tawhid.airwaves.core.theme.large
import org.tawhid.airwaves.core.theme.maxWidthIn
import org.tawhid.airwaves.core.theme.small
import org.tawhid.airwaves.core.theme.xxSmallPadding
import org.tawhid.airwaves.radio.domain.Radio
import org.tawhid.airwaves.radio.presentation.radio_home.components.RadioListItem

@Composable
fun ViewMoreScreenRoot(
    viewModel: RadioViewMoreViewModel = koinViewModel(),
    onRadioClick: (Radio) -> Unit,
    onBackClick: () -> Unit,
    innerPadding: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    ViewMoreScreen(
        state = state.value,
        onAction = { action ->
            when (action) {
                is RadioViewMoreAction.OnRadioClick -> onRadioClick(action.radio)
                is RadioViewMoreAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        innerPadding = innerPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ViewMoreScreen(
    state: RadioViewMoreState,
    onAction: (RadioViewMoreAction) -> Unit,
    innerPadding: PaddingValues
) {
    val searchResultListState = rememberLazyListState()
    val uniqueRadios = state.radioList.distinctBy { it.id to it.url }
    val radiosSize by mutableStateOf(uniqueRadios.size)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        topBar = {
            TopAppBar(
                title = {
                    state.currentViewMoreType?.let {
                        Text(text = it + " " + stringResource(Res.string.radios))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onAction(RadioViewMoreAction.OnBackClick)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(Res.string.go_back),
                        )
                    }
                },
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = searchResultListState,
                modifier = Modifier.padding(contentPadding),
                verticalArrangement = Arrangement.spacedBy(xxSmallPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = radiosSize,
                    key = { index -> uniqueRadios[index].id }
                ) { index ->
                    val radio = uniqueRadios[index]
                    if (index == radiosSize - 1 && !state.isEndReached && !state.isLoading && state.errorMsg == null) {
                        onAction(RadioViewMoreAction.OnLoadMoreRadios)
                    }
                    RadioListItem(
                        radio = radio,
                        modifier = Modifier
                            .widthIn(max = maxWidthIn)
                            .fillMaxWidth()
                            .padding(horizontal = xxSmallPadding),
                        onClick = {
                            onAction(RadioViewMoreAction.OnRadioClick(radio))
                        }
                    )
                }
                item {
                    if (state.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(large),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(extraSmall)
                            )
                        }
                    } else if (state.errorMsg != null) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(small),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = state.errorMsg.asString())
                            Button(
                                onClick = {
                                    onAction(RadioViewMoreAction.OnLoadMoreRadios)
                                },
                                content = { Text(text = stringResource(Res.string.retry)) }
                            )
                        }
                    }
                }
            }
        }
    }
}