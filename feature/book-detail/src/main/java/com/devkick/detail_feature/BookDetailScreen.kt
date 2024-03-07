package com.devkick.detail_feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devkick.base.BackdropBookImage
import com.devkick.base.TextStyleEnum
import com.devkick.base.typography
import com.itbookstore.resource.R

@Composable
fun BookDetailScreen(
    modifier: Modifier,
    viewModel: BookDetailViewModel = hiltViewModel(),
    navigateBack: () -> Boolean,
) {
    val bookUiState: BookDetailUiState by viewModel.bookUiState.collectAsStateWithLifecycle()
    val bookEvent: BookDetailEvent by viewModel.eventFlow.collectAsStateWithLifecycle(
        BookDetailEvent.Waiting
    )
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(bookEvent) {
        when (val event = bookEvent) {
            BookDetailEvent.ClickBack -> navigateBack()
            is BookDetailEvent.ClickUrl -> uriHandler.openUri(event.url)
            BookDetailEvent.Waiting -> {}
        }
    }

    UI(
        modifier = modifier,
        bookUiState = bookUiState,
        bookEvent = viewModel::event
    )
}

@Composable
fun UI(
    modifier: Modifier,
    bookUiState: BookDetailUiState,
    bookEvent: (BookDetailEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(30.dp)
                    .padding(start = 10.dp)
                    .clickable {
                        bookEvent(BookDetailEvent.ClickBack)
                    },
            )

            Text(
                text = stringResource(R.string.detail),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(Alignment.Center),
                style = typography(textStyle = TextStyleEnum.Title)
            )
        }

        LazyColumn {
            when (bookUiState) {
                is BookDetailUiState.Fail -> {
                    item {
                        Text(
                            text = bookUiState.errorMessage,
                            modifier = Modifier
                                .fillMaxSize(),
                            color = Color.Red,
                            style = typography(textStyle = TextStyleEnum.Body)
                        )
                    }
                }

                BookDetailUiState.Loading -> {
                    item {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            color = Color.LightGray
                        )
                    }

                    item {
                        Text(
                            text = stringResource(id = R.string.now_loading),
                            modifier = Modifier
                                .fillMaxSize(),
                            color = Color.LightGray,
                            style = typography(textStyle = TextStyleEnum.Body)
                        )
                    }

                }

                is BookDetailUiState.Success -> {
                    item {
                        Box {
                            BackdropBookImage(url = bookUiState.book.image)
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp)
                        ) {
                            Text(
                                text = bookUiState.book.authors,
                                style = typography(textStyle = TextStyleEnum.Title),
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .weight(1f)
                            )

                            Text(
                                text = bookUiState.book.price,
                                style = typography(textStyle = TextStyleEnum.Title),
                                color = Color.Green,
                            )
                        }

                        Text(
                            text = bookUiState.book.title,
                            style = typography(textStyle = TextStyleEnum.HeadLine),
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                        )

                        Text(
                            text = bookUiState.book.subtitle,
                            style = typography(textStyle = TextStyleEnum.Body),
                            color = Color.LightGray
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Gray)
                                .padding(10.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    stringResource(id = R.string.rating),
                                    color = Color.LightGray,
                                    style = typography(textStyle = TextStyleEnum.Body)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_star),
                                        contentDescription = "rating",
                                        colorFilter = ColorFilter.tint(Color.Yellow),
                                        modifier = Modifier
                                            .padding(end = 3.dp)
                                            .size(12.dp)
                                    )

                                    Text(
                                        text = "${bookUiState.book.rating}",
                                        style = typography(textStyle = TextStyleEnum.Body)
                                    )
                                }
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    stringResource(id = R.string.pages),
                                    color = Color.LightGray,
                                    style = typography(textStyle = TextStyleEnum.Body)
                                )

                                Text(
                                    text = "${bookUiState.book.pages}",
                                    style = typography(textStyle = TextStyleEnum.Body)
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    stringResource(id = R.string.year),
                                    color = Color.LightGray,
                                    style = typography(textStyle = TextStyleEnum.Body)
                                )

                                Text(
                                    text = "${bookUiState.book.year}",
                                    style = typography(textStyle = TextStyleEnum.Body)
                                )
                            }
                        }
                    }

                    item {
                        Text(
                            text = stringResource(id = R.string.desc),
                            style = typography(textStyle = TextStyleEnum.HeadLine),
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 5.dp)
                        )

                        Text(
                            text = bookUiState.book.desc,
                            style = typography(textStyle = TextStyleEnum.Body),
                            color = Color.LightGray
                        )
                    }

                    item {
                        Text(
                            text = stringResource(id = R.string.publisher),
                            style = typography(textStyle = TextStyleEnum.Title),
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 5.dp)
                        )

                        Text(text = bookUiState.book.publisher)
                    }

                    item {
                        Text(
                            text = stringResource(id = R.string.isbn),
                            style = typography(textStyle = TextStyleEnum.Title),
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 5.dp)
                        )

                        Text(
                            text = bookUiState.book.isbn10,
                            style = typography(textStyle = TextStyleEnum.Body),
                            modifier = Modifier.padding(bottom = 5.dp)
                        )

                        Text(
                            text = bookUiState.book.isbn13,
                            style = typography(textStyle = TextStyleEnum.Body),
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.url),
                                style = typography(textStyle = TextStyleEnum.Title),
                                modifier = Modifier
                                    .padding(end = 10.dp)
                            )

                            Text(
                                text = bookUiState.book.url,
                                color = Color.Blue,
                                style = typography(textStyle = TextStyleEnum.Body),
                                modifier = Modifier
                                    .drawBehind {
                                        val strokeWidthPx = 1.dp.toPx()
                                        val verticalOffset = size.height - 2.sp.toPx()
                                        drawLine(
                                            color = Color.Blue,
                                            strokeWidth = strokeWidthPx,
                                            start = Offset(0f, verticalOffset),
                                            end = Offset(size.width, verticalOffset)
                                        )
                                    }
                                    .clickable {
                                        bookEvent(BookDetailEvent.ClickUrl(bookUiState.book.url))
                                    }
                            )
                        }
                    }

                    items(bookUiState.book.pdf) { pdf ->
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.pdf),
                                modifier = Modifier
                                    .padding(end = 10.dp)
                            )

                            Text(
                                text = pdf,
                                style = typography(textStyle = TextStyleEnum.Body),
                                color = Color.Blue,
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .drawBehind {
                                        val strokeWidthPx = 1.dp.toPx()
                                        val verticalOffset = size.height - 2.sp.toPx()
                                        drawLine(
                                            color = Color.Blue,
                                            strokeWidth = strokeWidthPx,
                                            start = Offset(0f, verticalOffset),
                                            end = Offset(size.width, verticalOffset)
                                        )
                                    }
                                    .clickable {
                                        bookEvent(BookDetailEvent.ClickUrl(bookUiState.book.url))
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}