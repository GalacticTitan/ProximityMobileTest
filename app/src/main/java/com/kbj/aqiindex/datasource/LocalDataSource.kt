package com.kbj.aqiindex.datasource

import androidx.lifecycle.LiveData
import com.kbj.aqiindex.models.AQIBean
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun insertData(dataList: List<AQIBean>)
    fun getLatestData() : Flow<List<AQIBean>>
    fun getLastValuesOf(city: String) : Flow<List<AQIBean>>
}