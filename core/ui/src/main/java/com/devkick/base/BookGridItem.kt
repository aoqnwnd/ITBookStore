package com.devkick.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devkick.model.BookList
import java.util.UUID

@Composable
fun BookGridItem(
    modifier: Modifier = Modifier,
    book: BookList.Book,
    clickItem: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .clickable { clickItem() },
    ) {
        Column(
            modifier = modifier
                .padding(5.dp),
        ) {
            Image(
                painter = rememberAsyncImagePainter(book.image),
                contentDescription = "listImage",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray),
            )

            Text(
                text = book.title,
                style = typography(textStyle = TextStyleEnum.Title),
                modifier = Modifier
                    .padding(top = 5.dp)
            )

            Text(
                text = book.subtitle,
                style = typography(textStyle = TextStyleEnum.Body),
                modifier = Modifier
                    .padding(vertical = 5.dp)
            )

            Text(
                text = book.price,
                style = typography(textStyle = TextStyleEnum.Body)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBookGridItem() {
    BookGridItem(
        book = BookList.Book(
            title = "Clean Architecture",
            subtitle = "first software programming",
            isbn13 = "p1sqw/1r1mf1",
            price = "32$",
            image = "https://contents.kyobobook.co.kr/sih/fit-in/280x0/pdt/9788966262472.jpg",
            url = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fproduct.kyobobook.co.kr%2Fdetail%2FS000001033082&psig=AOvVaw1yRpaLAyDfgEhErkeMgTug&ust=1709694306946000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPi_x-CR3IQDFQAAAAAdAAAAABAE",
            uuId = UUID.randomUUID()
        ),
        clickItem = {}
    )
}

@Preview
@Composable
fun PreviewBookGridList() {
    PageableLazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        shouldStartPaginate = { /*TODO*/ },
        modifier = Modifier,
    ) {
        items(8) {
            PreviewBookGridItem()
        }
    }
}