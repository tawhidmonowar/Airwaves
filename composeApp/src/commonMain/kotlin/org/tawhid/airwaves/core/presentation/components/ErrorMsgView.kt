package org.tawhid.airwaves.core.presentation.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.retry
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.core.theme.small
import org.tawhid.airwaves.core.utils.UiText

@Composable
fun ErrorMsgView(
    errorMsg: UiText,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(small),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = errorMsg.asString())
            Button(
                onClick = {
                    onRetryClick()
                },
                content = { Text(text = stringResource(Res.string.retry)) }
            )
        }
    }
}