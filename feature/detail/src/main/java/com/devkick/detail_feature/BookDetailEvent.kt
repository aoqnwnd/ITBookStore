package com.devkick.detail_feature

sealed interface BookDetailEvent {
    data class OnCreate(val id: String) : BookDetailEvent
    data object ClickPdf2 : BookDetailEvent
    data object ClickPdf5 : BookDetailEvent
}