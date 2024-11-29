package org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.tawhid.airwaves.book.openbook.presentation.book_detail.components.BlurredImageBackground

@Composable
fun AudioBookDetailScreenRoot(
    viewModel: AudioBookDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AudioBookDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AudioBookDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun AudioBookDetailScreen(
    state: AudioBookDetailState,
    onAction: (AudioBookDetailAction) -> Unit
) {
    BlurredImageBackground(
        imageUrl = state.audioBook?.imgUrl,
        isSaved = state.isSaved,
        onSaveClick = {

        },
        onBackClick = {
            onAction(AudioBookDetailAction.OnBackClick)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.audioBook != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.audioBook.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = state.audioBook.authors.joinToString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Synopsis",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 8.dp
                        )
                )
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = if (state.audioBook.description.isNullOrBlank()) {
                            "Description Unavailable"
                        } else {
                            state.audioBook.description
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        color = if (state.audioBook.description.isNullOrBlank()) {
                            Color.Black.copy(alpha = 0.4f)
                        } else Color.Black,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}