package org.tawhid.airwaves.radio.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tawhid.airwaves.radio.domain.Radio

class SelectedRadioViewModel : ViewModel() {

    private val _selectedRadio = MutableStateFlow<Radio?>(null)
    val selectedRadio = _selectedRadio.asStateFlow()

    fun onSelectedRadio(radio: Radio?) {
        _selectedRadio.value = radio
    }
}