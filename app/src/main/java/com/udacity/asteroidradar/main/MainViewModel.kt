package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.Utils
import com.udacity.asteroidradar.api.NeoWService
import com.udacity.asteroidradar.api.parseJSONStringResponse
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {


    //instantiate database
    val database = AsteroidDatabase.getDatabaseInstance()


    private val _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
    get() = _asteroidList

    private val _picture = MutableLiveData<PictureOfDay>()
    val picture:LiveData<PictureOfDay>
    get() = _picture

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean>
        get() = _loading
    init {
        getAsteroids()
        getPictureOfTheDay()

        Timber.i("Date is ${Utils.getTodayDate()}")
        Timber.i("Date is ${Utils.getDaysAgo(7L)}")
        Timber.i("Date is ${Utils.getDaysTo(7)}")
    }


    fun getAsteroids() {
        _loading.value = true

        //viewModelScope

        viewModelScope.launch {
            try {
                val response = NeoWService.neoWService.getNearEarthObjects(


                    "2021-01-10",
                    "2021-01-13",
                    "cQFHop6TptuKuyX1784hIC86YWgsWuFqOsxUTYoI")


                val parsedResponse = parseJSONStringResponse(response)
                _loading.value = false
                _asteroidList.value = parsedResponse
              // Timber.i("The raw data returned is $parsedResponse")
            } catch (e: Exception) {
                Timber.i("The raw data returned is $e")

            }

        }}


        fun getPictureOfTheDay(){

            viewModelScope.launch {


                try {

                    val picture: PictureOfDay = NeoWService.neoWService.getPictureOfTheDay(Constants.API_KEY)
                   // Timber.i("The picture is $picture")
                    _picture.value = picture

                }catch (e:java.lang.Exception){

                  //  Timber.i("The exception: $e")
                }
            }



    }




}

