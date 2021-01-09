package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NeoWService
import com.udacity.asteroidradar.api.parseJSONStringResponse
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainViewModel : ViewModel() {

    private val _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList

    init {
        getAsteroids()
    }


    fun getAsteroids() {

        //viewModelScope

        viewModelScope.launch {
            try {
                val response = NeoWService.neoWService.getNearEarthObjects(


                    "2015-09-07",
                    "2015-09-08",
                    "cQFHop6TptuKuyX1784hIC86YWgsWuFqOsxUTYoI")


                val parsedResponse = parseJSONStringResponse(response)

_asteroidList.value = parsedResponse
                Timber.i("The raw data returned is $parsedResponse")
            } catch (e: Exception) {
                Timber.i("The raw data returned is $e")

            }

        }


    }

    fun getTodayFormattedDate(): Date {

        val stringDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


        return stringDate.toDate(Constants.API_QUERY_DATE_FORMAT)!!

    }

    fun getNextSevenDaysFormattedDate(): Date {

        val date = LocalDate.now();
        val plusDays = date.plusDays(7);

        val stringDate =
            SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(plusDays)
        return stringDate.toDate(Constants.API_QUERY_DATE_FORMAT)!!

    }


    fun String.toDate(format: String): Date? {
        val dateFormatter = SimpleDateFormat(format, Locale.US)
        return try {
            dateFormatter.parse(this)
        } catch (e: ParseException) {
            null
        }
    }


}