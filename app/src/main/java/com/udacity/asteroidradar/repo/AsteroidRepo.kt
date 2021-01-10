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
import com.udacity.asteroidradar.database.asAsteroidModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AsteroidRepo(private val database: AsteroidDatabase) {

    //LOAD ASTEROID DATA FROM NETWORK INTO THE REPO

    suspend fun getAsteroidsFromNetwork(filter: DateFilter) {

        withContext(IO) {

            //get network result
            val networkResponse = NeoWService.neoWService.getNearEarthObjects(
                filter.date,
                filter.date,
                Constants.API_KEY)

            //insert into AsteroidDatabase
            val parsedResponse = parseJSONStringResponse(networkResponse)
            database.asteroidDao.insertAsteroids(parsedResponse.asAsteroidEntity())
        }
    }


    //EXPOSE SAVED/OFFLINE CACHE AS LIVEDATA
    val asteroidLiveData: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {

            it.asAsteroidModel()
        }
}