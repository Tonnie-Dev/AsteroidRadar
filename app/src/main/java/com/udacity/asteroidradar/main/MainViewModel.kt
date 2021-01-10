package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NeoWService
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repo.AsteroidRepo
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {


    //instantiate database
    val database = AsteroidDatabase.getDatabaseInstance(application)

    //create repo
    val repo = AsteroidRepo(database)


    private val _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
        get() = repo.asteroidLiveData

    private val _picture = MutableLiveData<PictureOfDay>()
    val picture: LiveData<PictureOfDay>
        get() = _picture

    private val _loadingStatus = MutableLiveData<PictureLoadingStatus>()
    val loadingStatus: LiveData<PictureLoadingStatus>
        get() = _loadingStatus

    init {

        viewModelScope.launch {
            // repo.getAsteroidsListFromNetwork(DateFilter.WEEK_ASTEROIDS)

        }

        // get picture of the Day when viewModel is first created
        getPictureOfTheDay()


    }


    private fun getPictureOfTheDay() {
//launch coroutine inside viewModelScope
        viewModelScope.launch {


            try {
                _loadingStatus.value = PictureLoadingStatus.LOADING

                //get picture of the day from network
                val picture: PictureOfDay =
                    NeoWService.neoWService.getPictureOfTheDay(Constants.API_KEY)
                // Timber.i("The picture is $picture")
                _picture.value = picture

                //change picture loading status
                _loadingStatus.value = PictureLoadingStatus.DONE
            } catch (e: java.lang.Exception) {
                e.printStackTrace()

                //change picture loading status in case of connection error
                _loadingStatus.value = PictureLoadingStatus.ERROR
            }
        }


    }


}

enum class PictureLoadingStatus {

    DONE, ERROR, LOADING
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