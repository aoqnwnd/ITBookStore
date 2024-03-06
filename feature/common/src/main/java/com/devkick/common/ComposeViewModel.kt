package com.devkick.common

import androidx.lifecycle.ViewModel

abstract class ComposeViewModel<UiState, UiEvent> : ViewModel() {

    abstract fun uiState(): UiState

    abstract fun onEvent(event: UiEvent)
}