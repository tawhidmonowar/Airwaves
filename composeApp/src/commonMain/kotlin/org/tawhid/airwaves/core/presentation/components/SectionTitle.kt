package org.tawhid.airwaves.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import org.tawhid.airwaves.core.theme.xSmallPadding

@Composable
fun SectionTitle(
    title: String,
    btnText: String? = null,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(xSmallPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        if (!btnText.isNullOrEmpty()) {
            Text(text = btnText, style = MaterialTheme.typography.titleMedium)
        }
    }
}