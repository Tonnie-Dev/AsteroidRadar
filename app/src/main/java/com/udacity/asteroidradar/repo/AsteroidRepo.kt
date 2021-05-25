package com.udacity.asteroidradar.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.DateFilter
import com.udacity.asteroidradar.api.NeoWService
import com.udacity.asteroidradar.api.parseJSONStringResponse
import com.udacity.asteroidradar.asAsteroidEntity
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.convertToAsteroidDataClass
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import timber.log.Timber

class AsteroidRepo(private val database: AsteroidDatabase) {

    //LOAD ASTEROID DATA FROM NETWORK INTO THE REPO FOR 7 DAYS

    suspend fun getAsteroidsFromNetwork() {

        withContext(IO) {

            //get network result for the next seven days
            val networkResponse = NeoWService.neoWService.getNearEarthObjects(
                DateFilter.WEEK_ASTEROIDS.startDate,
                DateFilter.WEEK_ASTEROIDS.endDate,
                Constants.API_KEY)

            //insert into AsteroidDatabase
            val parsedResponse = parseJSONStringResponse(networkResponse)
            Timber.i("the Astroid are $parsedResponse")

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