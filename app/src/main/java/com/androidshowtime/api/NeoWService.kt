package com.androidshowtime.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.androidshowtime.asteroidradar.Constants
import com.androidshowtime.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//"https://api.nasa.gov/neo/rest/v1/feed"

//BASE_URL
private const val BASE_URL = Constants.BASE_URL


//Build Moshi Object
private val moshi =
    Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

//Build Retrofit Object
val retrofit =
    Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create()) // for parsing JSON String
        .addConverterFactory(MoshiConverterFactory.create(moshi)) //for parsing KotlinObjects i.e.
        // picture of the day
        .baseUrl(BASE_URL)
        .build()


interface NeoWSInterface {


    //get NASA's Asteroid Data and return a string to be parsed in the provide NetworkUtils

    //end point supplied on @GET annotation
    @GET("neo/rest/v1/feed")
    suspend fun getNearEarthObjects(

        //filters for the dates
        @Query("start_date")
        startDate: String,
        @Query("end_date")
        endDate: String,

        //API_KEY appended at the end
        @Query("api_key")
        apiKey: String
    ): String


    //get NASA's picture of the Day
    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key")
        apiKey: String
    ): PictureOfDay


}


//create singleton to instantiate Retrofit
object NeoWService {

    val neoWService: NeoWSInterface by lazy {

        retrofit.create(NeoWSInterface::class.java)
    }
}
