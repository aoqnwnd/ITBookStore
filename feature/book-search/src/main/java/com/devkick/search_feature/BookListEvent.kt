package com.devkick.search_feature

sealed interface BookListEvent {
    data class OnCreate(val id: String) : BookListEvent
    data object ClickPdf2 : BookListEvent
    data object ClickPdf5 : BookListEvent
}