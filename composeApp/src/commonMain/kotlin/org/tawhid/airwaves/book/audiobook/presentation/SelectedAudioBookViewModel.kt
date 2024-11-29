package org.tawhid.airwaves.book.audiobook.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tawhid.airwaves.book.audiobook.domain.AudioBook

class SelectedAudioBookViewModel : ViewModel() {
    private val _selectedBook = MutableStateFlow<AudioBook?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    fun onSelectBook(audioBook: AudioBook?) {
        _selectedBook.value = audioBook
    }
}