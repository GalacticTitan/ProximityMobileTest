package com.kbj.aqiindex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kbj.aqiindex.repository.AQIRepository
import com.kbj.aqiindex.utils.StatusMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import com.google.gson.Gson
import com.kbj.aqiindex.callbacks.ConnectionCallBack
import com.kbj.aqiindex.models.AQIBean

/**
 * This {@link ViewModel} handles the data in the app which is persistent
 */

@HiltViewModel
class DataViewModel @Inject constructor(private val aqiRepository: AQIRepository) :
    ViewModel() {
    private var webSocket: WebSocket? = null
    private val closeCode = 1000
    lateinit var data: LiveData<List<AQIBean>>

    fun setWebHook() {
        webSocket = aqiRepository.getAQIConnector()
    }

    fun setDataLineage(city: String? = null) {
        data = if (city == null) {
            aqiRepository.getLatestData()
        } else {
            aqiRepository.getLastValuesOf(city)
        }
    }

    fun setConnectionCallBack(connectionCallBack: ConnectionCallBack){
        aqiRepository.connectionCallBack = connectionCallBack
    }

    fun cancelSocket() {
        webSocket?.cancel()
    }
}