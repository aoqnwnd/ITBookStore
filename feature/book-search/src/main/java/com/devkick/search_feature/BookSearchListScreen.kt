package com.devkick.search_feature

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devkick.base.BookGridItem
import com.devkick.base.BookListItem
import com.devkick.base.ListViewType
import com.devkick.base.PageableLazyVerticalGrid
import com.devkick.base.TextStyleEnum
import com.devkick.base.typography
import com.itbookstore.resource.R

@Composable
fun BookSearchListScreen(
    modifier: Modifier = Modifier,
    viewModel: BookSearchListViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
) {
    UI(
        modifier = modifier,
        state = viewModel.uiState(),
        event = viewModel::onEvent,
        suspendEvent = viewModel::onSuspendEvent,
        navigateToDetail = navigateToDetail
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UI(
    modifier: Modifier = Modifier,
    state: BookSearchListUiState,
    event: (BookSearchEvent) -> Unit,
    suspendEvent: suspend (BookSearchEvent) -> Unit,
    navigateToDetail: (String) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            // 서버 호출 실행
            suspendEvent(BookSearchEvent.RefreshList)

            pullToRefreshState.endRefresh()
        }
    }

    val scaleFraction = if (pullToRefreshState.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState.progress).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        SearchTextField(
            searchQuery = state.queryText,
            event = event
        )

        val span = when (state.listViewType) {
            ListViewType.List -> 1
            ListViewType.Grid -> 3
        }

        Box(
            modifier = Modifier
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            PageableLazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = GridCells.Fixed(span),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                shouldStartPaginate = {
                    if (state.queryText.isNotEmpty())
                        event(BookSearchEvent.LoadNextPage)
                },
            ) {
                items(
                    items = if (state.queryText.isEmpty())
                        state.newBookList
                    else
                        state.bookList,
                    key = { it.uuId },
                ) {
                    Box(
                        modifier = Modifier
                            .animateItemPlacement(
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearOutSlowInEasing,
                                )
                            )
                    ) {
                        when (state.listViewType) {
                            ListViewType.List -> BookListItem(
                                book = it,
                            ) { navigateToDetail(it.isbn13) }

                            ListViewType.Grid -> BookGridItem(
                                book = it,
                            ) { navigateToDetail(it.isbn13) }
                        }
                    }
                }

                if (state.isLoading) {
                    item(span = { GridItemSpan(span) }) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            color = Color.LightGray
                        )
                    }
                }

                item(span = { GridItemSpan(span) }) {
                    if (state.isEmpty) {
                        Text(
                            text = stringResource(id = R.string.no_result),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 150.dp)
                        )
                    }
                }
            }

            PullToRefreshContainer(
                modifier = Modifier
                    .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction)
                    .align(Alignment.TopCenter),
                state = pullToRefreshState,
            )
        }
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchQuery: String,
    event: (BookSearchEvent) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchTriggered = {
        keyboardController?.hide()
    }

    Row(
        modifier = Modifier
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 4.dp, vertical = 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .weight(1f)
                .padding(5.dp),
            value = searchQuery,
            onValueChange = { changedText ->
                event(BookSearchEvent.QueryChange(changedText))
            },
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
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
            textStyle = typography(TextStyleEnum.Title),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchTriggered() })
        )

        Image(
            painter = painterResource(id = R.drawable.ic_list),
            contentDescription = "change list type",
            modifier = Modifier
                .padding(5.dp)
                .size(20.dp)
                .clickable {
                    event(BookSearchEvent.ClickListTypeChange)
                }
        )
    }
}
