package com.kbj.aqiindex.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kbj.aqiindex.models.AQIBean

@Dao
interface AQIDao {
    @Insert
    fun insertAll(dataList: List<AQIBean>)

    @Query("SELECT * FROM AQIBean baq WHERE baq.seconds = (SELECT MAX(seconds) FROM AQIBean ab WHERE ab.city = baq.city) GROUP BY baq.city")
    fun getLastValues() : LiveData<List<AQIBean>>

    @Query("SELECT * FROM AQIBean baq WHERE baq.seconds = (SELECT MAX(seconds) FROM AQIBean ab WHERE ab.city = baq.city) GROUP BY baq.city")
    fun getValues() : List<AQIBean>

    @Query("SELECT * FROM AQIBean baq WHERE baq.city = :city ORDER BY baq.seconds DESC limit 50")
    fun getLastValuesOf(city: String) : LiveData<List<AQIBean>>
}