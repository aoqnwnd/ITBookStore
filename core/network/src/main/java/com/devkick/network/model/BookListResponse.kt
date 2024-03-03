package com.devkick.network.model

import com.google.gson.annotations.SerializedName

data class BookListResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("page")
    val page: String?,
    @SerializedName("books")
    val books: List<Book>,
) {
    data class Book(
        @SerializedName("title")
        val title: String,
        @SerializedName("subtitle")
        val subtitle: String,
        @SerializedName("isbn13")
        val isbn13: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("url")
        val url: String
    )
}