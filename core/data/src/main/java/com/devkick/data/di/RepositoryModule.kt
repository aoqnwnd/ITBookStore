package com.devkick.data.di

import com.devkick.data.repository.BookRepository
import com.devkick.data.repository.impl.BookRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository
}