package com.androidshowtime.asteroidradar

import android.app.Application
import androidx.work.*
import com.androidshowtime.asteroidradar.worker.LoadAsteroidsWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidApplicationClass : Application() {
    /**
     * Application's onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */

    override fun onCreate() {
        super.onCreate()
        //initialize Timber
        Timber.plant(Timber.DebugTree())
Timber.i("Application's onCreate Called")

        //start work inside onCreate
        runWorkInBackground()
    }

    //switch work to run on background
    private fun runWorkInBackground(){

         CoroutineScope(Default).launch {

             setUpAsteroidLoadingWork()
         }
     }

    //set-up work
    private fun setUpAsteroidLoadingWork() {

        //define work constraints
        val workConstraints =
                Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.UNMETERED)
                        .setRequiresCharging(false)
                        .build()

        //create WorkRequest
        val workRequest = PeriodicWorkRequestBuilder<LoadAsteroidsWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                workConstraints)
                .build()

        //get WorkManager
        val workManager = WorkManager.getInstance(this)


        //enqueue work
        workManager.enqueueUniquePeriodicWork(
                LoadAsteroidsWorker.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest)


    }

}