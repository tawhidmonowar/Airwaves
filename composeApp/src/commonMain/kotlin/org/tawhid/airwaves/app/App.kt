package org.tawhid.airwaves.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.app.navigation.NavigationScreenRoot
import org.tawhid.airwaves.core.presentation.setting.SettingViewModel
import org.tawhid.airwaves.theme.AirwavesTheme
import org.tawhid.airwaves.utils.Theme

@Composable
fun App() {
    KoinContext {

        val settingViewModel = koinViewModel<SettingViewModel>()
        val state by settingViewModel.state.collectAsState()
        val currentTheme = state.currentTheme

        AirwavesTheme(currentTheme) {
            NavigationScreenRoot(settingViewModel)
        }
    }
}