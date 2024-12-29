package org.tawhid.airwaves.core.presentation.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.right_arrow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.core.theme.small

@Composable
fun FeedTitle(
    title: String,
    btnText: String? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = titleStyle
        )
        btnText?.let { text ->
            TextButton(onClick = { onClick?.invoke() }) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = stringResource(Res.string.right_arrow),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}