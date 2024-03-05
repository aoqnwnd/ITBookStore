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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devkick.base.PageableLazyColumn
import com.devkick.base.PreviewBookListItem
import com.devkick.base.TextStyleEnum
import com.devkick.base.typography
import com.itbookstore.resources.R
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectSideEffect
import kotlin.reflect.KFunction1

@Composable
fun BookSearchListScreen(
    modifier: Modifier = Modifier,
    viewModel: BookSearchListViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchUiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
val context = LocalContext.current
    viewModel.collectSideEffect {
        when (it) {
            BookSearchSideEffect.OnCreate -> TODO()
            is BookSearchSideEffect.SendToToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
            is BookSearchSideEffect.NavigateToDetail -> TODO()
        }
    }

    UI(
        modifier = modifier,
        searchUiState = searchUiState,
        navigateToDetail = navigateToDetail,
        searchQuery = searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
    )
}

@Composable
fun UI(
    modifier: Modifier = Modifier,
    searchUiState: BookSearchState,
    navigateToDetail: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChanged: KFunction1<String, Unit>,
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
                    onSearchQueryChanged(changedText)
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
                    if (searchQuery.isNotBlank()) onSearchQueryChanged(searchQuery)
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
                    shouldStartPaginate = { /*TODO*/ },
                ) {
                    items(searchUiState.books.totalBooks) {
                        PreviewBookListItem()
                    }
                }
            }
        }

    }
}
