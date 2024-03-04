package com.devkick.detail_feature

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun BookDetailScreen(
    modifier: Modifier,
    viewModel: BookDetailViewModel = hiltViewModel(),
    navigateBack: () -> Boolean,
) {
    val detailUiState: BookDetailUiState by viewModel.detailUiState.collectAsStateWithLifecycle()

    UI(
        detailUiState = detailUiState,
        modifier = modifier,
        navigateBack = navigateBack
    )
}

@Composable
fun UI(
    detailUiState: BookDetailUiState,
    modifier: Modifier,
    navigateBack: () -> Boolean,
) {
    Text(text = "detail")
}