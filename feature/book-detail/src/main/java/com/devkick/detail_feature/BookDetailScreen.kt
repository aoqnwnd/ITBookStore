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

    UI(
        modifier = modifier,
        navigateBack = navigateBack
    )
}

@Composable
fun UI(
    modifier: Modifier,
    navigateBack: () -> Boolean,
) {
    Text(text = "detail")
}
