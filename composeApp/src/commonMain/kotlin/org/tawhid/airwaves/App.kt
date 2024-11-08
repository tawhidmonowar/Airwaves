package org.tawhid.airwaves

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import org.tawhid.airwaves.navigation.graphs.RootNavGraph
import org.tawhid.airwaves.theme.AirwavesTheme

import org.koin.compose.KoinContext
import org.tawhid.airwaves.presentations.settings.SettingScreenViewModel
import org.tawhid.airwaves.utils.AppPreferences
import androidx.compose.runtime.getValue


@Composable
fun App(prefs: DataStore<Preferences>) {

    val appPreferences = remember {
        AppPreferences(prefs)
    }

    val settingScreenViewModel = viewModel {
        SettingScreenViewModel(appPreferences)
    }

    val currentTheme by settingScreenViewModel.currentTheme.collectAsState()

    KoinContext {
        AirwavesTheme(currentTheme) {
            RootNavGraph(settingScreenViewModel)
        }
    }
}