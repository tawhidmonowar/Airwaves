package org.tawhid.airwaves.presentations.radios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.tawhid.airwaves.data.models.radio.RadioData
import org.tawhid.airwaves.utils.Resource
import org.tawhid.airwaves.utils.radios

class RadioScreenViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<List<RadioData>>>(Resource.Idle)

    val uiState: StateFlow<Resource<List<RadioData>>> get() = _uiState

    init {
        getRadios()
    }

    private fun getRadios() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(Resource.Loading)
            try {
                val radios = radios
                _uiState.emit(Resource.Success(radios))

            } catch (e: Exception) {
                _uiState.emit(Resource.Error(e.message.toString()))
            }
        }
    }
}