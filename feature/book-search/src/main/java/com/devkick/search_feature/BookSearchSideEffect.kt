package com.devkick.search_feature

sealed class BookSearchSideEffect {
    data object OnCreate : BookSearchSideEffect()
    data class SendToToast(val message: String) : BookSearchSideEffect()
    data class NavigateToDetail(val isbn13: String) : BookSearchSideEffect()
}