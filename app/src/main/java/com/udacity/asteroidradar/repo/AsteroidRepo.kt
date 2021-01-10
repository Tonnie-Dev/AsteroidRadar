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

class AsteroidRepo(private val database: AsteroidDatabase) {

    //LOAD ASTEROID DATA FROM NETWORK INTO THE REPO

    suspend fun getAsteroidsFromNetwork() {

        withContext(IO) {

            //get network result for the next seven days
            val networkResponse = NeoWService.neoWService.getNearEarthObjects(
                DateFilter.WEEK_ASTEROIDS.startDate,
                DateFilter.WEEK_ASTEROIDS.endDate,
                Constants.API_KEY)

            //insert into AsteroidDatabase
            val parsedResponse = parseJSONStringResponse(networkResponse)
            database.asteroidDao.insertAsteroids(parsedResponse.asAsteroidEntity())
        }
    }


    //EXPOSE SAVED/OFFLINE CACHE AS LIVEDATA


    fun applyDateFilter(filter: DateFilter):LiveData<List<Asteroid>>  {


        return when (filter) {
            DateFilter.TODAY_ASTEROIDS -> {
                Transformations.map(database.asteroidDao.getTodayAsteroids(filter.startDate, filter.endDate)){
                    it.convertToAsteroidDataClass()
                }

            }
            DateFilter.WEEK_ASTEROIDS  -> {

                Transformations.map(database.asteroidDao.getNextSevenDaysAsteroid(filter.startDate, filter.endDate)){

                    it.convertToAsteroidDataClass()
                }
            }
            else -> Transformations.map(database.asteroidDao.getAllAsteroids()) {

                it.convertToAsteroidDataClass()
            }
        }

    }
}