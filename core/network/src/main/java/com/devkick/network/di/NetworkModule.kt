package com.devkick.network.di

import com.devkick.network.service.BookService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            if (!message.startsWith("{") && !message.startsWith("[")) {
                Timber.tag("OkHttp").d(message)
            } else {
                try {
                    Timber
                        .tag("OkHttp")
                        .d(
                            GsonBuilder()
                                .setPrettyPrinting().create()
                                .toJson(JsonParser().parse(message))
                        )
                } catch (m: JsonSyntaxException) {
                    Timber.tag("OkHttp").d(message)
                }
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logger: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(logger)
        .build()

    @Provides
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.itbook.store/1.0/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    fun provideImageService(retrofit: Retrofit): BookService {
        return retrofit.create(BookService::class.java)
    }
}