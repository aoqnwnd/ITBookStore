package com.devkick.image.book

import com.devkick.data.repository.BookRepository
import com.devkick.network.model.BookDetailResponse

class GetBookDetailUseCase(
    private val bookRepository: BookRepository,
) {
    suspend operator fun invoke(id: String): BookDetailResponse? {
        return BookDetailResponse(
            error = "turpis",
            title = "elementum",
            subtitle = "diam",
            authors = "ac",
            publisher = "potenti",
            isbn10 = "explicari",
            isbn13 = "invenire",
            pages = "constituto",
            year = "hac",
            rating = "ex",
            desc = "urbanitas",
            price = "cras",
            image = "posuere",
            url = "https://duckduckgo.com/?q=efficiantur",
            pdf = BookDetailResponse.Pdf(
                chapter2 = "qualisque",
                chapter5 = "erroribus"
            )
        )
    }
}