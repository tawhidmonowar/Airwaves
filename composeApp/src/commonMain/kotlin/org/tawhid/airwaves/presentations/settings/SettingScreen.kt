package org.tawhid.airwaves.presentations.settings

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.clear_data
import airwaves.composeapp.generated.resources.go_back
import airwaves.composeapp.generated.resources.setting
import airwaves.composeapp.generated.resources.theme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.presentations.settings.components.ClearDataDialog
import org.tawhid.airwaves.presentations.settings.components.SettingItem
import org.tawhid.airwaves.presentations.settings.components.ThemeSelectionDialog
import org.tawhid.airwaves.utils.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    rootNavController: NavHostController
) {
    var showClearDataDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }

    when {
        showThemeDialog -> {
            ThemeSelectionDialog(
                currentTheme = Theme.LIGHT_MODE.name,
                onThemeChange = {
                    showThemeDialog = false
                },
                onDismissRequest = {
                    showThemeDialog = false
                }
            )
        }
        showClearDataDialog -> {
            ClearDataDialog(
                onDismissRequest = {
                    showClearDataDialog = false
                },
                onDeleteHistory = {
                    showClearDataDialog = false
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.setting))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        rootNavController.navigateUp()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(Res.string.go_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            item {
                SettingItem(
                    onClick = {
                        showThemeDialog = true
                    },
                    painter = Icons.Filled.Settings,
                    itemName = stringResource(Res.string.theme)
                )
            }

            item {
                SettingItem(
                    onClick = {
                        showClearDataDialog = true
                    },
                    painter = Icons.Filled.Delete,
                    itemName = stringResource(Res.string.clear_data)
                )
            }
        }
    }
}
