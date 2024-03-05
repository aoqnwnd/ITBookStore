package com.devkick.search_feature

import com.devkick.model.BookList

sealed interface BookListUiState {
    data object Loading : BookListUiState
    data object Empty : BookListUiState

    data class Fail(
        val message: String
    ) : BookListUiState

    data class Success(
        val data: BookList
    ) : BookListUiState
}