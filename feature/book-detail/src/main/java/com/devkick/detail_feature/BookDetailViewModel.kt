package com.devkick.detail_feature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkick.common.ComposeViewModel
import com.devkick.image.book.GetBookDetailUseCase
import com.devkick.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getBookDetailUseCase: GetBookDetailUseCase,
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<BookDetailEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val bookIsbn13 = savedStateHandle.get<String>("isbn13")

    var bookUiState: StateFlow<BookDetailUiState> =
        getBookDetailUseCase(bookIsbn13 ?: "")
            .map<Book, BookDetailUiState>(BookDetailUiState::Success)
            .onStart { emit(BookDetailUiState.Loading) }
            .catch { emit(BookDetailUiState.Fail(it.message.toString())) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = BookDetailUiState.Loading
            )

    fun event(event: BookDetailEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}