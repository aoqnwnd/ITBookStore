package com.devkick.base

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import timber.log.Timber

@Composable
fun PageableLazyVerticalGrid(
    columns: GridCells,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    shouldStartPaginate: () -> Unit,
    content: LazyGridScope.() -> Unit,
) {
    val lazyGridListState = rememberLazyGridState()

    fun isStartPaginate(): Boolean {
        return (lazyGridListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9) >=
                (lazyGridListState.layoutInfo.totalItemsCount - 3)
    }

    val shouldPaginate = isStartPaginate()

    LaunchedEffect(shouldPaginate) {
        if (shouldPaginate) shouldStartPaginate()
    }

    LazyVerticalGrid(
        columns = columns,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement,
        state = lazyGridListState,
        modifier = modifier
    ) {
        content()
    }
}