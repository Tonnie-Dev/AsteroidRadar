package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//initialize Timber
        Timber.plant(Timber.DebugTree())
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        Timber.i("Today is $date")
    }
}
