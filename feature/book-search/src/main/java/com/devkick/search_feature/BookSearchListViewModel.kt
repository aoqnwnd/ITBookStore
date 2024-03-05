package com.devkick.search_feature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkick.image.book.GetNewBooksUseCase
import com.devkick.image.book.GetSearchBooksUseCase
import com.devkick.model.BookList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class BookSearchListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSearchBooksUseCase: GetSearchBooksUseCase,
    private val getNewBooksUseCase: GetNewBooksUseCase,
) : ContainerHost<BookSearchState, BookSearchSideEffect>, ViewModel() {
    override val container = container<BookSearchState, BookSearchSideEffect>(
        BookSearchState.Loading
    )

    init {
        fetchData()
    }

    private fun fetchData() {
        intent {
            viewModelScope.launch {
                val result = getNewBooksUseCase()
                    .map<BookList, BookSearchState>(BookSearchState::Success)
                    .onStart { emit(BookSearchState.Loading) }
                    .catch { emit(BookSearchState.Error(exception = it)) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000),
                        initialValue = BookSearchState.Loading
                    ).value

                reduce {
                    state
                    result
                }
                postSideEffect(sideEffect = BookSearchSideEffect.SendToToast("테스트"))
            }
        }
    }

    private var page = 0

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    private val newBooksList = MutableStateFlow<BookList?>(null)

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
        page = 0
    }

    fun onPagingSearchBooks() {
        page++
        savedStateHandle[SEARCH_QUERY] = searchQuery.value
    }

    private fun getNewBooks() {
        viewModelScope.launch {
            getNewBooksUseCase()
                .collect { newBooksList.value = it }
        }
    }
}

private const val SEARCH_QUERY = "searchQuery"
private const val SEARCH_QUERY_MIN_LENGTH = 2