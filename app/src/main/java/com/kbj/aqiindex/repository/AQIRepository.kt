package com.kbj.aqiindex.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kbj.aqiindex.callbacks.ConnectionCallBack
import com.kbj.aqiindex.datasource.LocalDataSource
import com.kbj.aqiindex.datasource.WebHookDataSource
import com.kbj.aqiindex.db.AQIDao
import com.kbj.aqiindex.models.AQIBean
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Will handle the local and remote data
 */
class AQIRepository @Inject constructor(
    private val mClient: OkHttpClient,
    private val request: Request,
    private val aqiDao: AQIDao
) :
    WebHookDataSource, LocalDataSource {
    private val listType: Type = object : TypeToken<List<AQIBean?>?>() {}.type
    private val gson = Gson()
    var connectionCallBack: ConnectionCallBack? = null

    private val listener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            val dataList = gson.fromJson<List<AQIBean>>(text, listType)
            insertData(dataList.onEach {
                it.seconds = System.currentTimeMillis() / 1000
            })
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            connectionCallBack?.onConnectionFailure()
        }
    }

    override fun getAQIConnector() =
        mClient.newWebSocket(request, listener)

    override fun insertData(dataList: List<AQIBean>) {
        GlobalScope.launch {
            aqiDao.insertAll(dataList)
        }
    }

    override fun getLastValuesOf(city: String) = aqiDao.getLastValuesOf(city)

    override fun getLatestData() = aqiDao.getLastValues()
}