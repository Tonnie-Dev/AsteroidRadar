package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.DateFilter
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repo.AsteroidRepo
import retrofit2.HttpException

class LoadAsteroidsWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "LoadAsteroidWorker"
    }

    override suspend fun doWork(): Result {

        //get instance of database for use with Repo
        val db = AsteroidDatabase.getDatabaseInstance(applicationContext)

        //initialize Repo
        val repo = AsteroidRepo(db)

        return try {
            //define work i.e. load asteroids from Network for the next seven days
                repo.getAsteroidsFromNetwork()

            Result.success()
        }catch (e:HttpException){

            Result.retry()
        }

    }
}