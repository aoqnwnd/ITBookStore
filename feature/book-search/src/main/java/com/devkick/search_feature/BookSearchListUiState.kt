package com.devkick.search_feature

import com.devkick.base.ListViewType
import com.devkick.model.BookList

data class BookSearchListUiState(
    val queryText: String = "",
    val bookList: List<BookList.Book> = listOf(),
    val newBookList: List<BookList.Book> = listOf(),
    val listViewType: ListViewType = ListViewType.List,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
)