package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    //abstract asteroidDao to access DAO class
    abstract val asteroidDao: AsteroidDAO

    //companion object to enable access to getDatabaseInstance() fxn
    companion object {

        //mark with @volatile annotation to avoid caching
        @Volatile
        private lateinit var INSTANCE: AsteroidDatabase

        //public fxn which returns INSTANCE after initialization
        fun getDatabaseInstance(context: Context): AsteroidDatabase {

            //wrap in synchronized block thread-safety
            synchronized(this) {

                //if instance is not initialized we create the DB Instance
                if (!::INSTANCE.isInitialized) {

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroiddatabase").fallbackToDestructiveMigration().build()
                }

                return INSTANCE

            }


        }


    }

}