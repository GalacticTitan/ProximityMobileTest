package com.kbj.aqiindex.di

import android.app.Application
import com.kbj.aqiindex.db.AQIDao
import com.kbj.aqiindex.db.AQIDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This HiltModule class is used to build boilerplate code for initilizing the
 * database variables
 */
@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAQIDb(application: Application?): AQIDatabase {
        return AQIDatabase.getDatabase(application!!.applicationContext)
    }

    @Provides
    @Singleton
    fun provideAQIDao(db: AQIDatabase): AQIDao {
        return db.aqiDao()
    }
}