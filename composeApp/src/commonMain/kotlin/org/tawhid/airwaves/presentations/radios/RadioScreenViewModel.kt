package org.tawhid.airwaves.presentations.radios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.tawhid.airwaves.data.models.radio.RadioData
import org.tawhid.airwaves.data.models.radio.RaidoResponseError
import org.tawhid.airwaves.data.repository.radio.RadioRepository
import org.tawhid.airwaves.utils.Resource

class RadioScreenViewModel(
    private val radioRepository: RadioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<RadioData>>>(Resource.Loading)
    val uiState: StateFlow<Resource<List<RadioData>>> get() = _uiState

    init {
        getRadios()
    }

    private fun getRadios() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(Resource.Loading)
            try {
                val httpResponse = radioRepository.getRadios()
                if (httpResponse.status.value in 200..299) {
                    val body = httpResponse.body<List<RadioData>>()
                    _uiState.emit(Resource.Success(body))
                } else {
                    val body = httpResponse.body<RaidoResponseError>()
                    _uiState.emit(Resource.Error(body.message.toString()))
                }

            } catch (e: Exception) {
                _uiState.emit(Resource.Error(e.message.toString()))
            }
        }
    }
}