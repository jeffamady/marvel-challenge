package com.amadydev.intermedia.di

import com.amadydev.intermedia.BuildConfig.BASE_API_URL
import com.amadydev.intermedia.data.datasource.CharactersDataSource
import com.amadydev.intermedia.data.services.CharactersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @MarvelApi
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    fun provideCharactersService(@MarvelApi retrofit: Retrofit): CharactersService =
        retrofit.create(CharactersService::class.java)

    @Provides
    fun provideCharactersDataSource(charactersService: CharactersService) =
        CharactersDataSource(charactersService)
}