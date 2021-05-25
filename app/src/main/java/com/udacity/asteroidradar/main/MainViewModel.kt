package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.NeoWService
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repo.AsteroidRepo
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _videoURL = MutableLiveData<String>()
    val videoURL:LiveData<String>
    get() = _videoURL

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



    init {
        // get picture of the Day when viewModel is first created
        getPictureOfTheDay()
    }

    private fun getPictureOfTheDay() {
        //launch coroutine inside viewModelScope
        viewModelScope.launch {

            try {

                //get picture of the day from network
                val picture= NeoWService.neoWService.getPictureOfTheDay(Constants.API_KEY)

                //set result to MutableLiveDataObject
                _pictureOfTheDay.value = picture

                if (picture.mediaType.equals("video")){

                    _videoURL.value = picture.url

                }else{


                }

                Timber.i("Today's Media Type is ${picture.mediaType} url ${picture.url}")


            }
            catch (e: Exception) {
                e.printStackTrace()

            }
        }


    }

    //method for updating date range as per menu selection
    fun updateRange(range: DurationRange) {

        _databaseAsteroidList.value = range
    }
}



