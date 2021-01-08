package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants

class NeoWService {

    //BASE_URL

    private val BASE_URL = Constants.BASE_URL

    //Build Moshi Object
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    
}