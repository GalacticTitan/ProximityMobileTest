package com.kbj.aqiindex.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kbj.aqiindex.models.AQIBean
import kotlinx.coroutines.flow.Flow

@Dao
interface AQIDao {
    @Insert
    fun insertAll(dataList: List<AQIBean>)

    @Query("DELETE FROM AQIBean")
    fun deleteData()

    @Query("SELECT * FROM AQIBean baq WHERE baq.seconds = (SELECT MAX(seconds) FROM AQIBean ab WHERE ab.city = baq.city) GROUP BY baq.city")
    fun getLastValues() : Flow<List<AQIBean>>

    @Query("SELECT * FROM AQIBean baq WHERE baq.seconds = (SELECT MAX(seconds) FROM AQIBean ab WHERE ab.city = baq.city) GROUP BY baq.city")
    fun getValues() : List<AQIBean>

    @Query("SELECT * FROM AQIBean baq WHERE baq.city = :city ORDER BY baq.seconds DESC limit 50")
    fun getLastValuesOf(city: String) : Flow<List<AQIBean>>
}