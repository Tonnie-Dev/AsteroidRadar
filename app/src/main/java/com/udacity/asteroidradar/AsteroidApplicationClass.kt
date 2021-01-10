package com.udacity.asteroidradar

import android.app.Application
import timber.log.Timber

class AsteroidApplicationClass :Application(){

    override fun onCreate() {
        super.onCreate()
        //initialize Timber
        Timber.plant(Timber.DebugTree())
    }
}