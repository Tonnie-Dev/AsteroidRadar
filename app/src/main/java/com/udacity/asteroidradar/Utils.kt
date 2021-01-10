package com.udacity.asteroidradar

import android.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Utils {
    companion object {

        private fun getDay(time: Long):String = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(
            time)
        fun getTodayDate(): String {
            val currentTime = System.currentTimeMillis()
            return getDay(currentTime)
        }


        fun getDaysAgo(days: Long):String{
            val diffAgo = TimeUnit.DAYS.toMillis(days)
            val time = System.currentTimeMillis() - diffAgo
            return getDay(time)
        }


        fun getDaysTo(days: Long):String{
            val diffAgo = TimeUnit.DAYS.toMillis(days)
            val time = System.currentTimeMillis() + diffAgo
            return getDay(time)
        }
    }
}