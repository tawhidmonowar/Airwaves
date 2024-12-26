package org.tawhid.airwaves.radio.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tawhid.airwaves.radio.domain.Radio

class SharedRadioViewModel : ViewModel() {
    private val _currentSelectedRadio = MutableStateFlow<Radio?>(null)
    private val _currentViewMoreType = MutableStateFlow<String?>(null)

    val currentSelectedRadio = _currentSelectedRadio.asStateFlow()
    val currentViewMoreType = _currentViewMoreType.asStateFlow()

    fun updateSelectedRadio(selectedRadio: Radio?) {
        _currentSelectedRadio.value = selectedRadio
    }

    fun updateViewMoreType(viewMoreType: String?) {
        _currentViewMoreType.value = viewMoreType
    }
}