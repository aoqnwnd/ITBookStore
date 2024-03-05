package com.devkick.detail_feature

sealed interface BookDetailSideEffect {
    data class OnCreate(val id: String) : BookDetailSideEffect
    data object ClickPdf2 : BookDetailSideEffect
    data object ClickPdf5 : BookDetailSideEffect
}