package com.devkick.data.repository

import com.devkick.model.Book
import com.devkick.model.BookList
import com.devkick.network.model.BookDetailResponse
import com.devkick.network.model.BookListResponse
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getSearchBooks(query: String, page: Int): Flow<BookList>
    fun getNewBooks(): Flow<BookList>
    fun getBookDetail(isbn13: String): Flow<Book>
}