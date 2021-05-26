package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//BASE_URL
private const val BASE_URL = Constants.BASE_URL


//Build Moshi Object
private val moshi =
        Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

//Build Retrofit Object
val retrofit: Retrofit =
        Retrofit.Builder()

                .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
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
            apiKey: String): String


    //get NASA's picture of the Day
    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
            @Query("api_key")
            apiKey: String): PictureOfDay


}


//create singleton to instantiate Retrofit
object NeoWService {

    val neoWService: NeoWSInterface by lazy {

        retrofit.create(NeoWSInterface::class.java)
    }
}
