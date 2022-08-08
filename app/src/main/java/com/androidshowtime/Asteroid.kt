package com.androidshowtime.asteroidradar

import android.os.Parcelable
import com.androidshowtime.asteroidradar.database.AsteroidEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable


//Convert Network Asteroid to AsteroidEntity for use by Database


fun List<Asteroid>.asAsteroidEntity (): List<AsteroidEntity>{


    return this.map { AsteroidEntity(
        id = it.id,
        codename = it.codename,
        closeApproachDate = it.closeApproachDate,
        absoluteMagnitude = it.absoluteMagnitude,
        estimatedDiameter = it.estimatedDiameter,
        relativeVelocity = it.relativeVelocity,
        distanceFromEarth = it.distanceFromEarth,
        isPotentiallyHazardous = it.isPotentiallyHazardous) }
}
