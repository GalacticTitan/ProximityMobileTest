package com.kbj.aqiindex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kbj.aqiindex.models.AQIBean

/**
 * database which is used to store the incoming data
 */
@Database(
    entities = [AQIBean::class],
    version = 1,
    exportSchema = false
)
abstract class AQIDatabase : RoomDatabase() {
    abstract fun aqiDao() : AQIDao

    companion object {
        @Volatile private var instance: AQIDatabase? = null

        fun getDatabase(context: Context): AQIDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AQIDatabase::class.java, "AQI.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}