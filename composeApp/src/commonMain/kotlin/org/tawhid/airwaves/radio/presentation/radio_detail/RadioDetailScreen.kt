package org.tawhid.airwaves.radio.presentation.radio_detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RadioDetailScreenRoot(
    viewModel: RadioDetailViewModel,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RadioDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is RadioDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RadioDetailScreen(
    state: RadioDetailState,
    onAction: (RadioDetailAction) -> Unit,
) {

    if (state.radio != null) {
        Text(
            text = state.radio.name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        Text(
            text = "No radio found",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}