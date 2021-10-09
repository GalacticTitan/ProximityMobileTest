package com.kbj.aqiindex.datasource

import androidx.lifecycle.LiveData
import com.kbj.aqiindex.models.AQIBean

interface LocalDataSource {
    fun insertData(dataList: List<AQIBean>)
    fun getLatestData() : LiveData<List<AQIBean>>
    fun getLastValuesOf(city: String) : LiveData<List<AQIBean>>
}