package com.devkick.image.book

import com.devkick.data.repository.BookRepository
import com.devkick.model.BookList
import kotlinx.coroutines.flow.Flow

class GetNewBooksUseCase(
    private val bookRepository: BookRepository,
) {
    operator fun invoke(): Flow<BookList> = bookRepository.getNewBooks()
}