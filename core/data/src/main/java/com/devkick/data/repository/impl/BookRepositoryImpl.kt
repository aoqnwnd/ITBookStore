package com.devkick.data.repository.impl

import com.devkick.data.repository.BookRepository
import com.devkick.data.repository.mapper.BookMapper
import com.devkick.network.model.BookDetailResponse
import com.devkick.network.model.BookListResponse
import com.devkick.network.service.BookService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookService: BookService,
    private val bookMapper: BookMapper
) : BookRepository {
    override suspend fun getSearchBooks(query: String, page: Int): Flow<BookListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getNewBooks(): Flow<BookListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookDetail(id: String): BookDetailResponse? {
        TODO("Not yet implemented")
    }
}