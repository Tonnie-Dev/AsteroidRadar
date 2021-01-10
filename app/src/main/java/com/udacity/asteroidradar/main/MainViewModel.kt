package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.Utils
import com.udacity.asteroidradar.api.NeoWService
import com.udacity.asteroidradar.api.parseJSONStringResponse
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean>
    get() = _loading

    private val _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
    get() = _asteroidList

    private val _picture = MutableLiveData<PictureOfDay>()
    val picture:LiveData<PictureOfDay>
    get() = _picture

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


                    Utils.getTodayDate(),
                    Utils.getDaysTo(7),
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