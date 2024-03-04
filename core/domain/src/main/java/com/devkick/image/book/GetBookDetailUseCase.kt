package com.devkick.image.book

import com.devkick.data.repository.BookRepository
import com.devkick.model.Book
import kotlinx.coroutines.flow.Flow

class GetBookDetailUseCase(
    private val bookRepository: BookRepository,
) {
    operator fun invoke(
        id: String
    ): Flow<Book> = bookRepository.getBookDetail(id)
}