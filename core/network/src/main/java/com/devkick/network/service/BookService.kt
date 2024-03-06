package com.devkick.network.service

import com.devkick.network.model.BookDetailResponse
import com.devkick.network.model.BookListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookService {
    @GET("search/{query}/{page}")
    suspend fun getSearchBooks(
        @Path("query") query: String,
        @Path("page") page: Int,
    ): BookListResponse

    @GET("new")
    suspend fun getNewBooks(): BookListResponse

    @GET("books/{isbn13}")
    suspend fun getBookDetail(
        @Path("isbn13") isbn13: String,
    ): BookDetailResponse
}