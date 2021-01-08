package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NeoWService {

    //BASE_URL

    private val BASE_URL = Constants.BASE_URL

    //Build Moshi Object
    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    //Build Retrofit Object
    val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
}