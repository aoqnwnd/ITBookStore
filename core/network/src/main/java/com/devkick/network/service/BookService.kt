package com.devkick.network.service

import com.devkick.network.model.BookDetailResponse
import com.devkick.network.model.BookListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookService {
    @GET("/search/{query}/{page}")
    suspend fun getSearchBooks(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): BookListResponse

    @GET("/new")
    suspend fun getNewBooks(): BookListResponse

    @GET("/books/{isbn13}")
    suspend fun getBookDetail(
        @Path("isbn13") isbn13: String,
    ): BookDetailResponse
}