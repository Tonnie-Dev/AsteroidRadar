package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


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
                .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(BASE_URL)
                    .build()


    interface NeoWSInterface {
        //private val BASE_URL = Constants.BASE_URL

        //"neo/rest/v1/feed?start_date=2020-12-07&end_date=2020-12-08&api_key=cQFHop6TptuKuyX1784hIC86YWgsWuFqOsxUTYoI"
        //end point supplied on get
        @GET("neo/rest/v1/feed")
        suspend fun getNearEarthObjects(
                @Query("start_date")
                startDate: String,
                @Query("end_date")
                endDate: String,
                @Query("api_key")
                apiKey: String):String
    }


    //create singleton to instantiate Retrofit
    object NeoWService {

        val neoWService: NeoWSInterface by lazy {

            retrofit.create(NeoWSInterface::class.java)
        }
    }
