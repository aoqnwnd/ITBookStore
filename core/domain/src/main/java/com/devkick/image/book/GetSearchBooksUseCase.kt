package com.devkick.image.book

import com.devkick.data.repository.BookRepository
import com.devkick.model.BookList
import kotlinx.coroutines.flow.Flow

class GetSearchBooksUseCase(
    private val bookRepository: BookRepository,
) {
    operator fun invoke(
        query: String,
        page: Int,
    ): Flow<BookList> = bookRepository.getSearchBooks(query, page)
}