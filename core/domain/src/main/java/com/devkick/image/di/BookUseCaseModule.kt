package com.devkick.image.di

import com.devkick.data.repository.BookRepository
import com.devkick.image.book.GetBookDetailUseCase
import com.devkick.image.book.GetSearchBooksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookUseCaseModule {
    @Provides
    @Singleton
    fun provideGetSearchBooksUseCase(
        bookRepository: BookRepository,
    ): GetSearchBooksUseCase {
        return GetSearchBooksUseCase(bookRepository)
    }

    @Provides
    @Singleton
    fun provideGetBookDetailUseCase(
        bookRepository: BookRepository,
    ): GetBookDetailUseCase {
        return GetBookDetailUseCase(bookRepository)
    }
}