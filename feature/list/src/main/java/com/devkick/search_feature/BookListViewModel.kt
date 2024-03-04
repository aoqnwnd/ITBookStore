package com.devkick.search_feature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkick.image.book.GetNewBooksUseCase
import com.devkick.image.book.GetSearchBooksUseCase
import com.devkick.model.BookList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSearchBooksUseCase: GetSearchBooksUseCase,
    private val getNewBooksUseCase: GetNewBooksUseCase,
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    val bookListUiState: StateFlow<BookListUiState> =
        searchQuery
            .debounce(1_000)
            .flatMapLatest { query ->
                if (query.length < SEARCH_QUERY_MIN_LENGTH) {
                    getNewBooksUseCase()
                        .map<BookList, BookListUiState>(BookListUiState::Success)
                        .catch { emit(BookListUiState.Fail(message = it.message.toString())) }
                        .onStart { emit(BookListUiState.Loading) }
                } else {
                    getSearchBooksUseCase(query, 0)
                        .map<BookList, BookListUiState>(BookListUiState::Success)
                        .catch { emit(BookListUiState.Fail(message = it.message.toString())) }
                        .onStart { emit(BookListUiState.Loading) }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = BookListUiState.Loading
            )

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }
}

private const val SEARCH_QUERY = "searchQuery"
private const val SEARCH_QUERY_MIN_LENGTH = 2