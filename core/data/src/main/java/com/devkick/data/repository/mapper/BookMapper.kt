package com.devkick.data.repository.mapper

import com.devkick.model.Book
import com.devkick.model.BookList
import com.devkick.network.model.BookDetailResponse
import com.devkick.network.model.BookListResponse

object BookMapper {
    fun mapFromBookListResponseToModel(bookListResponse: BookListResponse) =
        BookList(
            page = bookListResponse.page?.toInt() ?: 0,
            result = bookListResponse.books.map {
                BookList.Book(
                    title = it.title,
                    subtitle = it.subtitle,
                    isbn13 = it.isbn13,
                    price = it.price,
                    image = it.image,
                    url = it.url
                )
            },
            totalBooks = bookListResponse.total.toInt(),
        )

    fun mapFromBookDetailResponseToModel(bookDetailResponse: BookDetailResponse): Book {
        return Book(
            title = bookDetailResponse.title,
            subtitle = bookDetailResponse.subtitle,
            authors = bookDetailResponse.authors,
            publisher = bookDetailResponse.publisher,
            isbn10 = bookDetailResponse.isbn10,
            isbn13 = bookDetailResponse.isbn13,
            pages = bookDetailResponse.pages.toInt(),
            year = bookDetailResponse.year.toInt(),
            rating = bookDetailResponse.rating.toInt(),
            desc = bookDetailResponse.desc,
            price = bookDetailResponse.price,
            image = bookDetailResponse.image,
            url = bookDetailResponse.url,
            pdf = bookDetailResponse.pdf?.values?.toList() ?: emptyList(),
        )
    }

}