package com.devkick.search_feature

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devkick.base.BookListItem
import com.devkick.base.PageableLazyColumn
import com.devkick.base.TextStyleEnum
import com.devkick.base.typography
import com.itbookstore.resources.R
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun BookSearchListScreen(
    modifier: Modifier = Modifier,
    viewModel: BookSearchListViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
) {
    val searchUiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is BookSearchSideEffect.NavigateToDetail -> navigateToDetail(it.isbn13)
            is BookSearchSideEffect.SendToToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    UI(
        modifier = modifier,
        searchUiState = searchUiState,
        searchQuery = searchQuery,
        queryChange = viewModel::onSearchQueryChanged
    )
}

@Composable
fun UI(
    modifier: Modifier = Modifier,
    searchUiState: BookSearchState,
    searchQuery: String,
    queryChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                value = searchQuery,
                onValueChange = { changedText ->
                    queryChange(changedText)
                },
                decorationBox = { innerTextField ->
                    Box(modifier = modifier) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.placeholder),
                                color = Color.Gray,
                                style = typography(TextStyleEnum.Body),
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                            )
                        }
                        innerTextField()
                    }
                },
                singleLine = true,
                textStyle = typography(TextStyleEnum.Body),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    if (searchQuery.isNotBlank()) queryChange(searchQuery)
                })
            )
        }

        when (searchUiState) {
            BookSearchState.Loading -> {
                Text(text = "now Loading")
            }

            is BookSearchState.Error -> {
                Text(text = "now Error ${searchUiState.exception.message}")
            }

            is BookSearchState.Success -> {
                PageableLazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    shouldStartPaginate = {

                    },
                ) {
                    items(searchUiState.books.result) {
                        BookListItem(
                            book = it,
                            navigateToDetail = {}
                        )
                    }
                }
            }

            BookSearchState.Empty ->Text(text = "empty List")
        }

    }
}
