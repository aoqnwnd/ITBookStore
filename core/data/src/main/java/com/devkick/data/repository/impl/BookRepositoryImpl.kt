package com.devkick.data.repository.impl

import com.devkick.data.repository.BookRepository
import com.devkick.data.repository.mapper.BookMapper
import com.devkick.model.Book
import com.devkick.model.BookList
import com.devkick.network.service.BookService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookService: BookService,
) : BookRepository {
    override fun getSearchBooks(
        query: String,
        page: Int,
    ): Flow<BookList> = flow {
        emit(
            BookMapper.mapFromBookListResponseToModel(
                bookService.getSearchBooks(query, page)
            )
        )
    }

    override fun getNewBooks(): Flow<BookList> = flow {
        emit(
            BookMapper.mapFromBookListResponseToModel(
                bookService.getNewBooks()
            )
        )
    }


    override fun getBookDetail(isbn13: String): Flow<Book> = flow {
        emit(
            BookMapper.mapFromBookDetailResponseToModel(
                bookService.getBookDetail(isbn13)
            )
        )
    }
}