package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface AsteroidDAO {
    //Return a LiveData today, order by Ascending
    @Query("SELECT * FROM asteroidentity WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
    fun getTodayAsteroids(startDate: String, endDate: String): LiveData<List<AsteroidEntity>>


    //Return a LiveData for the next one week, order by Ascending
    @Query("SELECT * FROM asteroidentity WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
    fun getWeekAsteroids(startDate: String, endDate: String): LiveData<List<AsteroidEntity>>

    //Return a LiveData for all saved asteroids,
    @Query("SELECT * FROM asteroidentity ORDER BY closeApproachDate DESC")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>


    //Insert Asteroid and replace items in case of duplications
    @Insert(onConflict = REPLACE)
    fun insertAsteroids(asteroidList: List<AsteroidEntity>)

}