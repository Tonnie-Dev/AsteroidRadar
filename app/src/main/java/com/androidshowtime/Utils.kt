package com.androidshowtime.asteroidradar

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

//Class Util to get time
class Utils {
    companion object {
        //get time
        private fun getDay(time: Long): String =
                SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(
                        time)
        //get today
        fun getTodayDate(): String {
            val currentTime = System.currentTimeMillis()
            return getDay(currentTime)
        }

        //calculate days into the future
        fun getDaysTo(days: Long): String {
            val diffDaysTo = TimeUnit.DAYS.toMillis(days)
            val time = System.currentTimeMillis() + diffDaysTo
            return getDay(time)
        }
    }
}
//Enum class for dates values
enum class DateFilter(val startDate: String, val endDate: String) {

    TODAY_ASTEROIDS(Utils.getTodayDate(), Utils.getTodayDate()),
    WEEK_ASTEROIDS(
            Utils.getTodayDate(), Utils.getDaysTo(7))
}
//Enum class for dates ranges
enum class DurationRange(val range: String) {

    RANGE_TODAY("Today"),
    RANGE_ONE_WEEK("Week"),
    RANGE_ALL_TIME("AllTime")
}



