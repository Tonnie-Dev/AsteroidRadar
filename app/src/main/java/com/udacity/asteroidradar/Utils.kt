package com.udacity.asteroidradar

import android.util.TimeUtils
import okhttp3.internal.Util
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


        fun getDaysTo(days: Long):String{
            val diffAgo = TimeUnit.DAYS.toMillis(days)
            val time = System.currentTimeMillis() + diffAgo
            return getDay(time)
        }
    }
}

enum class DateFilter(val startDate: String, val endDate: String){

    TODAY_ASTEROIDS(Utils.getTodayDate(),Utils.getTodayDate()), WEEK_ASTEROIDS(Utils.getTodayDate(), Utils
            .getDaysTo(7)), SAVED_ASTEROIDS("", "")
}

enum class DurationRange(val range: String){

    RANGE_TODAY("Today"), RANGE_ONE_WEEK("Week"), RANGE_ALL_TIME("AllTime")
}

/*
sealed class DateFilter(val startDate: String, val endDate: String){

    class TodayAsteroids(Utils.getTodayDate(),Utils.getTodayDate()):DateFilter
    class WeekAsteroids(Utils.getTodayDate(), Utils.getDaysTo(7))
    class AllAsteroids(Utils.getTodayDate(), Utils.getDaysTo(7))

}*/
