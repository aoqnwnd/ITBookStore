package com.devkick.model

data class BookList(
    val totalBooks: Int,
    val page: Int,
    val result: List<Book>,
) {
    data class Book(
        val title: String,
        val subtitle: String,
        val isbn13: String,
        val price: String,
        val image: String,
        val url: String,
    )
}
