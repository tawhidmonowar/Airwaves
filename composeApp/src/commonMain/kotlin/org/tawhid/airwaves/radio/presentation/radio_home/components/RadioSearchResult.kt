package org.tawhid.airwaves.radio.presentation.radio_home.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.no_search_result
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.radio.domain.Radio
import org.tawhid.airwaves.radio.presentation.radio_home.RadioHomeState

@Composable
fun RadioSearchResult(
    state: RadioHomeState,
    onRadioClick: (Radio) -> Unit
) {
    val searchResultListState = rememberLazyListState()
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (state.isSearchLoading) {
                    CircularProgressIndicator()
                } else {
                    when {
                        state.searchErrorMsg != null -> {
                            Text(
                                text = state.searchErrorMsg.asString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        state.searchResult.isEmpty() -> {
                            Text(
                                text = stringResource(Res.string.no_search_result),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        else -> {
                            RadioList(
                                radios = state.searchResult,
                                onRadioClick = { radio ->
                                    onRadioClick(radio)
                                },
                                modifier = Modifier.fillMaxSize(),
                                scrollState = searchResultListState
                            )
                        }
                    }
                }
            }
        }
    }
}