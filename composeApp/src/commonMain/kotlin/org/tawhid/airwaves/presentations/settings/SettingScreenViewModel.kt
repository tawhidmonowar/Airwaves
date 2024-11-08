package org.tawhid.airwaves.presentations.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.tawhid.airwaves.utils.AppPreferences

class SettingScreenViewModel(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _currentTheme: MutableStateFlow<String?> = MutableStateFlow(null)
    val currentTheme = _currentTheme.asStateFlow()

    init {
        getCurrentTheme()
    }

    private fun getCurrentTheme() = runBlocking {
        _currentTheme.value = appPreferences.getCurrentTheme()

    }

    fun changeThemeMode(value: String) = viewModelScope.launch(Dispatchers.IO) {
        appPreferences.changeThemeMode(value)
        _currentTheme.value = value
    }
}