package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface AsteroidDAO {

    //Return a LiveData to make owners observe LiveData Automatically
    @Query("SELECT * FROM asteroidentity ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>


    //Insert Videos - varags
    @Insert(onConflict = REPLACE)
    fun insertAsteroids(asteroidList: List<AsteroidEntity>)

}