package com.kbj.aqiindex.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.kbj.aqiindex.R
import com.kbj.aqiindex.models.AQIBean
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

object UtilFunctions {
    var simpleDateFormat =
        SimpleDateFormat("hh : mm a", Locale.getDefault())

    /**
     * The logic used here is simple
     * if there is new item comes in terms of data it will be added to the
     * adapter data
     */
    fun processData(
        mContext: Context,
        dataList: List<AQIBean>,
        adapterDataList: ArrayList<AQIBean>
    ) {
        dataList.forEach {
            val itemIndex = adapterDataList.indexOf(it)
            it.color = processColors(mContext, it.aqi)
            if (itemIndex == -1) {
                adapterDataList.add(it)
            } else {
                adapterDataList[itemIndex] = it
            }
        }
        adapterDataList.forEach {
            val seconds = findTimeDifference(it)
            when {
                seconds < 60 -> {
                    it.lastUpdated = String.format(mContext.getString(R.string.seconds, seconds.toString()))
                }
                seconds < 60 * 60 -> {
                    it.lastUpdated = String.format(
                        mContext.getString(
                            R.string.minutes,
                            floor(it.seconds!! / 60.0).toString()
                        )
                    )
                }
                seconds < 60 * 60 * 60 -> {
                    it.lastUpdated = String.format(
                        mContext.getString(
                            R.string.hours,
                            floor(it.seconds!! / 60.0 * 60).toString()
                        )
                    )
                }
                seconds > 60 * 60 * 60 -> {
                    it.lastUpdated = simpleDateFormat.format(seconds * 1000)
                }
            }
        }
    }

    /**
     * This method will find the time difference in terms of seconds
     */
    fun findTimeDifference(it: AQIBean) =
        System.currentTimeMillis() / 1000 - it.seconds!!

    fun processColors(mContext: Context, value: Double): Int {
        when {
            value < 51 -> {
                return ContextCompat.getColor(mContext, R.color.dark_green)
            }
            value < 101 -> {
                return ContextCompat.getColor(mContext, R.color.light_green)
            }
            value < 201 -> {
                return ContextCompat.getColor(mContext, R.color.light_yellow)
            }
            value < 301 -> {
                return ContextCompat.getColor(mContext, R.color.light_orange)
            }
            value < 401 -> {
                return ContextCompat.getColor(mContext, R.color.red)
            }
            value > 400 -> {
                return ContextCompat.getColor(mContext, R.color.dark_red)
            }
        }
        return ContextCompat.getColor(mContext, R.color.black)
    }
}