package com.devkick.image.book

import com.devkick.data.repository.BookRepository
import com.devkick.model.BookList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {
    operator fun invoke(): Flow<BookList> = bookRepository.getNewBooks()
}