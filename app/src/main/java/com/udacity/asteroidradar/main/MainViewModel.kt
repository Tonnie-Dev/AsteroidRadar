package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.NeoWService
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repo.AsteroidRepo
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {


    //get database instant
    private val database = AsteroidDatabase.getDatabaseInstance(application)

    //create repo
    private val repo = AsteroidRepo(database)

//MutableLiveData setup with DurationRange Enum - Default is 1 week asteroids; can be altered at with menu
    private val _databaseAsteroidList = MutableLiveData(DurationRange.RANGE_ONE_WEEK)

    //use switchMap to switch between Repo's LiveData  but can be altered at with menu
    val databaseAsteroidList =
            Transformations.switchMap(_databaseAsteroidList) { range ->
                when (range) {
                    DurationRange.RANGE_TODAY -> {
                        repo.todayAsteroids
                    }
                    DurationRange.RANGE_ONE_WEEK -> {
                        repo.weekAsteroids
                    }

                    DurationRange.RANGE_ALL_TIME -> {
                        repo.savedAsteroids
                    }

                    else-> MutableLiveData(emptyList())


                }
            }

    //MutableLiveData to get store picture of the day value
    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    //MutableLiveData to track picture of the day loading status(ERROR,DONE,LOADING)
    private val _loadingStatus = MutableLiveData<PictureLoadingStatus>()
    val loadingStatus: LiveData<PictureLoadingStatus>
        get() = _loadingStatus

    init {
        // get picture of the Day when viewModel is first created
        getPictureOfTheDay()
    }

    private fun getPictureOfTheDay() {
        //launch coroutine inside viewModelScope
        viewModelScope.launch {

            try {
                //change picture loading status to LOADING
                _loadingStatus.value = PictureLoadingStatus.LOADING

                //get picture of the day from network
                val picture: PictureOfDay = NeoWService.neoWService.getPictureOfTheDay(Constants.API_KEY)

                //set result to MutableLiveDataObject
                _pictureOfTheDay.value = picture

                //change picture loading status to DONE
                _loadingStatus.value = PictureLoadingStatus.DONE
            }
            catch (e: Exception) {
                e.printStackTrace()

                //change picture loading status in case of connection error to ERROR
                _loadingStatus.value = PictureLoadingStatus.ERROR
            }
        }


    }

    //method for updating date range as per menu selection
    fun updateRange(range: DurationRange) {

        _databaseAsteroidList.value = range
    }
}



