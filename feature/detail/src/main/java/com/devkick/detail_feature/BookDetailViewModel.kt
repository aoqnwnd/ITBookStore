package com.devkick.detail_feature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkick.image.book.GetBookDetailUseCase
import com.devkick.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getBookDetailUseCase: GetBookDetailUseCase
) : ViewModel() {
    private val bookId = savedStateHandle.get<String>("isbn13")

    var detailUiState: StateFlow<BookDetailUiState> =
        getBookDetailUseCase(bookId ?: "")
            .map<Book, BookDetailUiState>(BookDetailUiState::Success)
            .onStart { emit(BookDetailUiState.Loading) }
            .catch { emit(BookDetailUiState.Fail(it.message.toString())) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = BookDetailUiState.Loading
            )
}