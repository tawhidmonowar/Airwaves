package org.tawhid.airwaves.radio.presentation.radio_home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.tawhid.airwaves.core.theme.extraSmall
import org.tawhid.airwaves.core.theme.maxWidthIn
import org.tawhid.airwaves.radio.domain.Radio

@Composable
fun RadioList(
    radios: List<Radio>,
    onRadioClick: (Radio) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(extraSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = radios,
            key = { it.id }
        ) { radio ->
            RadioListItem(
                radio = radio,
                modifier = Modifier
                    .widthIn(max = maxWidthIn)
                    .fillMaxWidth()
                    .padding(horizontal = extraSmall),
                onClick = {
                    onRadioClick(radio)
                }
            )
        }
    }
}