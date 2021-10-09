package com.kbj.aqiindex.di

import com.kbj.aqiindex.utils.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton

/**
 * This singleton dependency injection module will handle the initialization of
 * {@literal @}{@link com.kbj.aqiindex.db.AQIDatabase} database
 */
@Module
@InstallIn(SingletonComponent::class)
class WebSocketModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @Singleton
    fun provideHttpRequest(): Request {
        return Request.Builder().url(ApiConstants.BASE_URL).build()
    }
}