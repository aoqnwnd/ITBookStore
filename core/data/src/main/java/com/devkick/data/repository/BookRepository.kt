package com.devkick.data.repository

import com.devkick.network.model.BookDetailResponse
import com.devkick.network.model.BookListResponse
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getSearchBooks(query: String, page: Int): Flow<BookListResponse>
    suspend fun getNewBooks(): Flow<BookListResponse>
    suspend fun getBookDetail(id: String): BookDetailResponse?
}