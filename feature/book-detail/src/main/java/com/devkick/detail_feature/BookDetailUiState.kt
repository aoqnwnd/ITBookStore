package com.devkick.detail_feature

import com.devkick.model.Book

sealed interface BookDetailUiState {
    data object Loading: BookDetailUiState

    data class Fail(
        val errorMessage: String
    ) : BookDetailUiState

    data class Success(
        val book: Book
    ) : BookDetailUiState
}