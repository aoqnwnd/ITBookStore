package com.devkick.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devkick.model.BookList

@Composable
fun BookListItem(
    modifier: Modifier = Modifier,
    book: BookList.Book,
    clickItem: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth()
            .clickable { clickItem() },
    ) {
        Row(
            modifier = modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(book.image),
                contentDescription = "listImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray),
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = book.title,
                    style = typography(textStyle = TextStyleEnum.Title),
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                )

                Text(
                    text = book.subtitle,
                    style = typography(textStyle = TextStyleEnum.Body)
                )
            }

            Text(
                text = book.price,
                style = typography(textStyle = TextStyleEnum.Body)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBookListItem() {
    BookListItem(
        book = BookList.Book(
            title = "Clean Architecture",
            subtitle = "first software programming",
            isbn13 = "p1sqw/1r1mf1",
            price = "32$",
            image = "https://contents.kyobobook.co.kr/sih/fit-in/280x0/pdt/9788966262472.jpg",
            url = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fproduct.kyobobook.co.kr%2Fdetail%2FS000001033082&psig=AOvVaw1yRpaLAyDfgEhErkeMgTug&ust=1709694306946000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPi_x-CR3IQDFQAAAAAdAAAAABAE"
        ),
        clickItem = {}
    )
}

@Preview
@Composable
fun PreviewBookListList() {
    PageableLazyVerticalGrid(
        columns = GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        shouldStartPaginate = { /*TODO*/ },
    ) {
        items(4) {
            PreviewBookListItem()
        }
    }
}