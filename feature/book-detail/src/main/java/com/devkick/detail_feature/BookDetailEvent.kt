package com.devkick.detail_feature

sealed class BookDetailEvent {
    data object Waiting : BookDetailEvent()
    data object ClickBack : BookDetailEvent()
    data class ClickUrl(val url: String) : BookDetailEvent()
}