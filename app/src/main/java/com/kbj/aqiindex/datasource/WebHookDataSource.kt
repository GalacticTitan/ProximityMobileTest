package com.kbj.aqiindex.datasource

import okhttp3.WebSocket
import okhttp3.WebSocketListener

interface WebHookDataSource {
    fun getAQIConnector() : WebSocket
}