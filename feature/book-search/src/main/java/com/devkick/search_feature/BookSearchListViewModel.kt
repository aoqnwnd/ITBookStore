package com.devkick.search_feature

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.devkick.base.ListViewType
import com.devkick.common.ComposeViewModel
import com.devkick.image.book.GetNewBooksUseCase
import com.devkick.image.book.GetSearchBooksUseCase
import com.devkick.model.BookList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class BookSearchListViewModel @Inject constructor(
    private val getSearchBooksUseCase: GetSearchBooksUseCase,
    private val getNewBooksUseCase: GetNewBooksUseCase,
) : ComposeViewModel<BookSearchListUiState, BookSearchEvent>() {
    private val queryText = mutableStateOf("")
    private var bookList = mutableStateListOf<BookList.Book>()
    private var newBookList = mutableStateListOf<BookList.Book>()
    private val errorMessage = mutableStateOf<String?>(null)
    private val listType = mutableStateOf(ListViewType.List)
    private val isLoading = mutableStateOf(false)
    private val isEmpty = mutableStateOf(false)
    private var isFinish = false

    private var page = 1
    private val channel = Channel<String>()

    init {
        viewModelScope.launch {
            getNewBooks()

            channel
                .consumeAsFlow()
                .debounce(500)
                .collect {
                    searchBooks(isReset = true)
                }
        }
    }

    override fun uiState(): BookSearchListUiState {
        return BookSearchListUiState(
            queryText = queryText.value,
            bookList = bookList,
            newBookList = newBookList,
            listViewType = listType.value,
            isLoading = isLoading.value,
            errorMessage = errorMessage.value,
            isEmpty = isEmpty.value
        )
    }

    override suspend fun onSuspendEvent(event: BookSearchEvent) {
        if (event is BookSearchEvent.RefreshList) refreshList()
    }

    override fun onEvent(event: BookSearchEvent) {
        when (event) {
            BookSearchEvent.ClickListTypeChange -> changeListType()
            BookSearchEvent.LoadNextPage -> loadNextPage()
            is BookSearchEvent.QueryChange -> onSearchQueryChanged(event.query)
            else -> {}
        }
    }

    private fun changeListType() {
        listType.value = when (listType.value) {
            ListViewType.List -> ListViewType.Grid
            ListViewType.Grid -> ListViewType.List
        }
    }

    private fun onSearchQueryChanged(query: String) {
        queryText.value = query
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                channel.send(query)
                bookList.clear()
            } else {
                isEmpty.value = false
            }
        }
    }

    private suspend fun getNewBooks() {
        getNewBooksUseCase()
            .onStart { isLoading.value = true }
            .catch { throwable ->
                errorMessage.value = throwable.message.toString()
            }
            .collect {
                isLoading.value = false
                newBookList.addAll(it.result)
            }
    }

    private fun searchBooks(isReset: Boolean = false) {
        viewModelScope.launch {
            if (isReset) {
                page = 1
                isFinish = false
            }

            if (isFinish) return@launch

            getSearchBooksUseCase(queryText.value, page)
                .catch { throwable ->
                    errorMessage.value = throwable.message.toString()
                }
                .onStart { isLoading.value = true }
                .collect {
                    isLoading.value = false

                    bookList.addAll(it.result)

                    isEmpty.value = bookList.isEmpty()

                    if (it.totalBooks == bookList.size || it.result.isEmpty()) isFinish = true
                }
        }
    }

    private suspend fun refreshList() {
        newBookList.clear()
        bookList.clear()

        if (queryText.value.isNotEmpty())
            channel.send(queryText.value)
        else
            getNewBooks()
    }

    private fun loadNextPage() {
        if (isLoading.value || isFinish) return

        page++
        searchBooks()
    }
}