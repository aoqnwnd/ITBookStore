package com.devkick.search_feature

sealed interface BookSearchEvent {
    data class QueryChange(val query: String) : BookSearchEvent
    data object LoadNextPage : BookSearchEvent
    data object ClickListTypeChange : BookSearchEvent
}