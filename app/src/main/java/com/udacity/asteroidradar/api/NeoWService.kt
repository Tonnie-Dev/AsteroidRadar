package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



    //BASE_URL

    private val BASE_URL = Constants.BASE_URL

    //Build Moshi Object
    private val moshi =
            Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

    //Build Retrofit Object
    val retrofit =
            Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(BASE_URL)
                    .build()


    interface NeoWSInterface {

        //end point supplied on get
        @GET("neo/rest/v1/feed")
        suspend fun getNearEarthObjects(
                @Query("startDate")
                startDate: String,
                @Query("endDate")
                endDate: String,
                @Query("apiKey")
                apiKey: String): List<Asteroid>
    }


    //create singleton to instantiate Retrofit
    object NeoWService {

        val neoWService: NeoWSInterface by lazy {

            retrofit.create(NeoWSInterface::class.java)
        }
    }
