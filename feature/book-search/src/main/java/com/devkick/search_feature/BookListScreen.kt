package com.devkick.search_feature

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.reflect.KFunction1

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    viewModel: BookListViewModel = hiltViewModel(),
    navigateToDetail: (String)-> Unit,
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResultUiState by viewModel.bookListUiState.collectAsStateWithLifecycle()

    UI(
        modifier = modifier,
        searchResultUiState = searchResultUiState,
        navigateToDetail = navigateToDetail,
        searchQuery = searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged
    )
}

@Composable
fun UI(
    modifier: Modifier,
    searchResultUiState: BookListUiState,
    navigateToDetail: Any,
    searchQuery: String,
    onSearchQueryChanged: KFunction1<String, Unit>,
) {
    Text(text = "BookList")
}
