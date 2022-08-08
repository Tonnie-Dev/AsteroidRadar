package com.androidshowtime.asteroidradar.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.androidshowtime.asteroidradar.Asteroid
import com.androidshowtime.asteroidradar.Constants
import com.androidshowtime.asteroidradar.DateFilter
import com.androidshowtime.asteroidradar.api.NeoWService
import com.androidshowtime.asteroidradar.api.parseJSONStringResponse
import com.androidshowtime.asteroidradar.asAsteroidEntity
import com.androidshowtime.asteroidradar.database.AsteroidDatabase
import com.androidshowtime.asteroidradar.database.convertToAsteroidDataClass
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import timber.log.Timber

class AsteroidRepo(private val database: AsteroidDatabase) {

    //LOAD ASTEROID DATA FROM NETWORK INTO THE REPO FOR 7 DAYS

    suspend fun getAsteroidsFromNetwork() {

Timber.i("called getAsteroidFromNetwork method")
        withContext(IO) {

            Timber.i("inside repo's IO")
            //get network result for the next seven days
            val networkResponse = NeoWService.neoWService.getNearEarthObjects(
                DateFilter.WEEK_ASTEROIDS.startDate,
                DateFilter.WEEK_ASTEROIDS.endDate,
                Constants.API_KEY)

            //insert into AsteroidDatabase
            val parsedResponse = parseJSONStringResponse(networkResponse)
            Timber.i("the Asteroid are $parsedResponse")

            database.asteroidDao.insertAsteroids(parsedResponse.asAsteroidEntity())
        }
    }


    //EXPOSE SAVED/OFFLINE CACHE AS LIVEDATA


    val todayAsteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDao.getTodayAsteroids(DateFilter.TODAY_ASTEROIDS
                                                                               .startDate, DateFilter
                    .TODAY_ASTEROIDS.endDate)) {
                it.convertToAsteroidDataClass()
            }

    val weekAsteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDao.getWeekAsteroids(DateFilter.WEEK_ASTEROIDS
                                                                               .startDate, DateFilter
                                                                               .WEEK_ASTEROIDS.endDate)) {
                it.convertToAsteroidDataClass()
            }


    val savedAsteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDao.getAllAsteroids()){
                it.convertToAsteroidDataClass()
            }




}