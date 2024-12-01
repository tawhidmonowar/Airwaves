package org.tawhid.airwaves.core.setting.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.apply
import airwaves.composeapp.generated.resources.cancel
import airwaves.composeapp.generated.resources.choose_a_theme
import airwaves.composeapp.generated.resources.clear_data_title
import airwaves.composeapp.generated.resources.clear_data_warning
import airwaves.composeapp.generated.resources.delete
import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.core.theme.mediumPadding
import org.tawhid.airwaves.core.theme.xLargePadding
import org.tawhid.airwaves.core.theme.xSmallPadding
import org.tawhid.airwaves.utils.Theme

@Composable
fun ClearDataDialog(
    onDeleteHistory: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(Res.string.clear_data_title)) },
        text = { Text(stringResource(Res.string.clear_data_warning)) },

        confirmButton = {
            TextButton(onClick = { onDeleteHistory() }
            ) { Text(stringResource(Res.string.delete)) }
        },

        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) { Text(stringResource(Res.string.cancel)) }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionDialog(
    onThemeChange: (Theme) -> Unit,
    onDismissRequest: () -> Unit,
    currentTheme: String
) {

    var currentSelectedTheme by remember { mutableStateOf(Theme.valueOf(currentTheme)) }

    BasicAlertDialog(onDismissRequest = onDismissRequest, content = {
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(mediumPadding)) {
                Text(
                    text = stringResource(Res.string.choose_a_theme),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(xSmallPadding)
                )
                Theme.entries.forEach { theme ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable { currentSelectedTheme = theme },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentSelectedTheme == theme,
                            onClick = { currentSelectedTheme = theme },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text(text = stringResource(theme.title))
                    }
                }

                Spacer(modifier = Modifier.height(xLargePadding))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(Res.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(mediumPadding))
                    TextButton(onClick = { onThemeChange(currentSelectedTheme) }) {
                        Text(stringResource(Res.string.apply))
                    }
                }
            }
        }
    })
}

