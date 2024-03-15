package com.devkick.data.di

import com.devkick.data.repository.BookRepository
import com.devkick.data.repository.impl.BookRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Provides
    fun bindsBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository
}