package com.devkick.search_feature

import com.devkick.model.BookList

sealed class BookSearchState {
    data object Loading : BookSearchState()
    data object Empty : BookSearchState()
    data class Success(
        val books: BookList,
    ) : BookSearchState()

    class Error(
        val exception: Throwable,
    ) : BookSearchState()
}