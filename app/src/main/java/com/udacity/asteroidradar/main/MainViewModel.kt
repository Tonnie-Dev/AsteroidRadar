package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.DateFilter
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NeoWService
import com.udacity.asteroidradar.api.parseJSONStringResponse
import com.udacity.asteroidradar.asAsteroidEntity
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repo.AsteroidRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {


    //instantiate database
    val database = AsteroidDatabase.getDatabaseInstance(application)

    //create repo
    val repo = AsteroidRepo(database)

    //expose LiveData from offline Database
    val asteroidList = repo.asteroidLiveData

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    private val _loadingStatus = MutableLiveData<PictureLoadingStatus>()
    val loadingStatus: LiveData<PictureLoadingStatus>
        get() = _loadingStatus

    init {
        // get picture of the Day when viewModel is first created
        getPictureOfTheDay()
testMethod(DateFilter.WEEK_ASTEROIDS)

    }



    fun testMethod(filter:DateFilter){

       viewModelScope.launch {
withContext(IO){


    //get network result
    val networkResponse = NeoWService.neoWService.getNearEarthObjects(
            filter.startDate,
            filter.endDate,
            Constants.API_KEY)

    //insert into AsteroidDatabase
    val parsedResponse = parseJSONStringResponse(networkResponse)
    database.asteroidDao.insertAsteroids(parsedResponse.asAsteroidEntity())
}
       }

    }
    private fun getPictureOfTheDay() {
        //launch coroutine inside viewModelScope
        viewModelScope.launch {

            try {
                _loadingStatus.value = PictureLoadingStatus.LOADING

                //get picture of the day from network
                val picture: PictureOfDay = NeoWService.neoWService.getPictureOfTheDay(Constants.API_KEY)
                // Timber.i("The picture is $picture")
                _pictureOfTheDay.value = picture

                //change picture loading status
                _loadingStatus.value = PictureLoadingStatus.DONE
            }
            catch (e: Exception) {
                e.printStackTrace()

                //change picture loading status in case of connection error
                _loadingStatus.value = PictureLoadingStatus.ERROR
            }
        }


    }


}

enum class PictureLoadingStatus {

    DONE,
    ERROR,
    LOADING
}


/* fun getAsteroids() {
        _loadingStatus.value = true

        //viewModelScope

        viewModelScope.launch {
            try {
                val response = NeoWService.neoWService.getNearEarthObjects(


                    "2021-01-10",
                    "2021-01-13",
                    "cQFHop6TptuKuyX1784hIC86YWgsWuFqOsxUTYoI")


                val parsedResponse = parseJSONStringResponse(response)
                _loadingStatus.value = false
               // _asteroidList.value = repo.asteroidLiveData
              // Timber.i("The raw data returned is $parsedResponse")
            } catch (e: Exception) {
                Timber.i("The raw data returned is $e")

            }

        }}
*/