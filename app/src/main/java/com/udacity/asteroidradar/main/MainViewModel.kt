package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.NeoWService
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repo.AsteroidRepo
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {


    //instantiate database
    val database = AsteroidDatabase.getDatabaseInstance(application)

    //create repo
    val repo = AsteroidRepo(database)


    private val _databaseAsteroidList = MutableLiveData<DurationRange>(DurationRange.RANGE_ONE_WEEK)
    //list of asteroids to observe
    val databaseAsteroidList =
            Transformations.switchMap<DurationRange, List<Asteroid>>(_databaseAsteroidList) { range ->
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
                    else                         -> MutableLiveData<List<Asteroid>>(emptyList())


                }
            }

    /*fun onTodayMenuItemSelected(){
        _databaseAsteroidList.value = "Today"
    }*/
    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

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



