package com.kbj.aqiindex

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.kbj.aqiindex.db.AQIDao
import com.kbj.aqiindex.db.AQIDatabase
import com.kbj.aqiindex.models.AQIBean
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import org.mockito.Mock
import org.junit.Rule
import org.mockito.Mockito.verify
import java.util.*


@RunWith(AndroidJUnit4::class)
@MediumTest
class DatabaseInsertionTest {
    lateinit var aqiDao: AQIDao
    lateinit var db: AQIDatabase

    @Before
   fun createDb() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AQIDatabase::class.java).build()
        aqiDao = db.aqiDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeUserAndReadInList() {
        val aqiBean = AQIBean(null, "Kozhikode", 14.1, null, System.currentTimeMillis(), "null")
        aqiDao.insertAll(arrayListOf(aqiBean))
        assertThat(1, equalTo(aqiDao.getValues().size))
    }

}